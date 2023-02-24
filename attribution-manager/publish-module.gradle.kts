apply(plugin = "maven-publish")
apply(plugin = "signing")

configure<PublishingExtension> {

    publications.create<MavenPublication>("release") {
        groupId = Extras.Manager.PUBLISH_GROUP_ID
        artifactId = Extras.Manager.PUBLISH_ARTIFACT_ID
        version = Extras.Manager.PUBLISH_VERSION

        afterEvaluate {
            from(components.getByName("release"))
        }
        pom {
            name.set(Extras.Manager.PUBLISH_ARTIFACT_ID)
            description.set(Extras.Manager.PUBLISH_DESCRIPTION)
            url.set(Extras.Manager.PUBLISH_URL)
            licenses {
                license {
                    name.set(Extras.Manager.PUBLISH_LICENSE_NAME)
                    url.set(Extras.Manager.PUBLISH_LICENSE_URL)
                }
            }
            developers {
                developer {
                    id.set(Extras.Manager.PUBLISH_DEVELOPER_ID)
                    name.set(Extras.Manager.PUBLISH_DEVELOPER_NAME)
                    email.set(Extras.Manager.PUBLISH_DEVELOPER_EMAIL)
                }
            }
            scm {
                connection.set(Extras.Manager.PUBLISH_SCM_CONNECTION)
                developerConnection.set(Extras.Manager.PUBLISH_SCM_DEVELOPER_CONNECTION)
                url.set(Extras.Manager.PUBLISH_SCM_URL)
            }
        }
    }
    repositories {
        mavenLocal()
        mavenCentral()
    }
}