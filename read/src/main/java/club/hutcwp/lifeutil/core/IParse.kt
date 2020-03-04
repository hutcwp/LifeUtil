package club.hutcwp.lifeutil.core

/**
 * Created by hutcwp on 2018/10/14 15:38
 * email: caiwenpeng@yy.com
 * YY: 909076244
 *
 *
 * 将网页解析成特定的数据集合 List<T>
</T> */
abstract class IParse<T> {

    /**
     * 解析得到数据集合，具体通过html解析还是json随意
     */
    abstract fun parse(path: String): List<T>

}

