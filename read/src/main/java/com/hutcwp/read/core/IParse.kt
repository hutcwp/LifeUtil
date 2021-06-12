package com.hutcwp.read.core

/**
 * Created by hutcwp on 2018/10/14 15:38
 *
 *
 *
 *
 * 将网页解析成特定的数据集合 List<T>
</T> */
abstract class IParse<T> {


    abstract fun parseHtmlFromUrl(path: String): List<T>

}

