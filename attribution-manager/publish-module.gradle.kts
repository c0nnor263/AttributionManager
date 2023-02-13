import Extras.PUBLISH_ARTIFACT_ID

apply(plugin = "maven-publish")
apply(plugin = "signing")


    configure<PublishingExtension> {

        publications.create<MavenPublication>("release") {
            groupId = Extras.PUBLISH_GROUP_ID
            artifactId = PUBLISH_ARTIFACT_ID
            version = Extras.PUBLISH_VERSION

            afterEvaluate {
                from(components.getByName("release"))
            }
            //artifact("androidSourcesJar")

            pom {
                name.set(Extras.PUBLISH_ARTIFACT_ID)
                description.set(Extras.PUBLISH_DESCRIPTION)
                url.set(Extras.PUBLISH_URL)
                licenses {
                    license {
                        name.set(Extras.PUBLISH_LICENSE_NAME)
                        url.set(Extras.PUBLISH_LICENSE_URL)
                    }
                }
                developers {
                    developer {
                        id.set(Extras.PUBLISH_DEVELOPER_ID)
                        name.set(Extras.PUBLISH_DEVELOPER_NAME)
                        email.set(Extras.PUBLISH_DEVELOPER_EMAIL)
                    }
                }
                scm {
                    connection.set(Extras.PUBLISH_SCM_CONNECTION)
                    developerConnection.set(Extras.PUBLISH_SCM_DEVELOPER_CONNECTION)
                    url.set(Extras.PUBLISH_SCM_URL)
                }
            }
        }
        repositories {
            mavenLocal()
            mavenCentral()
        }
        configure<SigningExtension> {
            sign(publications)
        }
    }