import org.gradle.api.DefaultTask
import org.gradle.api.DefaultTask

class SdkAwareTask extends DefaultTask {

    def getSdk() {
        getProject().android.getSdkDirectory()
    }

}