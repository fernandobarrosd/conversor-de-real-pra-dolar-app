import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.hiltPlugin)
}

val localProperties = Properties()
val localPropertiesFile = File("local.properties")

if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    signingConfigs {
        create("release") {
            storeFile = file(localProperties["KEY_STORE_PATH"] as String)
            storePassword = localProperties["KEY_STORE_PASSWORD"] as String
            keyAlias = localProperties["KEY_STORE_ALIAS"] as String
            keyPassword = localProperties["KEY_STORE_PASSWORD"] as String
        }
    }
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
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("release")
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

    //Hilt
    implementation(libs.hilt)
    annotationProcessor(libs.hilt.compiler)

    // Leak Canary
    debugImplementation(libs.leakCanary)

    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}