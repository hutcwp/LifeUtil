package club.hutcwp.lifeutil.ui.home.sub.picture

/**
 * @ProjectName: LifeUtil$
 * @Package: club.hutcwp.lifeutil.ui.home.sub.picture$
 * @ClassName: Callback$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/13$ 10:30 PM$
 */
interface Callback<T> {
    fun onSuccess(value: T)

    fun onError(t: Throwable)
}