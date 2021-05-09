package hut.cwp.compiler.process;

import javax.annotation.processing.RoundEnvironment;

import hut.cwp.compiler.SniperProcessor;

/**
 * 注解处理器接口
 */

public interface IProcessor {
    void process(RoundEnvironment roundEnv, SniperProcessor mAbstractProcessor);
}
