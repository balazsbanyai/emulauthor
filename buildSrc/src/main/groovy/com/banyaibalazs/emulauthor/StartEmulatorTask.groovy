package com.banyaibalazs.emulauthor

import org.gradle.api.tasks.TaskAction

class StartEmulatorTask extends SdkAwareTask {

    @TaskAction
    void startEmulatorProcess() {
        getProject().ext.emulatorProc=new ProcessBuilder("${sdk}/tools/emulator", '@tester')
                .redirectErrorStream(true)
                .start()
    }

}