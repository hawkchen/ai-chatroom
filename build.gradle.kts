plugins {
    war
    id("org.gretty") version "3.1.1"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://mavensync.zkoss.org/maven2")
    }

    maven {
        url = uri("https://mavensync.zkoss.org/eval")
    }

}

dependencies {
    implementation("org.zkoss.zk:zkmax:9.6.3-Eval")
    implementation("com.google.code.gson:gson:2.7")

    testImplementation("junit:junit:4.4")
}



group = "org.zkoss.support"
version = "1.0-SNAPSHOT"
description = "The ai-chartroom Project"
java.sourceCompatibility = JavaVersion.VERSION_1_8






