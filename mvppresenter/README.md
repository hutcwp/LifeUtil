# mvppresenter

# 功能介绍：
1. 使用注解@BindPresenter，运行时通过反射来动态获取Presenter类型，进而创建具体的Presenter。
2. 内置了MvpActivity，MvpFragment和MvpView等基础类型。
3. 通过@InitAttrConfigs和@InitAttrConfig动态加载定义好的Fragment。

### Jcenter地址：
```
    implementation 'hutcwp:mvppresenter-annotations:1.1'
    implementation 'hutcwp:mvppresenter:1.1'
    annotationProcessor 'hutcwp:mvppresenter-compiler:1.1'
```

如果导入不成功，请在project的build.gradle中加入：
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {url 'https://dl.bintray.com/jfrogcwp/maven'}  //这一行代码
    }
}
```

# 具体用法：
#### @BindPresenter：
创建Activity继承MvpActivity，Persenter继承MvpPresenter和接口继承MvpView。

接口：(示例中为IMain )
```
public interface IMain extends MvpView {
      //定义View层操作接口
      void changeText();
}
```

Activity：(示例中为MainActivity )
```
@BindPresenter(presenter = MainPresenter.class)
public class MainActivity extends MvpActivity<MainPresenter, IMain> implements IMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void changeText() {
        Toast.makeText(this,"hahah", Toast.LENGTH_LONG).show();
    }
}
```

Presenter：(示例中为MainPresenter )
```
public class MainPresenter extends MvpPresenter<IMain> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
```

#### @InitAttrConfigs：（自动加载Fragment到指定布局）
在定义的Activity(必须是继承于MvpActivity)上使用@InitAttrConfigs指定需要加载进当前布局（Activity的布局）的Fragment。具体通过@InitAttrConfig指定，参数是布局id（activity的布局中指定的容器id）和Fragment的class类型。
使用如下：
```
@InitAttrConfigs({
        //@InitAttrConfig(component = 具体的Fragment类, resourceId = 加载到的布局容器id),如果是library中使用请用R2.id.fragment_content
        @InitAttrConfig(component = TestFragment.class, resourceId = R.id.fragment_content)
        @InitAttrConfig(component = TestFragment2.class, resourceId = R.id.fragment_content2)
})

@BindPresenter(presenter = MainPresenter.class)
public class MainActivity extends MvpActivity<MainPresenter, IMain> implements IMain {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //可理解为注入，必须有否则不生效。如果没有指定@InitAttrConfigs[]而使用了下面语句则会报错。
        Injector.injectContainer(this);
    }

    @Override
    public void changeText() {
        Toast.makeText(this,"hahah", Toast.LENGTH_LONG).show();
    }
}
```


----- -分割线 -------

 具体可以参考 中的例子
