plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.movieexplorerapp"
    compileSdk = 34
    defaultConfig {

        applicationId = "com.example.movieexplorerapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.movieexplorerapp.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", "\"${properties["API_KEY"]}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.hilt:hilt-work:1.2.0")
    val hiltVersion = "2.49"
    val retrofitVersion = "2.9.0"
    val okhttp3Version = "5.0.0-alpha.11"
    val roomVersion = "2.6.1"
    val archCoreVersion = "2.2.0"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation(platform("androidx.compose:compose-bom:2024.02.02"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.02"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // Hilt for Android
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hiltVersion")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

    //okhttp3
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    // serialize
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // gson converter
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // google truth
    testImplementation("com.google.truth:truth:1.1.2")
    androidTestImplementation("com.google.truth:truth:1.1.2")

    // kotlinx.coroutines.test for testing with coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // hilt For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    androidTestImplementation("com.squareup.okhttp3:mockwebserver:$okhttp3Version")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hiltVersion")

    // Test implementation for MockK and JUnit
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("junit:junit:4.13.2")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // AndroidX Core Testing for LiveData testing
    testImplementation("androidx.arch.core:core-testing:$archCoreVersion")
    androidTestImplementation("androidx.arch.core:core-testing:$archCoreVersion")

  // Paging

    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")
    // navigation
    implementation ("androidx.room:room-paging:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    //shared preferences
    implementation("androidx.security:security-crypto:1.0.0")

    //data store
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    androidTestImplementation ("androidx.paging:paging-testing:3.2.1")
    testImplementation ("androidx.paging:paging-testing:3.2.1")
}