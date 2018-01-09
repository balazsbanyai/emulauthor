package com.banyaibalazs.emulauthor

import org.gradle.api.Task
import org.gradle.api.Plugin
import org.gradle.api.Project

class Emulauthor implements Plugin<Project> {

    void apply(Project project) {

        def supportedPluginIds = [
                'com.android.application' : 'applicationVariants',
                'com.android.library' : 'libraryVariants' ]

        supportedPluginIds.each { entry ->
            project.plugins.withId(entry.key) { plugin ->
                apply(project, project.android."${entry.value}")
            }
        }

    }

    void apply(Project project, def variants) {

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

        Task waitForEmulator = project.tasks.create(name: 'waitForEmulatorToStart', type: WaitForEmulatorTask) {
            dependsOn startEmulator
        }

        variants.all { v ->
            if (v.testVariant) {
                Task connectedTest = v.testVariant.connectedInstrumentTest
                Task emulatorTest = project.tasks.create(name: "${connectedTest.name}OnEmulator", group: "Verification")

                connectedTest.mustRunAfter waitForEmulator

                emulatorTest.dependsOn waitForEmulator
                emulatorTest.dependsOn connectedTest
                emulatorTest.finalizedBy stopEmulator
            }
        }


    }


}