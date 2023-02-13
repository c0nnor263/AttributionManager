@file:Suppress("UnstableApiUsage")
plugins {
    id("com.android.library")
    kotlin("android")

}
apply(from = "publish-module.gradle.kts")


android {

    namespace = Extras.NAMESPACE
    compileSdk = Versions.compileSdk

    defaultConfig {

        minSdk = Versions.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = Versions.sourceCompatibility
        targetCompatibility = Versions.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}


dependencies {
    implementation("androidx.core:core-ktx:1.9.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Google
    implementation("com.google.android.gms:play-services-ads-identifier:18.0.1")
    implementation("com.android.installreferrer:installreferrer:2.2")

    implementation("com.google.code.gson:gson:2.10.1")
}


//tasks.create("androidSourcesJar", Jar::class.java) {
//    archiveClassifier.set("sources")
//    project.plugins.findPlugin("com.android.library")?.let {
//        from(android.sourceSets.getByName("main").java.srcDirs)
//        from(android.sourceSets.getByName("main").kotlin.srcDirs())
//    }
//}
//
//artifacts {
//    archives(tasks.getByName("androidSourcesJar"))
//}