package com.hutcwp.dep


import org.gradle.api.JavaVersion
import org.gradle.api.Project

/**
 * 依赖配置
 */
class DepAssemble {

    @Delegate
    Project project

    @Delegate
    DepTemplate depTemplate


//    @Delegate
//    SubAppTemplate subAppTemplate

    DepAssemble(buildScript) {
        this.project = buildScript.project
        depTemplate = new DepTemplate(this.project)
//        subAppTemplate = new SubAppTemplate(this.project)
    }


    def butterKnife() {
        pluginKapt()
        apply plugin: 'com.jakewharton.butterknife'
        dependencies {
            implementation 'com.jakewharton:butterknife:' + Deps.Version.butterknifeVersion
            kapt 'com.jakewharton:butterknife-compiler:' + Deps.Version.butterknifeVersion
        }
    }


    def implementation(String path) {
        dependencies {
            implementation path
        }
    }


    def api(String path) {
        dependencies {
            api path
        }
    }


    def implementation(Project path) {
        dependencies {
            implementation project
        }
    }

    def api(Project path) {
        dependencies {
            api project
        }
    }

    def fragment() {
        dependencies {
            implementation 'androidx.appcompat:appcompat:' + Deps.Version.androidxSupportVersion, {
                force = true
            }
        }
    }

    def supportLibrary() {
        fragment()
        dependencies {
//            implementation Deps.CONTRAINT_LAYOUT, {
//                force = true
//            }
            implementation 'androidx.constraintlayout:constraintlayout:' + Deps.Version.constraintlayout_version, {
                force = true
            }
            implementation 'androidx.appcompat:appcompat:' + Deps.Version.androidxSupportVersion, {
                force = true
            }
            implementation 'androidx.recyclerview:recyclerview:' + Deps.Version.androidxSupportVersion, {
                force = true
            }
            implementation 'com.google.android.material:material:' + Deps.Version.androidxDesignVersion, {
                force = true
            }
            implementation 'androidx.annotation:annotation:' + Deps.Version.androidxSupportVersion
        }
    }

    /**
     * 接入 kotlin 插件
     * @return
     */
    def kotlin() {
        project.apply plugin: 'kotlin-android'
        def modulePathName = project.path.replaceFirst(":", "").replaceAll(":", "-")
        project.android.kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
            freeCompilerArgs += ['-module-name', modulePathName]
            freeCompilerArgs += ['-Xjvm-default=all']
        }
        def kotlin_version = project.rootProject.ext.kotlin_version
        implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    }

    /**
     * ARouter页面路由
     * @return
     */
    def aRouter() {
        pluginKapt()
        dependencies {
            implementation 'com.alibaba:arouter-api:1.5.0'
            annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
            kapt 'com.alibaba:arouter-compiler:1.2.2'
        }
    }


    /**
     * 版本更新库
     * @return
     */
    def versionUpdate() {
        dependencies {
            implementation 'com.qianwen:okhttp-utils:3.8.0'
            implementation 'com.qianwen:update-app:3.5.2'
            implementation 'com.qianwen:update-app-kotlin:1.2.3'
        }
    }

    /**
     * 签名配置
     * @return
     */
    def signingConfigs() {
        project.android {
            signingConfigs {
                release {
                    storeFile file('../key/signer.jks')
                    storePassword '123456'
                    keyAlias = 'key'
                    keyPassword '123456'
                }
            }
        }
    }


    /**
     * 崩溃库
     * @return
     */
    def crashLib() {
        dependencies {
            // 本地异常捕捉框架：https://github.com/Ereza/CustomActivityOnCrash
            implementation 'cat.ereza:customactivityoncrash:2.2.0'
        }
    }


    /**
     * 数据库
     * @return
     */
    def roomDb() {
        dependencies {
            implementation 'androidx.room:room-runtime:2.2.5'
            implementation "androidx.room:room-ktx:2.2.5"

            kapt "androidx.room:room-compiler:2.2.5"
        }
    }


    def retrofit() {
        dependencies {
            implementation 'com.squareup.retrofit2:retrofit:2.2.0'
            implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
            implementation 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
        }
    }

    def gson() {
        dependencies {
            implementation 'com.google.code.gson:gson:2.8.2'
        }
    }

    def rxlifecycle2() {
        dependencies {
            implementation 'com.trello.rxlifecycle2:rxlifecycle:2.2.1'
            implementation 'com.trello.rxlifecycle2:rxlifecycle-android:2.2.1'
            implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.1'
        }
    }


    def bindPresenter() {
        dependencies {
            implementation project(':library-annotations')
            implementation project(':base_scope:lib-mvp')
            annotationProcessor project(':library-processor')
//            annotationProcessor project(':library-processor')
        }
    }
}