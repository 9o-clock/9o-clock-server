plugins {
    kotlin("plugin.jpa") version "1.7.20"
}

dependencies {
    implementation(project(":applications:shard"))
    testImplementation(project(":applications:shard"))
    implementation(project(":infrastructures-data:data-jpa"))
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}