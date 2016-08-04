package com.smoothframe.http;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 把json字符串解析成相应的对象
 * Created by Onlydyf on 2015/8/10.
 */
public class FastObjectRequest<T> extends Request<T> {

    private Response.Listener<T> listener;

//    @Override
//    public Map<String, String> getParams() {
//        return params;
//    }

    private Map<String, String> params;
    private Class<T> clazz;

    public FastObjectRequest(int requestType, String url, Map<String, String> params, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(requestType, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.clazz = clazz;
    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        T t = null;
        try {
            String json = new String(response.data, "utf-8");
            if (!"".equals(json) && json != null) {
                t = JSON.parseObject(json, clazz);

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
    }


    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }


    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
    }

    /**
     * 设置自请求时间
     */
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }
}
