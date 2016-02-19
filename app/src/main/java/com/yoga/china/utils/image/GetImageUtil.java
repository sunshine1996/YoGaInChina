package com.yoga.china.utils.image;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.yoga.china.utils.TimeUtil;
import com.yoga.china.yogainchina.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 获取图片
 * Created by sunsiyuan on 16/2/18.
 */
public class GetImageUtil {

    /*去相机*/
    public static final int GOTO_CAMERA = 0x110;
    /*去相册*/
    public static final int GOTO_GALLERY = 0X120;
    /*去裁剪*/
    public static final int GOTO_CROP = 0X119;
    /*保存路径*/
    private String path;
    /*文件保存路径*/
    private String filePath;
    private Activity mActivity;
    /*拍照路径*/
    private Uri mImagePath;

    /**
     * 默认保存路径
     *
     * @param activity
     */
    public GetImageUtil(Activity activity) {
        this.mActivity = activity;
    }


    /**
     * 获取图片路径
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * 获取图片路径
     *
     * @return
     */
    public Uri getmImagePath() {
        return mImagePath;
    }

    /**
     * 通过这个方法来获取照片
     */
    public void getPhoto(int code) {
        switch (code) {
            case GOTO_CAMERA:
                setPath();
                goToCamera();
                break;
            case GOTO_GALLERY:
                setPath();
                goToGallery();
                break;
            case GOTO_CROP:
                cropImageUri();
                break;
        }
    }

    /**
     * 设置图片路径
     */
    private void setPath() {
        /*获取APP名称*/
        String name = mActivity.getResources().getString(R.string.app_name);
        /*获取后缀名称*/
        String last = TimeUtil.format(System.currentTimeMillis(), "yyyyMMddHHmmss") + ".jpg";
        /*获取文件夹名称*/
        filePath=Environment.getExternalStorageDirectory().getPath() + "/" + name;
        /*获取保存路径*/
        path = filePath + "/" + last;
        mImagePath = Uri.parse("file://"+path);
        /*验证文件路径是否存在*/
        vaildFile(filePath);
    }

    /**
     * 验证是否有文件夹
     * @param path
     * @return
     */
    private void vaildFile(String path){
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
    }


    /**
     * 相册
     *
     * @author Michael.Zhang 2013-06-20 17:06:04
     */
    private void goToGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        mActivity.startActivityForResult(intent, GOTO_GALLERY);
    }

    /**
     * 从相册获取的照片要保存到sdcard后才能裁剪
     *
     * @param intent
     * @return
     * @author ZhangYi 2014年5月13日 下午10:56:58
     */
    public void saveBitmapFromGallery(Intent intent) {
        Uri originalUri = intent.getData();
        path=SaveImageUtil.getInstance().savaBitmap2SDCard(mActivity, originalUri,filePath);
        mImagePath=Uri.parse("file://"+path);
    }

    /**
     * 相机
     *
     * @author Michael.Zhang 2013-06-20 16:54:47
     */
    private void goToCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImagePath);
        mActivity.startActivityForResult(intent, GOTO_CAMERA);
    }

    /**
     * 裁剪图片
     *
     * @author Michael.Zhang 2013-11-1 上午11:13:32
     */
    private void cropImageUri() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mImagePath, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("aspectX", 300);
        intent.putExtra("aspectY", 300);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImagePath);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        mActivity.startActivityForResult(intent, GOTO_CROP);
    }


}
