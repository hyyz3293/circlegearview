package com.tool.jackntest.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.blankj.utilcode.util.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PrinterShareUtil {

    /**
     * 判断PrinterShare是否安装
     *
     * @param context
     * @param
     * @return
     */
    public static boolean isAppInstalled(Context context) {
        String packageName = "com.dynamixsoftware.printershare.amazon";
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 安装apk
     *
     * @param context
     */
    public static void startInstallApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = getAssetFileToCacheDir(context, "PrinterShare_10.5.1.apk");
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 把Asset下的apk拷贝到sdcard下 /test/你的包名/cache 目录下
     *
     * @param context
     * @param fileName
     * @return
     */
    public static File getAssetFileToCacheDir(Context context, String fileName) {
        try {
            File cacheDir = getCacheDir("");
            final String cachePath = cacheDir.getAbsolutePath()
                    + File.separator + fileName;
            InputStream is = context.getAssets().open(fileName);
            File file = new File(cachePath);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];

            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取sdcard中的缓存目录
     *
     * @param
     * @return
     */
    public static File getCacheDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 启动图片打印
     *
     * @param context
     * @param
     */
    public static void startPicturesPrinterShare(Context context) {
        String param = "sd/" + "shot.png";
        String cls = "com.dynamixsoftware.printershare.ActivityPrintPictures";
        String type = "image/*";
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(
                "com.dynamixsoftware.printershare.amazon", cls);
        intent = new Intent();
        intent.setComponent(comp);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(param)), type);
        context.startActivity(intent);
    }

    /**
     * 打印word文档
     *
     * @param context
     * @param
     * @创建时间：2015-7-28 下午3:20:26
     * @作者：jack
     * @描述: TODO
     * @retrun void
     */
    public static void startWordPrinterShare(Context context, String param) {
        String cls = "com.dynamixsoftware.printershare.ActivityPrintDocuments";
        String type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(
                "com.dynamixsoftware.printershare.amazon", cls);
        intent = new Intent();
        intent.setComponent(comp);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(new File(param)), type);
        context.startActivity(intent);
    }

    /**
     * 截屏 保存
     */
    public static Bitmap getScreenImage(ScrollView scrollView) {
        View view = scrollView.getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
//		if (bitmap != null) {
//			try {
//				FileUtils.saveBitmap(bitmap, FileUtils.IMAGE_PATH, name);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else {
//		}
        return bitmap;
    }

    /**
     * 截图
     *
     * @param scrollView
     * @return
     */
    public static Bitmap getBitmapByView(ScrollView scrollView) {

//		measureView(scrollView);
//        int h = 0;
//        Bitmap bitmap = null;
//        // 获取scrollview实际高度
//        for (int i = 0; i < scrollView.getChildCount(); i++) {
//            h += scrollView.getChildAt(i).getHeight();
//            scrollView.getChildAt(i).setBackgroundColor(
//                    Color.parseColor("#ffffff"));
//        }
//        // 创建对应大小的bitmap
//        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
//                Bitmap.Config.RGB_565);
//        final Canvas canvas = new Canvas(bitmap);
//        scrollView.draw(canvas);


        View view = scrollView;

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

//        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static boolean saveImageToSD(Bitmap bitmap, int quality) {
        String fname = getFilePath() + "shot.png";
        if (bitmap != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fname);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, quality, stream);
                byte[] bytes = stream.toByteArray();
                fos.write(bytes);
                fos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @SuppressLint("SdCardPath")
    public static String getFilePath() {
        StringBuffer imgPath = new StringBuffer();
        imgPath.append("");
        File file = new File(imgPath.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        return imgPath.toString();
    }

    private static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }


    /**
     * 启动图片打印
     *
     * @param context
     * @param filePath 图片路径
     */
    public static void startPicturesPrinterShare(Context context, String filePath) {
        String cls = "com.dynamixsoftware.printershare.ActivityPrintPictures";
        String type = "image/*";
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(
                "com.dynamixsoftware.printershare.amazon", cls);
        intent = new Intent();
        intent.setComponent(comp);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File f = new File(filePath);
            Uri apkUri = FileProvider.getUriForFile(context, "com.tool.jackntest.fileprovider", f);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, type);

            context.startActivity(intent);
        } else {
            File f = new File(filePath);
            f.isFile();
            if (!f.exists()) {
                Toast.makeText(context,"文件不存在！", Toast.LENGTH_SHORT).show();
                return;
            }
            intent.setDataAndType(Uri.fromFile(f), type);
            context.startActivity(intent);
        }






    }
}
