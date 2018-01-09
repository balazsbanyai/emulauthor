# emulauthor
A gradle plugin that starts/stops the emulator

```groovy
apply plugin: 'com.banyaibalazs.emulauthor'

emulator {
    abi 'google_apis/x86'
    pkg 'system-images;android-25;google_apis;x86'
}

```

Then run: `./gradlew connectedDebugAndroidTestOnEmulator`