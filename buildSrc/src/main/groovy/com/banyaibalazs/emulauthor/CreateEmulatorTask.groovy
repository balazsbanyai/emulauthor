package com.banyaibalazs.emulauthor

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class CreateEmulatorTask extends SdkAwareTask {

    @Input
    EmulatorSpec emulator

    @TaskAction
    void createEmulator() {
        getProject().exec {
            executable "${sdk}/tools/bin/avdmanager"
            args 'create', 'avd', '--force', '--name', 'tester', '--abi', emulator.abi, '--package', emulator.pkg
            standardInput = new ByteArrayInputStream("no\n".getBytes())
        }
    }

}