package com.yoga.china.utils.image;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.yoga.china.utils.TimeUtil;
import com.yoga.china.utils.Tools;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 保存图片
 * Created by sunsiyuan on 16/2/18.
 */
public class SaveImageUtil {

    private static SaveImageUtil instance;

    public static SaveImageUtil getInstance() {
        if(instance==null){
            instance=new SaveImageUtil();
        }
        return instance;
    }

    /**
     * 保存图片到SD卡
     *
     * @param originalUri
     * @return
     * @author 孙思远 2015年11月20日 下午3:06:50
     */
    public String savaBitmap2SDCard(Activity mActivity, Uri originalUri,String saveFile) {
        Tools.showToast(mActivity,"uri="+originalUri);
        try {
            /**
             * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
             */

            int degree = ImageUtils.readPictureDegree(originalUri.getPath());
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 2;
            Bitmap photo = BitmapFactory.decodeStream(mActivity.getContentResolver().openInputStream(originalUri), null, bitmapOptions);

            /**
             * 把图片旋转为正的方向
             */
            photo = rotaingImageView(degree, photo);
            return savaBitmap2SDCard(photo, TimeUtil.format(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".jpg",saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /*
     * 旋转图片
	 *
	 * @param angle
	 *
	 * @param bitmap
	 *
	 * @return Bitmap
	 */
    public  Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // matrix.setRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * 存图片到sdcard
     *
     * @author Michael.Zhang
     * @param bitmap
     */
    public static String savaBitmap2SDCard(Bitmap bitmap, String img_name,String saveFile) {
        File file = new File(saveFile);

        if (!file.exists()) {
            file.mkdir();
        }
        File imageFile = new File(file, img_name);

        try {
            imageFile.createNewFile();

            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos); // 降低照片质量保存
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.recycle();
        return imageFile.getPath();
    }

}
