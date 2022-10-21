package com.hutcwp.lib

import com.hutcwp.dep.Deps
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * base-library-template.gradle改写
 */
class BaseLibTemplatePlugin implements Plugin<Project> {

    @Delegate
    Project project

    @Override
    void apply(Project project) {
        this.project = project
        apply plugin: 'com.android.library'

        project.android {
            compileSdkVersion Deps.compileSdkVersion
            defaultConfig {
                targetSdkVersion Deps.targetSdkVersion
                minSdkVersion Deps.minSdkVersion
                consumerProguardFiles "consumer-rules.pro"
            }

            buildTypes {
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
        }

    }
}