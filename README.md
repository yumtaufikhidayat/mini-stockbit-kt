## Mini Stockbit
This application build with:
- 100% Kotlin
- Material Design
- ViewBinding
- Single Activity
- Navigation Component
- Firebase Authentication
- Facebook SDK
- Retrofit
- Model View ViewModel (MVVM) Pattern
- Dagger Hilt
- Flow/Coroutine
- Paging 3

### Software
#### Operating System
- OS Name : Linux Mint (based on Ubuntu 20.04 LTS)
- Version : 20.2 Uma
- Platform : 64 bit

#### Programming Language
- Language Name : Kotlin
- Version : 1.5.21

#### IDE (Integrated Development Environment)
- IDE Name : Android Studio
- Version : 2020.3.1
- Codename : Arctic Fox

#### Java Build Tools
- Java Build Tools : Gradle
- Android Gradle Plugin Version : 7.0.0
- Android Gradle Version: 7.0.2

#### SDK Version
- Target SDK Version : 30
- Min SDK Version : 23
- Compile SDK Version : 30

#### AndroidX
- Migrate to AndroidX : Yes

#### Dependencies
##### By Default
        - implementation 'androidx.core:core-ktx:1.6.0'
        - implementation 'androidx.appcompat:appcompat:1.3.1'
        - implementation 'com.google.android.material:material:1.4.0'
        - implementation 'androidx.constraintlayout:constraintlayout:2.1.0'
        - testImplementation 'junit:junit:4.13.2'
        - androidTestImplementation 'androidx.test.ext:junit:1.1.3'
        - androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

##### Navigation Component
        - def nav_version = "2.3.5"
        - implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
        - implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

##### Firebase
        - implementation 'com.google.firebase:firebase-auth:21.0.1'
        - implementation platform('com.google.firebase:firebase-bom:28.3.1')
        - implementation 'com.google.firebase:firebase-auth-ktx'
        - implementation 'com.google.android.gms:play-services-auth:19.2.0'

##### Facebook SDK
        - implementation 'com.facebook.android:facebook-android-sdk:[5,6)'