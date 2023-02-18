plugins {
    war
    id("org.gretty") version "3.1.1"
}

//create custom configurations
val jettyRunnerRuntimeOnly by configurations.creating

repositories {
    mavenCentral()
    maven {
        url = uri("https://mavensync.zkoss.org/maven2")
    }

    maven {
        url = uri("https://mavensync.zkoss.org/eval")
    }

}

sourceSets {
    create("jettyRunner") {
        runtimeClasspath += sourceSets["main"].runtimeClasspath
    }
}



dependencies {
    implementation("org.zkoss.zk:zkmax:9.6.3-Eval")
    implementation("com.google.code.gson:gson:2.7")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")
    //runtimeOnly("org.zkoss.themepack:theme-pack:9.6.3") //require commercial license
    jettyRunnerRuntimeOnly("org.eclipse.jetty:jetty-runner:9.4.50.v20221201")
    testImplementation("junit:junit:4.4")
}



group = "org.zkoss.support"
version = "1.0-SNAPSHOT"
description = "The ai-chartroom Project"
java.sourceCompatibility = JavaVersion.VERSION_1_8


tasks.register("startJettyRunner", JavaExec::class.java) {
        main = "org.eclipse.jetty.runner.Runner"
        classpath(sourceSets["jettyRunner"].runtimeClasspath)
        args("--path", "/${rootProject.name}", "src/main/webapp")
}