package com.hutcwp.dep
/**
 * 依赖配置
 * 为了防止改一个版本就会导致编译时间变长，现在改为动态性了
 * 修改版本的文件名字为 dep_version.yaml，需要修改的话在这里改
 */
class Deps extends BaseDeps {

    /**
     * 开始组装
     * @param closure
     */
    static void compose(@DelegatesTo(DepAssemble.class) Closure closure) {
        def depAssemble = new DepAssemble(closure.owner)
        closure.delegate = depAssemble
        closure()
    }

}
