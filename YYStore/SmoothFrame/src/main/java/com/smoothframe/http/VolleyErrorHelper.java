package com.smoothframe.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;


/**
 * Volley 错误请求辅助类
 * Created by Onlydyf on 2015/8/10.
 */
public class VolleyErrorHelper {

    public static String getMessage(Object error) {
        if (error instanceof TimeoutError) {
            return "亲，您的手机网络不太顺畅喔~";
        } else if (isServerProblem(error)) {
            return "亲，服务器异常~";
        } else if (isNetWorkPrlblem(error)) {
            return "拜托拜托,亲快开网络~";
        }
        return "网络异常,请稍后再试~";
    }
    /**
     * 无网络连接
     * @param error
     * @return
     */
    private static boolean isNetWorkPrlblem(Object error){
        return (error instanceof NetworkError)||(error instanceof NoConnectionError);
    }

    /**
     * 服务器的响应的一个错误，最有可能的4xx或5xx HTTP状态代码
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error){
        return (error instanceof ServerError)||(error instanceof AuthFailureError);
    }
}
