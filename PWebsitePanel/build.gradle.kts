plugins {
    id("java")
    id("org.springframework.boot") version("3.2.2")
    id("io.spring.dependency-management") version("1.1.4")
    id("com.github.johnrengelman.shadow") version ("8.1.1")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    testRuntimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")
    testRuntimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(project(":PNettyClient"))
    implementation(project(":PCommon"))
}

springBoot {
    mainClass.set("pl.publicprojects.javawebpanel.JavaWebPanel")
}

tasks.bootJar {
    archiveFileName.set("PPanelPlugin-1.0.0.jar")
}

tasks.test {
    useJUnitPlatform()
}