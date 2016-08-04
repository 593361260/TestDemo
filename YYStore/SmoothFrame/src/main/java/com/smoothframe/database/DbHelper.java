package com.smoothframe.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DbHelper {

    public String mDataBaseName;
    private DatabaseHelper dbHelper = null;
    private Context mContext;

    //获得数据库操作对象
    public SQLiteDatabase getSqLiteDatabase() {
        return dbHelper.getWritableDatabase();
    }
    private static SQLiteDatabase sqLiteDatabase;
    public DbHelper(Context context, String dataBaseName) {
        this.mDataBaseName = dataBaseName;
        this.mContext = context;
        onCreateDB(mContext, mDataBaseName);
    }

    //创建
    private void onCreateDB(Context context, String dataBaseName) {
//        copyDB(context);
        dbHelper = new DatabaseHelper(context, dataBaseName + ".db");
    }

    /**
     * 检查数据库中的表是否存在.不存在时创建表.
     *
     * @param tableName 表名
     * @param createSql 创建表sql语句
     */
    public void createTable(String tableName, String createSql) {
        //先查询该表数据库中是否存在，存在就不创建，不存在就去创建。
        String queryTable = "select * from sqlite_master where type='table' and name ='" + tableName + "'";
        Cursor cursor = getSqLiteDatabase().rawQuery(queryTable, null);
        int count = cursor.getCount();
        if (count <= 0) {// 该数据库不存在，去创建
            getSqLiteDatabase().execSQL(createSql);
            //eg: db.execSQL("create table " + tableName + "(id integer primary key autoincrement,loginUser varchar(50), userName varchar(50), cardId varchar(30))");
        }
    }

    /**
     * 将一个本地raw路径中的数据库文件 复制进系统用作数据库
     *
     * @param rawDbFile 数据库资源
     * @param dbPath    目标数据库存放文件路径
     * @param dbName    目标数据库名称
     */
    public void copyDB(Context context, String dbPath, String dbName, int rawDbFile) {
//        String dbPath = "data/data/com.busticket/databases";
        File file = new File(dbPath);
        if (!file.exists()) {
            file.mkdir();
        }
//        file = new File(filePath + "/busdata.db");
        file = new File(dbPath + dbName);
        if (file.exists()) {
            return;
        }
        InputStream is = context.getResources().openRawResource(rawDbFile);
//        InputStream is = context.getResources().openRawResource(R.raw.busdata);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dbPath + dbName);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
        } catch (Exception e) {
        } finally {
            try {
                fos.flush();
                fos.close();
                is.close();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 删除数据库
     */
    public boolean delDatabase(Context context, String dataBaseName) {
        return context.deleteDatabase(dataBaseName + ".db");
    }

    /**
     * 情清空表内容
     */
    public void cleanTable(String TableName) {
        String sql = "delete from " + TableName;
        SQLiteDatabase db = getSqLiteDatabase();
        db.execSQL(sql);
        db.close();
    }

    /**
     * 删除表
     */
    public void delTable(String TableName) {
        String sql = "DROP TABLE " + TableName;
        SQLiteDatabase db = getSqLiteDatabase();
        db.execSQL(sql);
        db.close();
    }
/*
//四个数据库实例，查询，修改，删除，条件查询
    *//**
     * 根据输入的字母进行数据库检索
     *//*
    public List<StationEntity> SearchStation(String inputText, String tableName) {
        List<StationEntity> list = new ArrayList<StationEntity>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String text = inputText.toUpperCase();
        String sql = "select * from " + tableName + " where city like'" + text + "%' or shortPY like'" + text + "%' or PY like'" + text +
                "%' order by (case when city = '" + text + "' or shortPY = '" + text + "' or PY = '" + text + "' then 1 else 0 end) desc";
        Log.i("根据输入的字母进行数据库检索SQL", sql);
        try {
            Cursor cursor = db.rawQuery(sql, null);
            int count = cursor.getCount();
            if (count > 0)
                cursor.moveToFirst();// 游标移动至第一位
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    StationEntity entity = new StationEntity();
                    entity.setCity(cursor.getString(cursor
                            .getColumnIndex("city")));
                    Log.i("queryCity", entity.getCity());
                    entity.setShortPY(cursor.getString(cursor
                            .getColumnIndex("shortPY")));
                    entity.setPy(cursor.getString(cursor
                            .getColumnIndex("PY")));
                    entity.setFristPY(cursor.getString(cursor
                            .getColumnIndex("shortPY")).substring(0, 1));
                    list.add(entity);
                    cursor.moveToNext();//游标移至下一个
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return list;
    }

    *//**
     * 添加单个购票人信息
     *//*
    public void addTicketUserInfo(String tableName, String userName,
                                  String cardId) {
        SQLiteDatabase db = getSqLiteDatabase();
        ContentValues values = new ContentValues();
        values.put("loginUser", StaticData.userEntity.getLoginName());
        values.put("userName", userName);
        values.put("cardId", cardId);
        db.insert(tableName, null, values);
        Log.i("insert", "tableName:" + tableName + " data:" + userName
                + cardId);
        db.close();
    }

    *//**
     * 查询常用购票人
     *//*
    public List<TicketUserInfoEntity> queryTicketUserInfo() {
        List<TicketUserInfoEntity> list = new ArrayList<TicketUserInfoEntity>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            String sql = "select * from " + StaticData.DB_TicketUserInfoTableName + " where loginUser='" + StaticData.userEntity.getLoginName() + "'";
            cursor = db.rawQuery(sql, null);
            int count = cursor.getCount();
            cursor.moveToLast();// 游标移动至最后一个
            if (count > 0) {
                for (int i = count; i > 0; i--) {
                    TicketUserInfoEntity entity = new TicketUserInfoEntity();
                    entity.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
                    entity.setCardId(cursor.getString(cursor.getColumnIndex("cardId")));
                    list.add(entity);
                    cursor.moveToPrevious();// 游标移至上一个
                }
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<TicketUserInfoEntity>();
            return list;
        } finally {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }





    *//**
     * 删除常用购票人
     *//*
    public void delHaveToPayOrder(String tableName, String tradeNo) {
        String sql = "delete from " + tableName + " where tradeNo='" + tradeNo + "'";
        SQLiteDatabase db = getSqLiteDatabase();
        db.execSQL(sql);
        db.close();
    }*/
}
