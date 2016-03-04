package com.jerry.smartlife.bean;

import java.util.List;

/**
 * 页签的新闻数据
 * Created by Administrator on 2016/3/4.
 */
public class PageTagNewsData {
    public int retcode;
    public NewsContent data;

    /*
    {
    "retcode": 200,
    "data": {
        "countcommenturl": "https://www.baidu.com",
        "more": "https://www.baidu.com",
        "title": "厦门",
        "news": [
            {
                "comment": true,
                "commentlist": "https://www.baidu.com",
                "commenturl": "https://www.baidu.com",
                "id": 10001,
                "listimage": "https://www.baidu.com",
                "pubdate": "2016年02月12日 08:20:36",
                "title": "Android开发大会",
                "type": 1,
                "url": "https://www.baidu.com"
            }
        ],
        "topic": [
            {
                "description": "https://www.baidu.com",
                "id": 10001,
                "listimage": "https://www.baidu.com",
                "sort": 1,
                "title": "全国两会在北京举行",
                "url": "https://www.baidu.com"
            }
        ],
        "topnews": [
            {
                "comment": true,
                "commentlist": "https://www.baidu.com",
                "commenturl": "https://www.baidu.com",
                "id": 10001,
                "pubdate": "2016年03月03日 12:32:20",
                "title": "厦门房价持续上升",
                "topimage": "http://pic12.nipic.com/20110217/6757620_105953632124_2.jpg",
                "type": 1,
                "url": "https://www.baidu.com"
            },{
                "comment": true,
                "commentlist": "https://www.baidu.com",
                "commenturl": "https://www.baidu.com",
                "id": 10002,
                "pubdate": "2016年03月02日 11:05:26",
                "title": "太空冲击波",
                "topimage": "http://www.deskcar.com/desktop/else/201298121023/8.jpg",
                "type": 1,
                "url": "https://www.baidu.com"
            },{
                "comment": true,
                "commentlist": "https://www.baidu.com",
                "commenturl": "https://www.baidu.com",
                "id": 10003,
                "pubdate": "2016年02月28日 09:15:52",
                "title": "美丽的风景",
                "topimage": "http://img.win7china.com/NewsUploadFiles/20090823_121319_375_u.jpg",
                "type": 1,
                "url": "https://www.baidu.com"
            },
            {
                "comment": true,
                "commentlist": "https://www.baidu.com",
                "commenturl": "https://www.baidu.com",
                "id": 10004,
                "pubdate": "2016年02月22日 13:22:35",
                "title": "尼康D700可爱的小猫",
                "topimage": "http://www.pp3.cn/uploads/allimg/111118/10562Cb5-13.jpg",
                "type": 1,
                "url": "https://www.baidu.com"
            }
        ]
        }
    }

    * */

    public class NewsContent {
        public String countcommenturl;
        public String more;             // 加载更多数据
        public String title;
        public List<ListNewsData> news;
        public List<TopicData> topic;
        public List<TopNewsData> topnews;

        /**
         * 列表新闻
         */
        public class ListNewsData {
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public String id;
            public String listimage;
            public String pubdate;
            public String title;
            public String type;
            public String url;
        }

        public class TopicData {
            public String description;
            public String id;
            public String listimage;
            public String sort;
            public String title;
            public String url;
        }

        public class TopNewsData {
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public String id;
            public String pubdate;
            public String title;
            public String topimage;
            public String type;
            public String url;
        }
    }


}
