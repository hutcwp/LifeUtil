package com.hutcwp.mvp.compiler.velocity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 数据model => map 转换器
 *
 * @author g7734 on 2020/5/13
 */
public class MappingConverter {

    /**
     * 转换一批实现IMapping接口的数据model
     *
     * @param list 一批实现IMapping接口的数据model
     * @return map列表
     */
    public static List<Map<String, Object>> covert( List<? extends IMapping> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (IMapping item : list) {
            mapList.add(item.mapping());
        }
        return mapList;
    }
}
