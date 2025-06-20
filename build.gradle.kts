// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.10.1" apply false
    //id("org.jetbrains.kotlin.android") version "2.0.10" apply false
    id("com.google.gms.google-services") version "4.4.2"
    id("com.google.devtools.ksp") version "2.0.10-1.0.24" apply false
    kotlin("kapt") version "1.9.23"
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}

allprojects {

}
