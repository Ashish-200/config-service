plugins {
    id("java")
    id("org.springframework.boot") version "2.7.2"
    id ("io.spring.dependency-management") version "1.0.13.RELEASE"
 //   id("com.palantir.docker") version "0.34.0"
//    id("com.bmuschko.docker-remote-api") version "9.3.0"

}

group = "ltr.org"
version = "Dev_1.0"
springBoot {
    mainClass.set("ltr.org.config.ConfigServiceApplication")
}
repositories {
    mavenCentral()
    mavenLocal()
}
dependencies {
    implementation ("org.springframework.boot:spring-boot-starter-data-couchbase")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("commons-io:commons-io:2.11.0")
    compileOnly ("org.projectlombok:lombok:1.18.24")
    annotationProcessor ("org.projectlombok:lombok:1.18.24")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")

}
tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

//task<Copy>("unpack") {
//    val bootJar = tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar")
//    dependsOn(bootJar)
//    from(zipTree(bootJar.outputs.files.singleFile))
//    into("build/dependency")
//}
//
//docker {
//    val archiveBaseName = tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar").archiveBaseName.get()
//    name = "${project.group}/$archiveBaseName"
//    copySpec.from(tasks.getByName<Copy>("unpack").outputs).into("dependency")
//    buildArgs(mapOf("DEPENDENCY" to "dependency")) }]