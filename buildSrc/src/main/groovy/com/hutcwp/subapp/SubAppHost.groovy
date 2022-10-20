//package com.hutcwp.subapp
//
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import org.gradle.api.logging.Logger
//
///**
// * 宿主依赖插件
// */
//class SubAppHost implements Plugin<Project> {
//    private Logger logger
//
//    private subAppConfigVersion = 2
//    static final String SUB_APP_PREFIX = "component-"
//    static final String SUB_APP_PREFIX2 = "component_"
//
//    def static taskKeywords = ["ASSEMBLE",
//                               "BUILD",
//                               "aR",
//                               "TINKER",
//                               "INSTALL",
//                               "dependencies",
//                               "sonarqube",
//                               "lint",
//                               "RESGUARD",
//                               "extractApksFor"
//    ]
//
//
//    def static taskKeywordsUpCase = new ArrayList<String>()
//    static {
//        taskKeywords.each {
//            taskKeywordsUpCase.add(it.toUpperCase())
//        }
//    }
//
//    static Set<Project> componentProjects = new TreeSet<>()
//
//    static final String BLOCK_CONFIG_NAME = "sub-app.yaml"
//
//    static final String CONFIG_NAME = "all-app.yaml"
//
//    void createConfig(Project project) {
//        def hostOptions = new SubAppHostOptions()
//        hostOptions.disable = false
//        def logger = project.logger
//        componentProjects.each {
//            def options = SubAppItemPlugin.getSubAppOptions(it)
//            logger.info("${it.path} | ${options}")
//            if (options.isMustInclude()) {
//                hostOptions.mustIncludeComponents.add(it.path)
//            } else {
//                hostOptions.includeComponents.add(it.path)
//            }
//            hostOptions.allComponents.add(it.path)
//        }
//        hostOptions.version = subAppConfigVersion
//        final YamlMapping commented = hostOptions.toYaml()
//        def allAllFile = project.file("$CONFIG_NAME")
//        allAllFile.text = "" //清空
//        allAllFile.append(commented.toString())
//    }
//
//
//    @Override
//    void apply(Project project) {
//        this.logger = project.getLogger()
//        project.rootProject.afterEvaluate {
////            println("allComponentProjects  ${componentProjects}")
//            createConfig(project)
//            addDependencies(project)
//        }
//        println "startParameter : ${project.gradle.startParameter}"
//    }
//
//
//    private boolean isNeedAddImpProject(Project project) {
//        List<String> taskNames = project.gradle.startParameter.taskNames
//        for (String task : taskNames) {
////            println("taskName : ${task} projectPath ${project.path}")
//            // 非完全严谨的判断，但是目前没问题，如果遇到都是放在一个目录下的project才会出现问题
////            boolean isMyProjectTask = task.startsWith(project.path + ":")
//////            if (!isMyProjectTask) {
//////                // 不是这个project，直接跳过依赖增加
//////                return false
//////            }
//            def upperTaskName = task.toUpperCase()
//            for (String keyword : taskKeywordsUpCase) {
//                if (upperTaskName.contains(keyword)) {
//                    return true
//                }
//            }
//        }
//        return false
//    }
//
//    private void addDependencies(Project project) {
////        println "taskName ${project.gradle.startParameter.taskNames}"
//
//        // 所有配置
//        def allProjectPaths = []
//        componentProjects.each {
//            allProjectPaths.add(it.path)
//            project.dependencies.add("implementation", project.project(it.path + ":api"))
//        }
//        if (!isNeedAddImpProject(project)) {
//            // 不是运行的task，不加依赖关系
//            println("${project.path} 不是运行的 task ，不添加依赖关系")
//            return
//        }
//        def impComponentsPath = new ArrayList(allProjectPaths)
//        def blockFile = project.file("$BLOCK_CONFIG_NAME")
//
//        if (blockFile.exists()) {
//            YamlMapping team = Yaml.createYamlInput(blockFile).readYamlMapping()
//            SubAppHostOptions subAppOptions = new SubAppHostOptions(team)
//            println "检测到本地应用 $BLOCK_CONFIG_NAME 文件，将会使用本地文件覆盖默认配置"
//            if (subAppOptions.enable) {
//                impComponentsPath.clear()
//                println("excludeComponentSize " + subAppOptions.excludeComponents.size())
//                if (subAppOptions.excludeComponents.isEmpty()) {
//                    impComponentsPath.addAll(subAppOptions.includeComponents)
//                    impComponentsPath.addAll(subAppOptions.mustIncludeComponents)
//                } else {
//                    def leftPaths = new ArrayList(allProjectPaths)
//                    println "黑名单模式启用，移除之前为 ${leftPaths.size()}"
//                    subAppOptions.excludeComponents.each {
//                        leftPaths.remove(it)
//                    }
//                    impComponentsPath.addAll(leftPaths)
//                    println "移除之后为 ${leftPaths.size()}"
//                }
//            } else {
//                logger.info "本地应用列表开关关闭，将使用所有的 component"
//            }
//            logger.info("${project.path} 配置文件筛选完后需要加入的组件  ${impComponentsPath.join("\n")}")
//        }
//
//        def realAddComponents = new ArrayList()
//        def rootProject = project.rootProject
//        // 增加实现逻辑
//        impComponentsPath.each {
//            def componentProject = rootProject.findProject("$it")
//            if (componentProject == null) {
//                throw new IllegalArgumentException("${it} 项目不存在")
//            }
//            boolean isFeature = false
//            if (project.rootProject.ext.openQigsaw) {
//                for (String feature : QigsawFeatureChecker.getFeatureProject()) {
//                    if (feature == ":" + componentProject.name) {
//                        isFeature = true
//                    }
//                }
//            }
//            if (!isFeature) {
//                //非dynamic-feature模块才让cc-start依赖
//                project.dependencies.add("implementation", componentProject)
//                realAddComponents.add(it)
//            }
//        }
//        println "${project.path} 最后加入的组件为 \n${realAddComponents.join(",")}"
////        throw new IllegalArgumentException("故意的")
//
//    }
//
//}
