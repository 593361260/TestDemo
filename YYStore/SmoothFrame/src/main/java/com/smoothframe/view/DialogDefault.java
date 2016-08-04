package com.smoothframe.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sjkj.myapplication.R;

/**
 * Created by Administrator on 2015/8/19 0019.
 */
public class DialogDefault{

    private View mCustomView;
    private AlertDialog mCustomDialog;
    private LayoutInflater inflate;
    public DialogDefault(Context context, String title, String leftText, String rightText,final OnClickLeft onClickLeft,final OnClickRight onClickRight) {
        inflate = LayoutInflater.from(context);
        mCustomView = inflate.inflate(R.layout.dialog_default, null);
        mCustomDialog = new AlertDialog.Builder(context).show();
        mCustomDialog.dismiss();
        mCustomDialog.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mCustomDialog.setContentView(mCustomView);
        TextView tvTitle = (TextView) mCustomDialog.findViewById(R.id.tvTitle);
        TextView tvRight = (TextView) mCustomDialog.findViewById(R.id.tvRight);
        TextView tvLeft = (TextView) mCustomDialog.findViewById(R.id.tvLeft);
        tvTitle.setText(title);
        tvLeft.setText(leftText);
        tvRight.setText(rightText);
        tvRight.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                         onClickRight.clickRight();
                    }
                });
        tvLeft.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        onClickLeft.clickLeft();
                    }
                });
    }
    public void show(){
        mCustomDialog.show();
    }
    public  void dismiss(){
        mCustomDialog.dismiss();
    }
    public interface OnClickLeft {
        void clickLeft();
    }
    public interface OnClickRight {
        void clickRight();
    }
    public void setCanceledOnTouchOutside (boolean b){
        mCustomDialog.setCanceledOnTouchOutside(b);
    }

}
