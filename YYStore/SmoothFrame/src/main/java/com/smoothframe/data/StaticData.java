package com.smoothframe.data;

import com.smoothframe.database.DbHelper;

/**
 * 用于存放全局公用变量，新增时请详细备注
 */
public class StaticData {
    public static String SHAREPREFERENCENAME="shareperference";//shareperference配置文件的保存名称
    public static DbHelper DBHELPER=null;//sqlOpenHelper操作
}