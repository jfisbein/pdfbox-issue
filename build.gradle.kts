plugins {
  id("java")
}

group = "test"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.apache.pdfbox:pdfbox:3.0.3")
  testImplementation(platform("org.junit:junit-bom:5.10.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}