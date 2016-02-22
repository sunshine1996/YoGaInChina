package com.yoga.china.http;

import android.os.Handler;

import com.google.gson.Gson;
import com.http.base.HttpHelper;
import com.http.base.HttpStringResult;
import com.yoga.china.bean.BaseBean;
import com.yoga.china.http.config.Config;
import com.yoga.china.utils.Tools;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * Created by sunsiyuan on 16/2/22.
 */
public class HttpGet extends HttpBase{

    public static HttpGet instance;

    public static HttpGet getInstance() {
        if(instance==null){
            instance=new HttpGet();
        }
        return instance;
    }

    public void get(String url, LinkedHashMap params, final Type type, final String sign, final Handler handler) {
        HttpHelper.asyncPost(url, params, new HttpHelper.HttpStringHandler() {
            @Override
            public void handleResponse(HttpStringResult httpStringResult) {
                BaseBean baseBean = null;
                /*如果回调值为空，发送空数据，返回*/
                if (Tools.isNull(httpStringResult)) {
                    sendHandler(handler, baseBean, sign);
                    return;
                }
                Gson gson = new Gson();
                String context = httpStringResult.result;
                baseBean = new BaseBean();
                baseBean.setCode(Config.DEFEAT);
                /*如果后台返回值为空,失败，*/
                if (Tools.isNull(context)) {
                    baseBean.setMsg(httpStringResult.msg);
                    sendHandler(handler, baseBean, sign);
                    return;
                }
                /*判断data是否为空，如果data为空，返回*/
                if (judgeData(gson, context)) {
                    baseBean = getBean(gson, context);
                    sendHandler(handler, baseBean, sign);
                    return;
                }
                baseBean = gson.fromJson(context, type);
                sendHandler(handler, baseBean, sign);
            }
        });
    }

}
