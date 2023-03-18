package com.hutcwp.mvp.compiler.velocity;

import java.util.Map;

/**
 * 数据model转成velocity认识的map
 *
 * @author g7734 on 2020/5/8
 */
public interface IMapping {

    /**
     * 数据转成map
     *
     * @return map对象
     */
    Map<String, Object> mapping();
}
