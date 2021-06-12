package com.hutcwp.read.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: LifeUtil$
 * @Package: com.hutcwp.lifeutil.entitys$
 * @ClassName: ArticleDetailBean$
 * @Description:
 * @Author: caiwenpeng
 * @CreateDate: 2020/8/16$ 12:25 AM$
 */
public class ArticleDetailBean {

    /**
     * data : {"_id":"5f0fe631ee6ba981da2af3c2","author":"KunMinX","category":"Article","content":"","createdAt":"2020-07-16 13:31:29","desc":"MVP 和 MVVM 的关系，就像雷锋与雷峰塔的关系","email":"kunminx@gmail.com","images":["https://gank.io/images/5e5413c8422848aa8a50540797488412"],"index":0,"isOriginal":false,"license":"","likeCounts":0,"likes":[],"markdown":"## 前言\r\n\r\n很高兴见到你！\r\n\r\n我是[《Jetpack MVVM 精讲》](https://juejin.im/post/5dafc49b6fb9a04e17209922)的独立原创作者 KunMinX，GitHub star 8.7k，专注于深度思考和 Jetpack MVVM 的分享。\r\n\r\n关于 MVP 和 MVVM 本质和区别的文章，本来我是不想写的，因为经过长达一年的耳濡目染 和对方法论的试炼，相信 **但凡沉下心阅读过**《重学安卓》体系化文章的读者，多已练就 **透过表象迅速抓住本质** 的稀缺能力。\r\n\r\n专栏每天都有新的读者加入，然而没想到的是，1 年了，仍然时不时的会被咨询、或是在各个社区看到人们众说纷纭地在谈论 MVP 和 MVVM 谁\u201c好\u201d谁\u201c坏\u201d。\r\n\r\n\r\n\r\n## 并不是每一个提问都值得被回答\r\n\r\n> 爱因斯坦说，\u201c提出正确的问题，问题就已解决了一半\u201d。\r\n\r\n换言之，并不是每一个提问都值得被回答。**一次提问所包含的信息量，其实远远超出内容本身**。\r\n\r\n透过提问者的提问，几乎可以瞬间感知到，提问者对事实状况的掌握程度，并由此来决定到底值不值得继续交流。\r\n\r\n与\u201c高\u201d质量提问者的交流 让人感到如沐春风 \u2014\u2014 几句话就能自己先把背景交代清楚，然后就某个细节提出自己的困惑 \u2014\u2014 这让我不免想要与之多聊几句，把我知道的毫无保留地分享出去。\r\n\r\n反之，\u201c低\u201d质量提问 让人感到 **不舒服，甚至不对劲** \u2014\u2014 明明不遗余力地在多处 **划重点 反复交代**，明明白纸黑字写得清清楚楚，甚至段落、链接给他指出来，却视而不见，就好像从未发生过。\r\n\r\n>注：我从不在技术交流中使用 \u201c高\u201d、\u201c低\u201d、\u201c好\u201d、\u201c坏\u201d、\u201c轻\u201d、\u201c重\u201d 之类的主观描述，此处只是以多数人方便理解的方式来介绍。文中使用到的主观描述一律加上双引号。\r\n\r\n[更有甚者，为了满足抬杠的快感，不惜浪费彼此的时间 🤔 ... 🙄.jpg](https://images.xiaozhuanlan.com/photo/2020/ea9593d0a5d229a4c9860c55cfc9a306.png)\r\n\r\n## 本质和区别，我只说一遍\r\n\r\n事实上，我并不会去判断来者是否是来抬杠，而只须透过对方所说的话，即可瞬间判断对方说的是事实，还是自顾自地扯淡 \u2014\u2014 **你没法和一个前来扯淡的人交流**，你会发现 这种对话往往 **存在巨大的代沟**，并且抬杠者无意谋求和缝合对事实的理解，他本来就是为了 \u201c来的快\u201d 的精神胜利而来。\r\n\r\n> 事实即 \"就事论事\"，**事实必有特定背景和目的来约束**。一切脱离事实特征的意见和观点都是瞎扯淡，没有讨论的前提、不值得参与 \u2014\u2014 ©KunMinX\r\n\r\n所以，本文只写给那些 真的想搞清楚事实 的有缘人，只为有缘人铺路。\r\n\r\n并且关于 MVP 和 MVVM 各自的本质及区别，我就只说这么一遍，所以请认真阅读。\r\n\r\n\r\n\r\n## 文章目录一览\r\n\r\n- 前言\r\n- 并不是每一个提问都值得被回答\r\n- 本质和区别，我只说一遍\r\n- 先说结论\r\n- 所以二者的区别是什么？\r\n- Jetpack MVVM 和 MVVM 模式的关系\r\n- 我为什么能瞬间感知沟通质量的 \u201c好\u201d 与 \u201c坏\u201d ？\r\n- 综上\r\n\r\n\r\n\r\n## 先说结论\r\n\r\nMVP 本质：**是广义上的架构模式**，适用于面向实体或虚拟用户接口的开发。\r\n\r\n> 它主要是在 MVC 的背景下，通过 **依赖倒置**，来解决 **逻辑复用** 难、**实现更替** 难 的问题。\r\n\r\nMVVM 本质：**是狭义上的架构模式，专用于页面开发**。\r\n\r\n> 它主要是在多人协作的软件工程的背景下，通过只操作 ViewModel 中映射的视图数据 来刷新视图状态，以此来解决 **视图调用的一致性问题** 从而规避不可预期的错误。\r\n\r\n\r\n\r\n## 所以二者的区别是什么？\r\n\r\n区别就在于：\r\n\r\n**一个是广义上的架构**：\r\n\r\n> 你可以通过同一套逻辑去驱动不同品牌设备的实体用户接口（比如不同品牌的耳机线控），或虚拟用户接口（比如 Android 视图，但存在一致性问题而不推荐）；\r\n\r\n![](https://images.xiaozhuanlan.com/photo/2020/d5ac09a6196a70a669f022b270930e1f.png)\r\n\r\n**一个是狭义上的架构**：\r\n\r\n> 专用于可视化页面的开发，通过解决一致性问题 来规避不可预期的错误。\r\n\r\n![](https://images.xiaozhuanlan.com/photo/2020/0d4ba2b509f4c27ec7fd0c16a8b59905.png)\r\n\r\n所以轻易地你就可发现，二者分别适用于 在各自的专场下 解决不同的问题，根本没有可比性，更没有所谓的 谁\u201c好\u201d谁\u201c坏\u201d 之分。\r\n\r\n而且除了没有可比性，二者之间其实也没任何关系，MVP 的特质是 **依赖倒置**，MVVM 的特质是 **数据驱动**，二者没有说谁演化自谁的关系。回到刚刚所说的：\u201c根本就是 特定场景下解决特定问题 的两种截然不同的架构模式\u201d。\r\n\r\n> 没有所谓的 MVVM == MVP + DataBinding，正如没有所谓的 雷峰塔 == 雷锋 + 塔。\r\n\r\n\r\n\r\n## Jetpack MVVM 和 MVVM 模式的关系\r\n\r\nJetpack MVVM 是 MVVM 模式在 Android 开发中的一个具体落实，也即它不仅仅包含了 MVVM 模式用于解决 \u201c视图调用的一致性问题\u201d 这一本质，还兼顾了 Android 页面开发中其他不可预期的错误。\r\n\r\n正如我在[《 Jetpack MVVM 精讲》](https://juejin.im/post/5dafc49b6fb9a04e17209922)中介绍到的：\r\n\r\n> Lifecycle 的存在，主要是为了解决 **生命周期管理 的一致性问题**。\r\n\r\n> LiveData 的存在，主要是为了帮助 新手老手 都能不假思索地 **遵循 通过唯一可信源分发状态 的标准化开发理念**，从而在快速开发过程中 规避一系列 **难以追溯、难以排查、不可预期** 的问题。\r\n\r\n> ViewModel 的存在，主要是为了解决 **状态管理 和 页面通信 的问题**。\r\n\r\n> DataBinding 的存在，主要是为了解决 **视图调用 的一致性问题**。\r\n\r\n> 它们的存在 大都是为了 在软件工程的背景下 解决一致性的问题、将容易出错的操作在后台封装好，**方便使用者快速、稳定、不产生预期外错误地编码**。\r\n\r\n![](https://images.xiaozhuanlan.com/photo/2020/f56c889db87081ad766df2004eefb329.png)\r\n\r\n所以，Jetpack MVVM 的高频核心架构组件，和 iOS、WPF 的实现一定是有区别的，只不过抓住本质，我们就能举一反三，以不变应万变。\r\n\r\n通过[《事关软件工程安全 的 数据驱动 UI 框架 扫盲干货》](https://xiaozhuanlan.com/topic/2356748910)一文的介绍我们可知，**DataBinding 的唯一挑战者是 基于函数式编程的数据驱动 UI 框架**，也即发源自前端 Elm 框架的 React、Flutter、Jetpack Compose、SwiftUI 之流。\r\n\r\n所以 React、Flutter 这种，算不算 MVVM 呢？其实也算。DataBinding 被换下了，但视图调用一致性问题有函数式编程来解决。\r\n\r\n所以 ...\r\n\r\n\r\n\r\n## 我为什么能瞬间感知沟通质量的 \u201c好\u201d 与 \u201c坏\u201d ？\r\n\r\n事实上，在 \u201c先说结论\u201d 一节介绍完本质后，按照惯例，我是会以 \u201c如果这样说还没有理解的话\u201d 来引出下文，开始交代 \u201cBefore\u201d 和 \u201cAfter\u201d 的，\r\n\r\n因为每天都有新的读者加入，为方便新读者能够 结合自己的兴趣 随机阅读，**专栏几乎每一篇文章 都是以独立专辑的完整度来发行**。\r\n\r\n这也是为什么，我的每一篇文章，都当做自己是第一次和读者见面、不遗余力地贯彻 **全网独家的深度思考写作风格**，让每一位新读者都有机会和我注入到文章的灵魂发生交流。\r\n\r\n然而这一次，我不得不小小地任性一把，因为如果上述这样说了一通，还是不理解的话，答案早就写在以下几篇里：\r\n\r\n> [《是 持续增量更新 的 背景缘由甜点》](https://xiaozhuanlan.com/topic/0378514692) 的 \u201c饭后甜点不能当主食吃\u201d 一节 **（推荐）**；\r\n\r\n> [《基本功：是随时随地可受用的 深度思考秘诀》](https://xiaozhuanlan.com/topic/9837051426) 的 \u201c所以如何正确地思考\u201d 一节；\r\n\r\n> [《这是一份 简洁有力的 认知地图》](https://xiaozhuanlan.com/topic/9074561823) 的 \u201c认知地图\u201d 一节；\r\n\r\n> 还有近期在掘金开源的[《独家解析 | Android 深度写作技巧》](https://juejin.im/post/5efedf45e51d4534a67a80a0) 的 \u201c公式\u201d 一节 \u2014\u2014 \r\n\r\n这几处都有 **就正确的思维方式 提供方法论依据 以及手把手示范**。\r\n\r\n一旦熟悉了这套方法论，那些没完没了的争论，你会不会参与？我想大概率不会，因为你一眼就看出 这些言论中不包含基于事实的思考，不过是 **凭主观感觉、个人喜好** 的自说自话。\r\n\r\n参与到这种扯淡中 是对自己的不尊重，所以你不会参与。\r\n\r\n\r\n\r\n## 综上\r\n\r\nMVP 的本质是对 MVC 的依赖倒置，借此来解决 逻辑复用难 以及 实现替换难 的问题，\r\n\r\n> 因为在 MVP 下，UI 逻辑和业务逻辑全在 Presenter 中写，UI 和 Model 的实现，可以随意替换，\r\n\r\n> 也即如上文介绍的，**通过同一套 Presenter 中的逻辑，可以驱动不同品牌不同型号的耳机的线控**。（注意 UI 的全称是 \u201c用户接口\u201d，台湾的术语更准确，叫 \u201c用户介面\u201d。UI 不是狭义上的页面，UI 就是 UI）\r\n\r\nMVVM 的本质是对 View 数据的映射，借此来在软工背景下解决 视图调用的一致性问题。\r\n\r\n> MVP 和 MVVM 二者的区别在于，**前者是广义的架构模式，普遍适用**；**后者是狭义上的架构模式，专用于页面开发**，可以解决例如 视图调用的一致性问题，来规避不可预期的错误。\r\n\r\n> 也即 **MVP 和 MVVM 本质上毫无关系，没有谁演化自谁**。\r\n\r\nJetpack MVVM 是 MVVM 模式在 Android 下的一个落地，也即除了解决 视图调用的一致性问题，还因地制宜地解决了其他一致性问题，从而规避各种不可预期的错误，让软件开发的工作得以完成得又快又好。\r\n\r\n这样说，你理解了吗？\r\n\r\n\r\n\r\n## 版权声明\r\n\r\n本文以 [CC 署名-非商业性使用-禁止演绎 4.0 国际协议](https://creativecommons.org/licenses/by-nc-nd/4.0/deed.zh) 发行。\r\n\r\nCopyright © 2019-present KunMinX\r\n\r\n![](https://images.xiaozhuanlan.com/photo/2020/8fc6f51263babeb544bb4a7dae6cde59.jpg)\r\n\r\n文中提到的 关于 \u201cMVP 和 MVVV 各自的本质及区别\u201d 的具体描述、\u201d用户介面与耳机线控\u201c 的举例、架构设计图例、\u201dDataBinding 与函数式编程数据驱动框架的关系\u201c 的具体描述、\u201dJetpack MVVM 和 MVVM 关系\u201c 的描述、\u201dLifecycle、LiveData、ViewModel、DataBinding 等架构组件的存在意义\u201c 等多处 **对特定现象及其本质的概括，均属于本人独立原创的成果**，本人对此享有最终解释权。\r\n\r\n任何个人或组织在转载全文，或引用本文中上述提到的 描述、举例、图例或本质概括 时，**须注明原作者和出处**。未经授权不得用于洗稿、广告包装等商业用途。\r\n","originalAuthor":"","publishedAt":"2020-07-16 13:31:29","stars":1,"status":0,"tags":[],"title":"是让人 提神醒脑 的 MVP、MVVM 关系精讲！","type":"Android","updatedAt":"2020-07-16 13:31:29","url":"gank://0e2b304647d042c5b67aadc36c032bc5","views":9}
     * status : 100
     */

    private DataBean data;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean implements Serializable {
        /**
         * _id : 5f0fe631ee6ba981da2af3c2
         * author : KunMinX
         * category : Article
         * content :
         * createdAt : 2020-07-16 13:31:29
         * desc : MVP 和 MVVM 的关系，就像雷锋与雷峰塔的关系
         * email : kunminx@gmail.com
         * images : ["https://gank.io/images/5e5413c8422848aa8a50540797488412"]
         * index : 0
         * isOriginal : false
         * license :
         * likeCounts : 0
         * likes : []
         * markdown : ## 前言
         * originalAuthor :
         * publishedAt : 2020-07-16 13:31:29
         * stars : 1
         * status : 0
         * tags : []
         * title : 是让人 提神醒脑 的 MVP、MVVM 关系精讲！
         * type : Android
         * updatedAt : 2020-07-16 13:31:29
         * url : gank://0e2b304647d042c5b67aadc36c032bc5
         * views : 9
         */

        private String _id;
        private String author;
        private String category;
        private String content;
        private String createdAt;
        private String desc;
        private String email;
        private int index;
        private boolean isOriginal;
        private String license;
        private int likeCounts;
        private String markdown;
        private String originalAuthor;
        private String publishedAt;
        private int stars;
        private int status;
        private String title;
        private String type;
        private String updatedAt;
        private String url;
        private int views;
        private List<String> images;
        private List<?> likes;
        private List<?> tags;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isIsOriginal() {
            return isOriginal;
        }

        public void setIsOriginal(boolean isOriginal) {
            this.isOriginal = isOriginal;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public int getLikeCounts() {
            return likeCounts;
        }

        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
        }

        public String getMarkdown() {
            return markdown;
        }

        public void setMarkdown(String markdown) {
            this.markdown = markdown;
        }

        public String getOriginalAuthor() {
            return originalAuthor;
        }

        public void setOriginalAuthor(String originalAuthor) {
            this.originalAuthor = originalAuthor;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<?> getLikes() {
            return likes;
        }

        public void setLikes(List<?> likes) {
            this.likes = likes;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }
    }
}
