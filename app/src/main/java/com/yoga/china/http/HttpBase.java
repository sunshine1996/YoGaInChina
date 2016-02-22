package com.yoga.china.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yoga.china.bean.BaseBean;
import com.yoga.china.http.config.Config;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by sunsiyuan on 16/2/19.
 */
public class HttpBase {

    Type defaultType=new TypeToken<BaseBean<?>>(){}.getType();

    /**
     * 发送回调信息
     *
     * @param handler
     * @param bean
     * @param sign
     */
    protected void sendHandler(Handler handler, BaseBean bean, String sign) {
        Message msg = Message.obtain();
        /*默认失败*/
        msg.what = Config.DEFEAT;
        msg.obj = sign;
        Bundle bundle = new Bundle();
        if (bean != null) {
            if (bean.getCode() == Config.SUCCESS) {
                bundle.putSerializable(Config.DATA, (Serializable) bean.getData());
            }
            msg.what = bean.getCode();
            bundle.putSerializable(Config.MSG, bean.getMsg());
        } else {
            bundle.putString(Config.MSG, "");
        }
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

    /**
     * 判断数据是否为空
     * @param gson
     * @param context
     * @return
     */
    protected boolean judgeData(Gson gson,String context){
        BaseBean baseBean=gson.fromJson(context,defaultType);
        return baseBean.getData().toString().isEmpty();
    }

    /**
     * 获取data为空的bean
     * @param gson
     * @param context
     * @return
     */
    protected BaseBean getBean(Gson gson,String context){
        BaseBean baseBean=gson.fromJson(context,defaultType);
        baseBean.setData(null);
        return baseBean;
    }

}
