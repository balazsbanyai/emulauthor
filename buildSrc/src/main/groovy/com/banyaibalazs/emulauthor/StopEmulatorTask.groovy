package com.banyaibalazs.emulauthor

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class StopEmulatorTask extends DefaultTask {

    @TaskAction
    void stopEmulatorProcess() {
        Process p = getProject().ext.emulatorProc
        p.destroyForcibly()
    }

}