package com.banyaibalazs.emulauthor

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

class SdkAwareTask extends DefaultTask {

    @Input
    def getSdk() {
        getProject().android.getSdkDirectory()
    }

}