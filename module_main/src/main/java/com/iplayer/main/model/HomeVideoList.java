package com.iplayer.main.model;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */

public class HomeVideoList {
    /**
     * name : 轮播图
     * type : index_flash
     * tag : index_flash
     * hot : [{"works_id":"","terminal_type":"0","title":"坦克前线，坦克疯狂大战一触即发","url":"http://wap.kkk5.com/index.php?ct=ads&amp;p=302&amp;w=3030000","img_url":"http://a.hiphotos.baidu.com/video/pic/item/cdbf6c81800a19d8c1ad993834fa828ba61e4664.jpg","duration":"","update":"","rating":"0","tag":"","works_type":"browser","label":"","brief":""}]
     */

    private String name;
    private String type;
    private String tag;
    /**
     * works_id :
     * terminal_type : 0
     * title : 坦克前线，坦克疯狂大战一触即发
     * url : http://wap.kkk5.com/index.php?ct=ads&amp;p=302&amp;w=3030000
     * img_url : http://a.hiphotos.baidu.com/video/pic/item/cdbf6c81800a19d8c1ad993834fa828ba61e4664.jpg
     * duration :
     * update :
     * rating : 0
     * tag :
     * works_type : browser
     * label :
     * brief :
     */

    private List<HotEntity> hot;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setHot(List<HotEntity> hot) {
        this.hot = hot;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }

    public List<HotEntity> getHot() {
        return hot;
    }

    public static class HotEntity {
        private String works_id;
        private String terminal_type;
        private String title;
        private String url;
        private String img_url;
        private String duration;
        private String update;
        private String rating;
        private String tag;
        private String works_type;
        private String label;
        private String brief;

        public void setWorks_id(String works_id) {
            this.works_id = works_id;
        }

        public void setTerminal_type(String terminal_type) {
            this.terminal_type = terminal_type;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setWorks_type(String works_type) {
            this.works_type = works_type;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getWorks_id() {
            return works_id;
        }

        public String getTerminal_type() {
            return terminal_type;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public String getImg_url() {
            return img_url;
        }

        public String getDuration() {
            return duration;
        }

        public String getUpdate() {
            return update;
        }

        public String getRating() {
            return rating;
        }

        public String getTag() {
            return tag;
        }

        public String getWorks_type() {
            return works_type;
        }

        public String getLabel() {
            return label;
        }

        public String getBrief() {
            return brief;
        }
    }
}
