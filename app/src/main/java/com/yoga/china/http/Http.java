package com.yoga.china.http;

import android.os.Handler;

import com.google.gson.Gson;
import com.http.base.HttpHelper;
import com.http.base.HttpStringResult;
import com.yoga.china.bean.BaseBean;
import com.yoga.china.http.config.Config;
import com.yoga.china.utils.Tools;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * 网络请求
 * Created by sunsiyuan on 16/2/22.
 */
public class Http extends HttpBase{

    private static Http instance;

    public static Http getInstance() {
        if(instance==null){
            instance=new Http();
        }
        return instance;
    }


    /**
     * post 处理
     * @param url
     * @param params
     * @param type
     * @param sign
     * @param handler
     */
    public void post(String url, LinkedHashMap params, final Type type, final String sign, final Handler handler) {
        HttpHelper.asyncPost(url, params, new HttpHelper.HttpStringHandler(){

            @Override
            public void handleResponse(HttpStringResult httpStringResult){
                dealData(httpStringResult,handler,sign,type);
            }
        });
    }

    /**
     * get处理
     * @param url
     * @param params
     * @param type
     * @param sign
     * @param handler
     */
    public void get(String url, LinkedHashMap params, final Type type, final String sign, final Handler handler) {
        HttpHelper.asyncPost(url, params, new HttpHelper.HttpStringHandler() {
            @Override
            public void handleResponse(HttpStringResult httpStringResult){
                dealData(httpStringResult,handler,sign,type);
            }
        });
    }

    /**
     * 所有上传文件的接口都要走这个方法
     *
     * @author sunsy 2014年11月10日下午3:35:41
     * @param url
     * @param handler
     * @param params
     * @param files
     */
    private void sendFile(String url,LinkedHashMap params,  HashMap<String, File> files,final Type type,final String sign,final Handler handler
                          ) {
        HttpHelper.asyncFormPost(url, params, files, new HttpHelper.HttpStringHandler() {
            @Override
            public void handleResponse(HttpStringResult httpStringResult){
                dealData(httpStringResult,handler,sign,type);
            }
        });
    }



    /**
     * 数据处理
     * @param httpStringResult
     * @param handler
     * @param sign
     */
    private void dealData(HttpStringResult httpStringResult,Handler handler,String sign,Type type){
        {
            {
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
        }
    }

}
