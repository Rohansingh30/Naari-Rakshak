import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {

    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
//video recording
//    id("org.jetbrains.kotlin.android")



}

android {
    namespace = "com.blogspot.softwareengineerrohan.naarirakshak"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.blogspot.softwareengineerrohan.naarirakshak"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
buildFeatures{
    viewBinding = true

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    //noinspection GradleCompatible
    implementation("com.android.support:cardview-v7:28.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.firebase:firebase-firestore:25.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.preference:preference:1.2.1")
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("com.google.firebase:firebase-storage-ktx:21.0.0")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // google servive
    implementation ("com.google.android.gms:play-services-location:21.2.0")
    // google maps

    //   implementation 'com.google.maps.android:android-maps-utils:3.4.0'

//    implementation("androidx.appcompat:design:1.6.1")
    //noinspection GradleCompatible,GradleCompatible
    implementation ("com.android.support:design:28.0.0")


    // circularimageview
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // expandable textview
    implementation("at.blogc:expandabletextview:1.0.5")

    //lifecycle dependency
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.7.0")


//coroutine library
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")


//room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
//lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")


// bom
    implementation("com.google.firebase:firebase-bom:33.0.0")


    implementation ("com.google.android.gms:play-services-location:21.2.0")

//gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.5.0")


    //picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")


    //Shimer effect
    implementation("com.facebook.shimmer:shimmer:0.5.0")

}