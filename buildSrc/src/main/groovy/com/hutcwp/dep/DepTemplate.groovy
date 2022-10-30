package com.hutcwp.dep

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import com.hutcwp.subapp.SubAppApiPlugin
import com.hutcwp.lib.AndroidLibTemplatePlugin

class DepTemplate {

    def static subAppFrameworkLocation = "base/sub-app-framework"

    @Delegate
    Project project

    DepTemplate(Project project) {
        this.project = project
    }

    /**
     * 接入 cc-component 模板
     * @param buildScript
     * @return
     */
    def templateSubAppApi() {
        apply plugin: SubAppApiPlugin
    }

    def templateSubApp() {
        apply plugin: SubAppImpPlugin
    }


    /**
     * 接入 library 模板
     * @param buildScript
     * @return
     */
    def templateLibrary() {
        apply plugin : AndroidLibTemplatePlugin
    }

    def pluginKapt() {
        apply plugin: 'kotlin-android'
        apply plugin: 'kotlin-kapt'

        project.kapt {
                arguments {
                    arg("module", project.getName())
                }
            }

        project.android.kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
    }

    def templateBaseLibrary(@DelegatesTo(DepAssemble.class) Closure closure) {
        apply plugin: BaseLibTemplatePlugin
        if(closure == null){
            Deps.compose {
                kotlin()
                log()
            }
        }else {
            Deps.compose(closure)
        }
    }


    def pluginBusinessPackage() {
        apply plugin: TcpPlugin
        if (GitlabCi.isMy()) {
            apply plugin: CheckLayoutClassPlugin
        }

    }

    /**
     * 公共jar放在common-libs 非公共的jar抽离到各自component
     */
    def addCommonLibsCompileOnly() {
        dependencies{
            def commonLibs = project.rootDir.absolutePath + File.separator + "common-libs"
            compileOnly fileTree(dir: commonLibs, include: ['*.jar'])
        }
    }
    def addCommonLibsImplementation() {
        dependencies{
            def commonLibs = project.rootDir.absolutePath + File.separator + "common-libs"
            implementation fileTree(dir: commonLibs, include: ['*.jar'])
        }
    }

}
