package hut.cwp.annotations.mvp;

/**
 * 用于{@link MvpPresenter}的数据绑定类，调用setData的时候，
 * 会自动回调{@link MvpActivity}里面通过注解绑定好的方法
 * 并且跟随activity，fragment的生命周期取消绑定，避免内存泄漏
 */

public class LifeData<T> {

    T data;

    /**
     * {@link MvpActivity}通过注解进行数据变化绑定的回调
     */
    public InnerClass<T> innerClass;

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
        if(innerClass != null)innerClass.onDataChange(data);
    }

    public void setInnerClass(InnerClass<T> innerClass){
        this.innerClass = innerClass;
    }

    public interface InnerClass<T>{
        void onDataChange(T t);
    }
}
