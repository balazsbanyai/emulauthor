import org.gradle.api.tasks.TaskAction

class StartEmulatorTask extends SdkAwareTask {

    @TaskAction
    void startEmulatorProcess() {
        getProject().ext.emulatorProc=new ProcessBuilder("${sdk}/tools/emulator", '@tester')
                .redirectErrorStream(true)
                .start()

        getProject().exec {
            executable "${sdk}/platform-tools/adb"
            args 'wait-for-device', 'shell', 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done;'
        }
    }

}