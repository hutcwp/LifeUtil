package club.hutcwp.lifeutil.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hutcwp on 2020-03-04 17:07
 * email: caiwenpeng@yy.com
 * YY: 909076244
 **/
public class PhotoBean {

    /**
     * category : All
     * page : 1
     * results : [{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736776","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzuqwn06j30dw0dv0u4.jpg","objectId":"5b4cc6d29f54540031fc0465","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzuqwn06j30dw0dv0u4.jpg","title":"靠谱是什么定义呢"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736824","image_url":"https://wx1.sinaimg.cn/large/0060lm7Tgy1ftbzt9vheij30dw0ihgnd.jpg","objectId":"5b4cc6d267f35600352d8acc","thumb_url":"https://wx1.sinaimg.cn/small/0060lm7Tgy1ftbzt9vheij30dw0ihgnd.jpg","title":"邪不压正"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736832","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzqoe5vyj30dw0damzi.jpg","objectId":"5b4cc6d20b616000310b7f6b","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzqoe5vyj30dw0damzi.jpg","title":"你赢了跟你走，可输了呢"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736867","image_url":"https://wx2.sinaimg.cn/large/0060lm7Tgy1ftbztl4m0hj30u01hcwic.jpg","objectId":"5b4cc6d2fe88c200356f540b","thumb_url":"https://wx2.sinaimg.cn/small/0060lm7Tgy1ftbztl4m0hj30u01hcwic.jpg","title":"点球了"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736968","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzrbrhdoj30dw0cat9y.jpg","objectId":"5b4cc6d22f301e003ba1b942","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzrbrhdoj30dw0cat9y.jpg","title":"89年老阿姨求勾搭"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1737072","image_url":"https://wx2.sinaimg.cn/large/0060lm7Tgy1ftbzrh98u5j30dw07taam.jpg","objectId":"5b4cc6d29f5454003da24e23","thumb_url":"https://wx2.sinaimg.cn/small/0060lm7Tgy1ftbzrh98u5j30dw07taam.jpg","title":"一个小小的测试贴"},{"category":"ZaHui","group_url":"https://www.dbmeinv.com:443/dbgroup/1737190","image_url":"https://wx1.sinaimg.cn/large/0060lm7Tgy1ftbzvm92qvj30dw0dwt9r.jpg","objectId":"5b4cc6d2128fe1005b2dba65","thumb_url":"https://wx1.sinaimg.cn/small/0060lm7Tgy1ftbzvm92qvj30dw0dwt9r.jpg","title":"蒸一对儿可以一起旅行的伴侣或夫妻一起说走就走一起共创美好回忆"},{"category":"QiaoTun","group_url":"https://www.dbmeinv.com:443/dbgroup/1736778","image_url":"https://wx2.sinaimg.cn/large/0060lm7Tgy1ftbzqou1h8j30dw0gswh4.jpg","objectId":"5b4cc477d50eee0031950625","thumb_url":"https://wx2.sinaimg.cn/small/0060lm7Tgy1ftbzqou1h8j30dw0gswh4.jpg","title":"想养手机宠物"},{"category":"QiaoTun","group_url":"https://www.dbmeinv.com:443/dbgroup/1737003","image_url":"https://wx4.sinaimg.cn/large/0060lm7Tgy1ftbztf7bt4j30dw0gtmz9.jpg","objectId":"5b4cc477128fe1005b2dac72","thumb_url":"https://wx4.sinaimg.cn/small/0060lm7Tgy1ftbztf7bt4j30dw0gtmz9.jpg","title":"求勾搭啦"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736697","image_url":"https://wx4.sinaimg.cn/large/0060lm7Tgy1ftauujezulj30dw0k9adc.jpg","objectId":"5b4cc34f0b616000310b6a4a","thumb_url":"https://wx4.sinaimg.cn/small/0060lm7Tgy1ftauujezulj30dw0k9adc.jpg","title":"这么热的夏天怎么办好？"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736794","image_url":"https://wx1.sinaimg.cn/large/0060lm7Tgy1ftc0ny6g0oj30dw0kc76r.jpg","objectId":"5b4cc34f9f54540031fbedd8","thumb_url":"https://wx1.sinaimg.cn/small/0060lm7Tgy1ftc0ny6g0oj30dw0kc76r.jpg","title":"夏天都到了 我的小奶狗呢"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736802","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzqom3zdj30dw0fm41t.jpg","objectId":"5b4cc34f67f35600352d73b2","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzqom3zdj30dw0fm41t.jpg","title":"又快到深夜了 你们想看些什么"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736827","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftc02ztagxj30dw0ooju2.jpg","objectId":"5b4cc34f2f301e003ba1a2a9","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftc02ztagxj30dw0ooju2.jpg","title":"长吗"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736841","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzstc0vrj30dw0iidii.jpg","objectId":"5b4cc34fee920a003c1d263b","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzstc0vrj30dw0iidii.jpg","title":"只能凑合看"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736864","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzsiimvvj30dw0ii76l.jpg","objectId":"5b4cc34fd50eee003194fdda","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzsiimvvj30dw0ii76l.jpg","title":"不发?照和内内了"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736864","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzsyokgnj30dw0dwdh6.jpg","objectId":"5b4cc34f0b616000310b6a47","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzsyokgnj30dw0dwdh6.jpg","title":"不发?照和内内了"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736864","image_url":"https://wx1.sinaimg.cn/large/0060lm7Tgy1ftbzsnvk64j30dw0ii40q.jpg","objectId":"5b4cc34f128fe1005b2da398","thumb_url":"https://wx1.sinaimg.cn/small/0060lm7Tgy1ftbzsnvk64j30dw0ii40q.jpg","title":"不发?照和内内了"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736970","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzrmla69j30dw0j3ad6.jpg","objectId":"5b4cc34fa22b9d003c910c3c","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzrmla69j30dw0j3ad6.jpg","title":"太阳晒屁股啦"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1736993","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbztvs8g0j30dw0hcwgc.jpg","objectId":"5b4cc34fee920a003c1d2636","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbztvs8g0j30dw0hcwgc.jpg","title":"胃痛\u2026\u2026蹲在地上不想起来"},{"category":"MeiTui","group_url":"https://www.dbmeinv.com:443/dbgroup/1737011","image_url":"https://wx3.sinaimg.cn/large/0060lm7Tgy1ftc0odsk55j30dw0ii7b1.jpg","objectId":"5b4cc34e0b616000310b6a42","thumb_url":"https://wx3.sinaimg.cn/small/0060lm7Tgy1ftc0odsk55j30dw0ii7b1.jpg","title":"好丧啊，来一波豆油吧。"}]
     */

    private String category;
    private int page;
    private List<ResultsBean> results;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean implements Serializable {
        /**
         * category : ZaHui
         * group_url : https://www.dbmeinv.com:443/dbgroup/1736776
         * image_url : https://wx3.sinaimg.cn/large/0060lm7Tgy1ftbzuqwn06j30dw0dv0u4.jpg
         * objectId : 5b4cc6d29f54540031fc0465
         * thumb_url : https://wx3.sinaimg.cn/small/0060lm7Tgy1ftbzuqwn06j30dw0dv0u4.jpg
         * title : 靠谱是什么定义呢
         */

        private String category;
        private String group_url;
        private String image_url;
        private String objectId;
        private String thumb_url;
        private String title;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getGroup_url() {
            return group_url;
        }

        public void setGroup_url(String group_url) {
            this.group_url = group_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getThumb_url() {
            return thumb_url;
        }

        public void setThumb_url(String thumb_url) {
            this.thumb_url = thumb_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
