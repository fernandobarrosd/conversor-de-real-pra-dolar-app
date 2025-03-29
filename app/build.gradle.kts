import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.fernando.brlmoneyconverter"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }


    defaultConfig {
        applicationId = "com.fernando.brlmoneyconverter"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = File("local.properties")

    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_TOKEN", localProperties.getProperty("API_TOKEN"))
        }
        release {
            buildConfigField("String", "API_TOKEN", localProperties.getProperty("API_TOKEN"))
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)


    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}