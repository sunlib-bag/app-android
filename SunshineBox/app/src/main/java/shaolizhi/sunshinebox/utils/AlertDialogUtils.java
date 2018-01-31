package shaolizhi.sunshinebox.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 由邵励治于2017/10/30创造.
 */

public class AlertDialogUtils {

    public static void showAlertDialog(Activity context, String title, String message, DialogInterface.OnClickListener listener) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", listener);
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = DpToPxUtils.dpToPx(App.mAppContext, 541);
        window.setAttributes(params);
        try {
            Field mAlert = android.support.v7.app.AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(alertDialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(Color.parseColor("#747474"));
            mMessageView.setTextSize((float) 16);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void showAlertDialog(Activity context, String title, String message, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener cancelListener) {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("取消", cancelListener);
        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
        params.width = DpToPxUtils.dpToPx(App.mAppContext, 541);
        window.setAttributes(params);
        try {
            Field mAlert = android.support.v7.app.AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(alertDialog);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextColor(Color.parseColor("#747474"));
            mMessageView.setTextSize((float) 16);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

//    public static void showAlertDialog(Activity activity, String title, String message, TextView.OnClickListener listener) {
//        android.support.v7.app.AlertDialog.Builder builder =
//                new android.support.v7.app.AlertDialog.Builder(activity);
//        builder.setView(R.layout.dialog_wechat);
//        android.support.v7.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        Window window = alertDialog.getWindow();
////        window.setContentView(R.layout.dialog_wechat);
//        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
//        params.width = (int) activity.getResources().getDimension(x541);
//        params.height = (int) activity.getResources().getDimension(x312);
//        window.setAttributes(params);
//        TextView titleText = alertDialog.findViewById(R.id.wechat_dialog_title);
//        if (titleText != null) {
//            titleText.setText(title);
//        }
//        TextView messageText = alertDialog.findViewById(R.id.wechat_dialog_message);
//        if (messageText != null) {
//            messageText.setText(message);
//        }
//        TextView button = alertDialog.findViewById(R.id.wechat_dialog_sure);
//        if (button != null) {
//            button.setOnClickListener(listener);
//        }
//
//    }

    /*
     * 系统提供的AlertDialog，使用反射改造
     */
//    public static void showAlertDialog(Activity context, String title, String message, DialogInterface.OnClickListener listener) {
//        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setCancelable(false);
//        builder.setPositiveButton("确定", listener);
//        android.support.v7.app.AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();
//        params.width = (int) context.getResources().getDimension(x570);
//        params.height = (int) context.getResources().getDimension(x320);
//        alertDialog.getWindow().setAttributes(params);
//        try {
//            Field mAlert = android.support.v7.app.AlertDialog.class.getDeclaredField("mAlert");
//            mAlert.setAccessible(true);
//            Object mAlertController = mAlert.get(alertDialog);
//            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
//            mMessage.setAccessible(true);
//            TextView mMessageView = (TextView) mMessage.get(mAlertController);
//            mMessageView.setTextColor(Color.parseColor("#747474"));
//            mMessageView.setTextSize((float)16);
//        } catch (IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }

}
