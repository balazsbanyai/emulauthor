package com.banyaibalazs.emulauthor

import org.gradle.api.tasks.TaskAction

class WaitForEmulatorTask extends SdkAwareTask {

    @TaskAction
    void waitUntilEmulatorReady() {
        getProject().exec {
            executable "${sdk}/platform-tools/adb"
            args 'wait-for-device', 'shell', 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done;'
        }
    }

}