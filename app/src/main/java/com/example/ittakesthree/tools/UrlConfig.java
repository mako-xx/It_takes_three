package com.example.ittakesthree.tools;

public class UrlConfig {
    public static final boolean LOG_FLAG=true;

    public static final String BaseURL="http://192.168.43.45:8888";
    public static final String URL=BaseURL+"/TravelManager";
    public static final String LOGIN_URL0="/user/login.do";//登录
    public static final String REGIST_URL="/user/add.do";//注册
    public static final String upload_URL="/travel/upload.do";
    public static final String addTravel="/travel/add.do";
    public static final String travel_list="/travel/list.do";
    public static final String zan="/zan/add.do";
    public static final String delZan="/zan/delete.do";
    public static final String getTravelDetail="/travel/getDetail.do";
    public static final String addComment="/comment/add.do";
    public static final String listByTid="/comment/listByTid.do";
    public static final String help_list="/help/list.do";
    public static final String addHelp="/help/add.do";
    public static final String getHelpDetail="/help/getById.do";
    public static final String addAnswer="/answer/add.do";
    public static final String listByHid="/answer/listByHid.do";
    public static final String goods_list="/goods/list.do";
    public static final String getMyTravel="/travel/listByUser.do";
    public static final String delTravel="/travel/delete.do";
    public static final String getMyHelp="/help/listByUser.do";
    public static final String delHelp="/help/delete.do";
    public static final String getMyComment="/comment/listByUser.do";
    public static final String delComment="/comment/delete.do";
    public static final String getMyAnswer="/answer/listByUser.do";
    public static final String delAnswer="/answer/delete.do";
    public static final String changeUser="/user/update.do";
    public static final String getUser="/user/getById.do";

    public static final String ALL_Job="/job/list.do";
    public static final String ALL_Chat="/chat/listByUser.do";
    public static final String singleChat="/chat/singleChat.do";

    public static final String getMyBaoMing="/baoming/listByUser.do";

    public static final String baoming="/baoming/add.do";
    public static final String canclebaoming="/baoming/delete.do";


    public static final String addChat="/chat/add.do";
    public static final String addLuQu="/luqu/add.do";
    public static final String listByBid="/luqu/listByBid.do";
    public static final String gonggao_listByBid="/gonggao/listByBid.do";
    public static final String gonggao_listByUid="/gonggao/listByUid.do";
    public static final String addGongGao="/gonggao/add.do";
    public static final String addShouCang="/shoucang/add.do";
    public static final String delShouCang="/shoucang/delete.do";
    public static final String shoucang_listByUid="/shoucang/listByUid.do";
}
