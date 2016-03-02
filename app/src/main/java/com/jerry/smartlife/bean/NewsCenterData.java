package com.jerry.smartlife.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/3/1.
 */
public class NewsCenterData {
    public int retCode;
    public List<NewsData> data;

    /*
    *
    {"retCode":200,"data":[{
            "children":[
            {"id":"1001","title":"北京","type":1,"url":"http://www.baidu.com"},
            {"id":"1002","title":"中国","type":1,"url":"http://www.baidu.com"},
            {"id":"1003","title":"国际","type":1,"url":"http://www.baidu.com"},
            {"id":"1004","title":"体育","type":1,"url":"http://www.baidu.com"},
            {"id":"1005","title":"教育","type":1,"url":"http://www.baidu.com"}
            ],
            "id":"10001","title":"新闻","type":1,"url":"http://www.baidu.com",
            "url1":"http://www.baidu.com","dayurl":"http://www.baidu.com",
            "excurl":"http://www.baidu.com","weekurl":"http://www.baidu.com"
         },{
            "children":[
            {"id":"1001","title":"北京","type":1,"url":"http://www.baidu.com"},
            {"id":"1002","title":"中国","type":1,"url":"http://www.baidu.com"},
            {"id":"1003","title":"国际","type":1,"url":"http://www.baidu.com"},
            {"id":"1004","title":"体育","type":1,"url":"http://www.baidu.com"},
            {"id":"1005","title":"教育","type":1,"url":"http://www.baidu.com"}
            ],
            "id":"10002","title":"专题","type":1,"url":"http://www.baidu.com",
            "url1":"http://www.baidu.com","dayurl":"http://www.baidu.com",
            "excurl":"http://www.baidu.com","weekurl":"http://www.baidu.com"
         },{
            "children":[
            {"id":"1001","title":"北京","type":1,"url":"http://www.baidu.com"},
            {"id":"1002","title":"中国","type":1,"url":"http://www.baidu.com"},
            {"id":"1003","title":"国际","type":1,"url":"http://www.baidu.com"},
            {"id":"1004","title":"体育","type":1,"url":"http://www.baidu.com"},
            {"id":"1005","title":"教育","type":1,"url":"http://www.baidu.com"}
            ],
            "id":"10003","title":"组图","type":1,"url":"http://www.baidu.com",
            "url1":"http://www.baidu.com","dayurl":"http://www.baidu.com",
            "excurl":"http://www.baidu.com","weekurl":"http://www.baidu.com"
         },{
            "children":[
            {"id":"1001","title":"北京","type":1,"url":"http://www.baidu.com"},
            {"id":"1002","title":"中国","type":1,"url":"http://www.baidu.com"},
            {"id":"1003","title":"国际","type":1,"url":"http://www.baidu.com"},
            {"id":"1004","title":"体育","type":1,"url":"http://www.baidu.com"},
            {"id":"1005","title":"教育","type":1,"url":"http://www.baidu.com"}
            ],
            "id":"10004","title":"互动","type":1,"url":"http://www.baidu.com",
            "url1":"http://www.baidu.com","dayurl":"http://www.baidu.com",
            "excurl":"http://www.baidu.com","weekurl":"http://www.baidu.com"
         }],
         "extend":["1","2","3","4","5"]}
    * */

    public class NewsData {
        public List<ViewTagData> children;

        /**
         * 新闻中心的页签分类数据
         */
        public class ViewTagData {
            public String id;
            public String title;
            public int type;
            public String url;
        }
        public String id;
        public String title;
        public int type;

        public String url;
        public String url1;

        public String dayurl;
        public String excurl;

        public String weekurl;

    }

    public List<String> extend;
}
