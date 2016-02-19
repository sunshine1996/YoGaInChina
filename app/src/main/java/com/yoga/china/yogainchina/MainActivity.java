package com.yoga.china.yogainchina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yoga.china.utils.Tools;
import com.yoga.china.utils.image.GetImageUtil;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private GetImageUtil getImageUtil;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getImageUtil = new GetImageUtil(this);
        tv = (TextView) findViewById(R.id.tv);
    }


    public void gotoCamera(View view) {
        Tools.showToast(this, "gotoCamera");
        getImageUtil.getPhoto(GetImageUtil.GOTO_CAMERA);
    }

    public void gotoGallery(View view) {
        Tools.showToast(this, "gotoGallery");
        getImageUtil.getPhoto(GetImageUtil.GOTO_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GetImageUtil.GOTO_CAMERA:
                    getImageUtil.getPhoto(GetImageUtil.GOTO_CROP);
                    break;
                case GetImageUtil.GOTO_GALLERY:
                    getImageUtil.saveBitmapFromGallery(data);
                    getImageUtil.getPhoto(GetImageUtil.GOTO_CROP);
                    break;
                case GetImageUtil.GOTO_CROP:
                    ImageLoader.getInstance().displayImage(getImageUtil.getmImagePath().toString(), (ImageView) findViewById(R.id.iv));
                    break;
            }
        }
    }
}
