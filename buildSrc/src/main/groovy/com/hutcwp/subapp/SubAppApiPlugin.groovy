package com.hutcwp.subapp

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.hutcwp.dep.Deps


/**
 * sub-app-api.gradle改写
 */
class SubAppApiPlugin implements Plugin<Project> {

    @Delegate
    Project project

    @Override
    void apply(Project project) {
        this.project = project
        apply plugin: 'com.android.library'

        apply plugin: 'kotlin-android-extensions'
        apply plugin: 'kotlin-kapt'

        Deps.compose {
            kotlin()
            gson()
            rxJava()
        }

        project.android {

            signingConfigs {
                signer {
                    storeFile file('../key/signer.jks')
                    storePassword '123456'
                    keyAlias = 'key'
                    keyPassword '123456'
                }
            }

            compileSdkVersion Deps.compileSdkVersion

            defaultConfig {
                targetSdkVersion Deps.targetSdkVersion
                minSdkVersion Deps.minSdkVersion
                testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles "consumer-rules.pro"

                project.kapt {
                    arguments {
                        arg("AROUTER_MODULE_NAME", project.getName())
                    }
                }
            }

            buildTypes {
                release {
                    minifyEnabled false
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
                    signingConfig signingConfigs.signer
                }
            }

//            lintOptions {
//                abortOnError true
//                baseline file("lint-baseline.xml")
//                lintConfig(rootProject.file("lint.xml"))
//            }

            compileOptions {
                sourceCompatibility = 1.8
                targetCompatibility = 1.8
            }

        }
    }
}