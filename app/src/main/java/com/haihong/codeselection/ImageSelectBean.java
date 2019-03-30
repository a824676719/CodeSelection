package com.haihong.codeselection;

import java.util.List;

public class ImageSelectBean {
    /**
     * {"code": 200, "message": "\u8bf7\u6c42\u6210\u529f\uff01",
     * "data": {
     * "key": "cc3d3279c47cca04680207ef0ac9fff52ab2754b",
     * "url": "http://47.75.111.150/utils/img/08600141692862534_15314740131807816.jpg/",
     * "hanz": ["\u8bef", "\u6d74", "\u6c28", "\u9e9f"]}}
     */
    private int code;
    private String message;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String key;
        private String url;
        private String room_number;
        private List<String> hanz;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getRoom_number() {
            return room_number;
        }

        public void setRoom_number(String room_number) {
            this.room_number = room_number;
        }

        public List<String> getHanz() {
            return hanz;
        }

        public void setHanz(List<String> hanz) {
            this.hanz = hanz;
        }

    }

    public boolean isSuccess(){
        return this.code == 200;
    }

}
