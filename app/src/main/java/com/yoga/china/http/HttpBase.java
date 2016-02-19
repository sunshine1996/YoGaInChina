package com.yoga.china.http;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.yoga.china.bean.BaseBean;
import com.yoga.china.http.config.Config;

import java.io.Serializable;

/**
 * Created by sunsiyuan on 16/2/19.
 */
public class HttpBase {

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
            if (bean.getCode() == 1) {
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

}
