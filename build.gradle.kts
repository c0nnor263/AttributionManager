buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        mavenLocal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}

plugins {
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

group = Extras.Manager.PUBLISH_GROUP_ID
version = Extras.Manager.PUBLISH_VERSION


val secretPropsFile: File = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    val keystoreProperties = java.util.Properties()
    val stream = java.io.FileInputStream(secretPropsFile)
    keystoreProperties.load(stream)

    nexusPublishing {
        repositories {
            sonatype {
                stagingProfileId.set(keystoreProperties.getProperty("sonatypeStagingProfileId"))
                username.set(keystoreProperties.getProperty("ossrhUsername"))
                password.set(keystoreProperties.getProperty("ossrhPassword"))
                nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
                snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            }
        }
    }
}

