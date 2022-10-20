package com.hutcwp.mvp.compiler.process;

import com.hutcwp.mvp.compiler.SniperProcessor;
import javax.annotation.processing.RoundEnvironment;

/**
 * 注解处理器接口
 */

public interface IProcessor {
    void process(RoundEnvironment roundEnv, SniperProcessor mAbstractProcessor);
}
