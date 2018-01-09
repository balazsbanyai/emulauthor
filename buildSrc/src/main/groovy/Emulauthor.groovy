import org.gradle.api.Task
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Exec


class Emulauthor implements Plugin<Project> {

    void apply(Project project) {

        def supportedPluginIds = [
                'com.android.application' : 'applicationVariants',
                'com.android.library' : 'libraryVariants' ]

        supportedPluginIds.each { entry ->
            project.plugins.withId(entry.key) { plugin ->
                apply(project, project.android, project.android."${entry.value}")
            }
        }

    }

    void apply(Project project, def android, def variants) {

        EmulatorSpec spec = project.extensions.create("emulator", EmulatorSpec)

        Task deleteEmulator = project.tasks.create(name: 'deleteEmulator', type: DeleteEmulatorTask)

        Task stopEmulator = project.tasks.create(name: 'stopEmulator', type: StopEmulatorTask) {
            finalizedBy deleteEmulator
        }

        Task createEmulator = project.tasks.create(name: 'createEmulator', type: CreateEmulatorTask) {
            emulator = spec
            finalizedBy deleteEmulator
        }

        Task startEmulator = project.tasks.create(name: 'startEmulatorProcess', type: StartEmulatorTask) {
            dependsOn createEmulator
        }

        variants.all { v ->
            if (v.testVariant) {
                Task connectedTest = v.testVariant.connectedInstrumentTest
                Task my = project.tasks.create(name: "${connectedTest.name}OnEmulator", group: "Verification")

                connectedTest.mustRunAfter startEmulator

                my.dependsOn startEmulator
                my.dependsOn connectedTest
                my.finalizedBy stopEmulator
            }
        }


    }


}