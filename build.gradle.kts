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
}

group = Extras.PUBLISH_GROUP_ID
version = Extras.PUBLISH_VERSION


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

