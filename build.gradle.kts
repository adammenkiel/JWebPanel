plugins {
    id("java")
    id("org.springframework.boot") version("3.2.2")
    id("io.spring.dependency-management") version("1.1.4")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

group = "pl.publicprojects.javawebpanel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    testRuntimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
    testRuntimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.slf4j:slf4j-api:2.0.13")


    testCompileOnly("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.netty:netty-all:4.1.108.Final")
    testImplementation(project(":PCommon"))
    testImplementation(project(":PNettyServer"))
}

tasks.test {
    useJUnitPlatform()
}