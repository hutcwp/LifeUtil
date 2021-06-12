package com.hutcwp.read.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName: LifeUtil$
 * @Package: com.hutcwp.lifeutil.entitys$
 * @ClassName: RandomGirl$
 * @Description:
 * @Author: kevin
 * @CreateDate: 2020/8/15$ 6:58 PM$
 */
public class RandomGirl {

    /**
     * counts : 1
     * data : [{"_id":"5e5148e76e7524f833c3f7a0","author":"鸢媛","category":"Girl","createdAt":"2020-02-25 08:00:00","desc":"愿此时平淡，若彼时灿烂。","images":["http://gank.io/images/2e75774eac3f497caca35b3de7c50a42"],"likeCounts":2,"publishedAt":"2020-02-25 08:00:00","stars":1,"title":"第6期","type":"Girl","url":"http://gank.io/images/2e75774eac3f497caca35b3de7c50a42","views":680}]
     * status : 100
     */

    private int counts;
    private int status;
    private List<DataBean> data;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * _id : 5e5148e76e7524f833c3f7a0
         * author : 鸢媛
         * category : Girl
         * createdAt : 2020-02-25 08:00:00
         * desc : 愿此时平淡，若彼时灿烂。
         * images : ["http://gank.io/images/2e75774eac3f497caca35b3de7c50a42"]
         * likeCounts : 2
         * publishedAt : 2020-02-25 08:00:00
         * stars : 1
         * title : 第6期
         * type : Girl
         * url : http://gank.io/images/2e75774eac3f497caca35b3de7c50a42
         * views : 680
         */

        private String _id;
        private String author;
        private String category;
        private String createdAt;
        private String desc;
        private int likeCounts;
        private String publishedAt;
        private int stars;
        private String title;
        private String type;
        private String url;
        private int views;
        private List<String> images;

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

        public int getLikeCounts() {
            return likeCounts;
        }

        public void setLikeCounts(int likeCounts) {
            this.likeCounts = likeCounts;
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "_id='" + _id + '\'' +
                    ", author='" + author + '\'' +
                    ", category='" + category + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", likeCounts=" + likeCounts +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", stars=" + stars +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", views=" + views +
                    ", images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RandomGirl{" +
                "counts=" + counts +
                ", status=" + status +
                ", data=" + data +
                '}';
    }
}
