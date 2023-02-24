apply(plugin = "maven-publish")
apply(plugin = "signing")

group = Extras.PUBLISH_GROUP_ID
version = Extras.PUBLISH_VERSION
afterEvaluate {
configure<PublishingExtension> {

    publications.create<MavenPublication>("attribution-engine-ktor-release") {
        groupId = Extras.PUBLISH_GROUP_ID
        artifactId = Extras.KtorEngine.PUBLISH_ARTIFACT_ID
        version = Extras.PUBLISH_VERSION

        afterEvaluate {
            from(components.getByName("release"))
        }
        pom {
            name.set(Extras.KtorEngine.PUBLISH_ARTIFACT_ID)
            description.set(Extras.KtorEngine.PUBLISH_DESCRIPTION)
            url.set(Extras.KtorEngine.PUBLISH_URL)
            licenses {
                license {
                    name.set(Extras.KtorEngine.PUBLISH_LICENSE_NAME)
                    url.set(Extras.KtorEngine.PUBLISH_LICENSE_URL)
                }
            }
            developers {
                developer {
                    id.set(Extras.KtorEngine.PUBLISH_DEVELOPER_ID)
                    name.set(Extras.KtorEngine.PUBLISH_DEVELOPER_NAME)
                    email.set(Extras.KtorEngine.PUBLISH_DEVELOPER_EMAIL)
                }
            }
            scm {
                connection.set(Extras.KtorEngine.PUBLISH_SCM_CONNECTION)
                developerConnection.set(Extras.KtorEngine.PUBLISH_SCM_DEVELOPER_CONNECTION)
                url.set(Extras.KtorEngine.PUBLISH_SCM_URL)
            }
        }
    }
    repositories {
        mavenLocal()
        mavenCentral()
    }

    configure<SigningExtension> {
        sign(publications["attribution-engine-ktor-release"])
    }
}
}