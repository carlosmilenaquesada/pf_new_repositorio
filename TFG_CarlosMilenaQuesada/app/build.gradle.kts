plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.tfg_carlosmilenaquesada"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tfg_carlosmilenaquesada"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.volley)
    implementation(libs.recyclerview)
    //librería de marterial
    implementation(libs.material.v130)
    //implementación para usar la cámara como lector codigo barras
    implementation(libs.zxing.android.embedded)
    //Conversor gson
    implementation(libs.gson)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}


























