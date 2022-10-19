package com.hutcwp.dep

import org.yaml.snakeyaml.Yaml


class DepVersion implements GroovyInterceptable {

    Map<String, String> config
    File configFile

    DepVersion(File configFile) {
        this.configFile = configFile;
        def yaml = new Yaml()
        this.config = yaml.load(configFile.text)
    }

    @Override
    Object getProperty(String propertyName) {
        if (!this.config.containsKey(propertyName)) {
            throw new IllegalArgumentException("没有在 ${this.configFile.path} 找到 ${propertyName} 配置" )
        }
        return this.config[propertyName]
    }
}
