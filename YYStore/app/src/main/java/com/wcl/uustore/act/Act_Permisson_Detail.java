package com.wcl.uustore.act;

import android.os.Message;

import com.wcl.uustore.baseAct.BaseAct;

/**
 * Created by DoctorY on 2016/4/22.
 * <p/>
 * 权限详情
 */
public class Act_Permisson_Detail extends BaseAct {
    String permission ;

    @Override
    protected void getMessage(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        permission = getIntent().getStringExtra("permission");
    }

    @Override
    protected void request(int flag) {

    }
}
