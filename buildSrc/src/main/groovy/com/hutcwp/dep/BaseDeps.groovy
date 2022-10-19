package com.hutcwp.dep

import org.gradle.api.Project

/**
 * 依赖配置
 */
class BaseDeps {


    // 为了防止改一个版本就会导致编译时间变长，现在改为动态性了
    // 修改版本的文件名字为 dep_version.yaml，需要修改的话在这里改
    public static DepVersion Version

    public def static final minSdkVersion = 21
    public def static final compileSdkVersion = 29
    public def static final targetSdkVersion = 26

    public def static final versionName = "9.8.8(10000000)"

    //皮肤 resource 文件白名单位置
    public def static final SKIN_ALL_RESOURCE_PATH = []

    public def static final SKIN_TASK_NAME = "assembleSkin"
    public def static final APP_PROJECT_NAME = "cc-start"

    public def static final CC_LINT_VERSION = "1.0.2"

    static void init(Project rootProject){
        Version = new DepVersion(rootProject.file("config/dep_version.yaml"))
    }

}
