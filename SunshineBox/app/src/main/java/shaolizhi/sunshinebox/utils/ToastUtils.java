package shaolizhi.sunshinebox.utils;

import android.widget.Toast;

/**
 * 由邵励治于2016/6/20创造.
 */
public class ToastUtils {
    public static void showToast(String message) {
        Toast.makeText(App.mAppContext, message, Toast.LENGTH_SHORT).show();
    }
}
