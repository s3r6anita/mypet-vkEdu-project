pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven (
            url = "https://artifactory-external.vkpartner.ru/artifactory/vkid-sdk-andorid/"
        )
    }
}

rootProject.name = "My Pet"
include(":app")
