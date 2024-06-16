import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")// version "1.9.21-1.0.15"
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.f4.mypet"
    compileSdk = 34

    val localParams = Properties().apply {
        load(project.project.file("secrets.properties").inputStream())
    }

    defaultConfig {
        applicationId = "com.f4.mypet"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        addManifestPlaceholders(mapOf(
            "VKIDRedirectHost" to localParams.getProperty("VKIDRedirectHost"),
            "VKIDRedirectScheme" to localParams.getProperty("VKIDRedirectScheme"),
            "VKIDClientID" to localParams.getProperty("VKIDClientID"),
            "VKIDClientSecret" to localParams.getProperty("VKIDClientSecret")
        ))

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val sdkVersion = "1.3.1"
val version = "2.0.4"

dependencies {
    // vk id
    implementation("com.vk.id:vkid:1.3.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$version")
    implementation("com.vk.id:onetap-compose:${sdkVersion}")
//    vk sdk
    implementation("com.vk:android-sdk-core:4.1.0")
    implementation("com.vk:android-sdk-api:4.1.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.5.0")


    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore:1.1.1")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material:1.6.7")

    implementation("androidx.compose.ui:ui-tooling-preview")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.7")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")

    // Navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Dagger
    implementation("com.google.dagger:hilt-android:2.49")
    ksp("com.google.dagger:dagger-compiler:2.49") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.49")   // Hilt compiler
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.7")
}
