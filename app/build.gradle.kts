plugins {
    alias(libs.plugins.android.application)
    id ("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.pramilak.videoplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pramilak.videoplayer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    //for ViewBinding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.androidx.core.ktx)


    implementation(libs.androidx.legacy)

    //for glide
    implementation(libs.glide)

    //for exoplayer
    implementation(libs.exoplayerCore)
    implementation(libs.exoplayerUi)

    //for playing online content
    implementation(libs.exoplayerDash)

    //for vertical progress bar
    implementation(libs.verticalSeekbar)

    //for doubleTapFeature
    implementation(libs.doubleTapPlayerView)


    //custom chrome tabs for integrating youtube
    implementation(libs.androidx.browser)
    implementation(libs.firebase.database.ktx)

    implementation ("com.github.samyak2403:NativeAds-Recyclerview:1.0")


    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.google.android.gms:play-services-ads:23.3.0")


    implementation ("com.intuit.sdp:sdp-android:1.0.6")
    implementation ("com.github.bumptech.glide:glide:4.14.1")
    implementation ("com.airbnb.android:lottie:4.2.1")

    implementation ("androidx.multidex:multidex:2.0.1")
//    implementation project(':countrycodepicker')

    implementation ("com.google.code.gson:gson:2.9.0")
    // room - sqlite
    implementation ("androidx.room:room-runtime:2.4.3")
    annotationProcessor ("androidx.room:room-compiler:2.4.3")

//    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.8.0"
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0"
//    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0"


    /*admob*/
//    implementation 'com.google.android.gms:play-services-ads:22.2.0'

    /*fb ad*/
//    implementation 'androidx.annotation:annotation:1.6.0'

//    // Add Facebook Network
//    implementation 'com.ironsource.adapters:facebookadapter:4.3.44'
//    implementation 'com.facebook.android:audience-network-sdk:6.15.0'
//
//    /*ironsource*/
//    implementation 'com.ironsource.sdk:mediationsdk:7.4.0'
//    implementation 'com.google.android.gms:play-services-appset:16.0.0'
//    implementation 'com.google.android.gms:play-services-ads-identifier:18.0.1'
//    implementation 'com.google.android.gms:play-services-basement:18.1.0'


}