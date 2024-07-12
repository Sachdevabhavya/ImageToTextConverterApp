# Image To Text Converter

This is an image-to-text converter app built in Kotlin, utilizing Google ML Kit to transform text from images into editable text form. The layout is designed using XML, providing an intuitive interface for users. This app can significantly enhance productivity by saving users the time and effort of manually transcribing text from images, making it a highly valuable tool.

# Technology Used

- Android Studio
- Kotlin
- Google ML kit

# Install the project

```git
    git clone https://github.com/Sachdevabhavya/ImageToTextConverterApp.git
```

# Dependencies required

Add the following dependencies to run the app in the following file given :

- build.gradle.kts(module):

```kt
    implementation(platform("com.google.firebase:firebase-bom:33.1.1"))
    implementation("com.google.firebase:firebase-analytics")

    //TextRecognition
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
```

- build.gradle.kts(project):

```kt
    id("com.google.gms.google-services") version "4.4.2" apply false
```

# Update SDK in Build.gradle

```kt
compile Sdk = 34
target Sdk = 34
```

# Android Verion

This app is made for android 7 and above versions
