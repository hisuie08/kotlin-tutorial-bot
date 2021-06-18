plugins {
    java
    kotlin("jvm") version "1.5.10"
    application /*追記*/
    id("com.github.johnrengelman.shadow") version "7.0.0" /*追記*/
}

group = "com.github.hisuie08"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases") /*追記*/
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    implementation("net.dv8tion:JDA:4.3.0_277") /*追記*/
    /*
    バージョンはhttps://github.com/DV8FromTheWorld/JDA/releases より参照して適宜変更してください。
    */
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

application{/*applicationブロックごと追記*/
    /*pluginブロック内のapplicationと同時に書いた場合反映が遅れてブロックに警告が出ますが続行して大丈夫です。*/
    mainClass.set("${group}.${rootProject.name}.MainKt")
}