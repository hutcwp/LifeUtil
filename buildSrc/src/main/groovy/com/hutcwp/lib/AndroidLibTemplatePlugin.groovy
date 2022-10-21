package com.hutcwp.lib

import com.hutcwp.dep.Deps
import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * cc-android-library-template.gradle改写
 */
class AndroidLibTemplatePlugin implements Plugin<Project> {

    @Delegate
    Project project;

    @Override
    void apply(Project project) {
        this.project = project

        apply plugin: 'com.android.library'

//        Deps.compose {
//            checkAndApplyLibraryPlugin()
//            pluginActivityCheck()
//        }

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
                versionName Deps.versionName
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
                debug {
//                    testCoverageEnabled = (gradle.rootProject.ext.coverageEnabled == "true")
                }
                release {
                    minifyEnabled false
                    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
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

            // 指定源文件目录
            sourceSets {
                main {
                    java.srcDirs = ['src/main/java']
                }
                debug {
                    java.srcDirs = ['src/debug/java', 'build/generated/source/apt2/debug']
                }
                release {
                    java.srcDirs = ['src/release/java', 'build/generated/source/apt2/release']
                }
            }
        }

        dependencies {
            implementation fileTree(dir: 'libs', include: ['*.jar'])

            testImplementation 'junit:junit:4.12'
            androidTestImplementation 'androidx.test.ext:junit:1.1.0'
            androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'
        }

    }
}