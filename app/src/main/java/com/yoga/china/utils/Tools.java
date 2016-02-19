package com.yoga.china.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.Properties;

/**
 * 工具类
 * @author sunsiyuan
 *
 */
public class Tools {

    /**
     * Toast
     * @param context
     * @param value
     */
	public static void showToast(Context context,String value){
        if(!isNull(value))
        Toast.makeText(context,value,Toast.LENGTH_SHORT).show();
    }

    public static boolean isNull(String value){
        if(value==null|| value.isEmpty()||value.equals("null")||value.equals("")){
            return true;
        }else
            return false;
    }
	
}
