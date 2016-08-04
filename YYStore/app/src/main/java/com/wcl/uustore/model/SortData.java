package com.wcl.uustore.model;

import java.util.List;

/**
 * Created by DoctorY on 2016/6/14.
 */
public class SortData {


    private int msgCode;

    private List<BodyBean> body;

    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        private String title;
        private String iconUrl;

        private List<ObjectsBean> objects;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public List<ObjectsBean> getObjects() {
            return objects;
        }

        public void setObjects(List<ObjectsBean> objects) {
            this.objects = objects;
        }

        public static class ObjectsBean {
            private int id;
            private String name;
            private String iconUrl;
            private String bg_rgb;
            private String fg_rgb;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getBg_rgb() {
                return bg_rgb;
            }

            public void setBg_rgb(String bg_rgb) {
                this.bg_rgb = bg_rgb;
            }

            public String getFg_rgb() {
                return fg_rgb;
            }

            public void setFg_rgb(String fg_rgb) {
                this.fg_rgb = fg_rgb;
            }
        }
    }
}
