package com.banyaibalazs.emulauthor

import org.gradle.api.tasks.TaskAction

class DeleteEmulatorTask extends SdkAwareTask {

    @TaskAction
    void deleteEmulator() {
        getProject().exec {
            executable "${sdk}/tools/bin/avdmanager"
            args "delete", "avd", "--name", "tester"
        }
    }

}