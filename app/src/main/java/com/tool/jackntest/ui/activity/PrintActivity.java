package com.tool.jackntest.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.tool.jackntest.R;
import com.tool.jackntest.utils.PrinterShareUtil;

import java.util.List;

public class PrintActivity extends AppCompatActivity {
    int REQUESTCODE_FROM_ACTIVITY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        findViewById(R.id.open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {

                        new LFilePicker()
                                .withActivity(PrintActivity.this)
                                .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                                .withStartPath("/storage/emulated/0")
                                .withIsGreater(false)
                                .withMutilyMode(false)
                                .withFileFilter(new String[]{ ".png", ".docx"})
                                .withFileSize(500 * 1024)
                                .withMaxNum(1)
                                .start();
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {

                    }
                }).request();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE_FROM_ACTIVITY) {
                //If it is a file selection mode, you need to get the path collection of all the files selected
                //List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);//Constant.RESULT_INFO == "paths"
                //List<String> list = data.getStringArrayListExtra("paths");
                //Toast.makeText(getApplicationContext(), "selected " + list.size(), Toast.LENGTH_SHORT).show();
                //If it is a folder selection mode, you need to get the folder path of your choice
                String path = data.getStringExtra("path");
                //Toast.makeText(getApplicationContext(), "The selected path is:" + path, Toast.LENGTH_SHORT).show();
                if (PrinterShareUtil.isAppInstalled(this)) {
                    PrinterShareUtil.startPicturesPrinterShare(this,  path);
                } else {
                    ToastUtils.showShort("请先安装PrintShare打印工具");
                }
            }
        }
    }
}
