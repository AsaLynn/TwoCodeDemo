package com.think.twocode;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.encoding.EncodingHandler;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_1) {
            //生成二维码
            try {
                //参数1:二维码的实际内容.2,二维码图形的尺寸.
                Bitmap mBitmap = EncodingHandler.createQRCode("www.baidu.com", 300);//300表示宽高
                iv.setImageBitmap(mBitmap);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this,"生成二维码出错了!",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        } else if (view.getId() == R.id.btn_2) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.CAMERA},
                    0);

        } else if (view.getId() == R.id.btn_3) {

        }
    }

    private void initView() {
        btn1 = (Button) findViewById(R.id.btn_1);
        btn1.setOnClickListener(MainActivity.this);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn2.setOnClickListener(MainActivity.this);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn3.setOnClickListener(MainActivity.this);
        iv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        Bundle bundle = data.getExtras();
        String scanResult = bundle.getString("qr_scan_result");
        Toast.makeText(MainActivity.this, scanResult, Toast.LENGTH_LONG).show();
        Log.i("onActivityResult", "onActivityResult: -->***"+scanResult);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode){
            case 0:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    Toast.makeText(MainActivity.this,"0000",Toast.LENGTH_SHORT).show();
                    if (CommonUtil.isCameraCanUse()) {
                        //扫描二维码
                        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(this,"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(MainActivity.this,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
