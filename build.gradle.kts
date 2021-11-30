plugins {
    java
    kotlin("jvm") version "1.4.31" apply false
    id("nu.studer.jooq") version "6.0.1" apply false
    `kotlin-dsl`
    application
}

group = "org.xendv.java.edumail"
version = "1.0-SNAPSHOT"

dependencies{
    val restEasyVersion = "4.5.8.Final"
    implementation("org.jboss.resteasy:resteasy-guice:$restEasyVersion")
    implementation("org.jboss.resteasy:resteasy-jackson2-provider:$restEasyVersion")

    implementation(project(":jooq-generated"))
    implementation("javax.ws.rs:javax.ws.rs-api:2.1.1")

    val jettyVersion = "9.4.33.v20201020";
    // server jetty
    implementation("org.eclipse.jetty:jetty-servlet:$jettyVersion")
    implementation("org.eclipse.jetty:jetty-server:$jettyVersion")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")

    //jersey
    val jerseyVersion = "2.32"
    implementation("org.glassfish.jersey.containers:jersey-container-servlet:$jerseyVersion")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-jaxb:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-multipart:$jerseyVersion")

    // guice
    implementation("com.google.inject:guice:4.2.2")
    implementation("com.google.inject.extensions:guice-servlet:4.2.2")
    implementation("org.glassfish.hk2:guice-bridge:2.6.0")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    // db
    implementation("org.flywaydb:flyway-core:8.0.1")
    implementation("org.postgresql:postgresql:42.2.9")

    // compile
    compile("org.jooq:jooq:3.15.4")
    // jooq
    implementation("org.jooq:jooq:3.15.4")
    implementation("org.jooq:jooq-codegen:3.15.4")
    implementation("org.jooq:jooq-meta:3.15.4")

    // annotations
    implementation ("org.jetbrains:annotations:13.0")
    implementation("org.projectlombok:lombok:1.18.4")
    annotationProcessor("org.projectlombok:lombok:1.18.4")

}

allprojects{
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("nu.studer.jooq")
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        // compile
        compile("org.jooq:jooq:3.15.4")
        // jooq
        implementation("org.jooq:jooq:3.15.4")
        implementation("org.jooq:jooq-codegen:3.15.4")
        implementation("org.jooq:jooq-meta:3.15.4")

        // annotations
        implementation ("org.jetbrains:annotations:13.0")
        implementation("org.projectlombok:lombok:1.18.4")
        annotationProcessor("org.projectlombok:lombok:1.18.4")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}