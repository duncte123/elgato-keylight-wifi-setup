plugins {
    id("java")
}

group = "me.duncte123"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jmdns:jmdns:3.5.5")
    implementation("ch.qos.logback:logback-classic:1.4.12")
    implementation("com.squareup.okhttp3:okhttp:4.8.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}