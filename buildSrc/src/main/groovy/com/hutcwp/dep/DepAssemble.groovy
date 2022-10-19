package com.hutcwp.dep

import groovy.io.FileType
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


//    def implementation(String path) {
//        dependencies {
//            implementation path
//        }
//    }


    def api(String path) {
        dependencies {
            api path
        }
    }


//    def implementation(Project path) {
//        dependencies {
//            implementation project
//        }
//    }

    def api(Project path) {
        dependencies {
            api project
        }
    }

    /**
     * 点击日志，需要把其他几个也抽取出来
     * @return
     */
    def clickLog() {
        dependencies {
            api 'com.netease.cc.mobile:dm-log:1.0.6'
        }
    }


    def aRouter() {
        pluginKapt()
        dependencies {
            implementation 'com.alibaba:arouter-api:1.5.0'
            annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
            kapt 'com.alibaba:arouter-compiler:1.2.2'
        }
    }

}


