plugins {
    id("java")
}

group = "pl.publicprojects.pcommon"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("io.netty:netty-all:4.1.108.Final")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    compileOnly("org.slf4j:slf4j-api:2.0.13")
    compileOnly("org.apache.logging.log4j:log4j-api:2.23.1")
    testCompileOnly("org.slf4j:slf4j-api:2.0.13")
    testCompileOnly("org.apache.logging.log4j:log4j-api:2.23.1")
}

tasks.test {
    useJUnitPlatform()
}