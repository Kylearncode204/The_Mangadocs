plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "kytallo.com.themangadocs"
    compileSdk = 34

    defaultConfig {
        applicationId = "kytallo.com.themangadocs"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    dependencies {
        implementation ("androidx.appcompat:appcompat:1.6.1")
        implementation ("androidx.recyclerview:recyclerview:1.3.2")
        implementation ("com.squareup.retrofit2:retrofit:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation ("com.squareup.picasso:picasso:2.8")
        implementation ("androidx.viewpager:viewpager:1.0.0")
        implementation ("com.google.code.gson:gson:2.10")
        implementation ("com.squareup.picasso:picasso:2.71828")
        implementation ("androidx.cardview:cardview:1.0.0")
        implementation ("com.google.android.material:material:1.9.0")
    }
}