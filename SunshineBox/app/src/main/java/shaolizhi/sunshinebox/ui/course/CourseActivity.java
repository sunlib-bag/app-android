package shaolizhi.sunshinebox.ui.course;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.data.ConstantData;
import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.base.BaseActivity;

public class CourseActivity extends BaseActivity implements CourseContract.View {

    private String courseId;

    private CourseContract.Presenter presenter;

    private NetworkChangeBroadcast netWorkChangeBroadcast;

    @BindView(R.id.course_act_webview1)
    WebView webView;

    @OnClick(R.id.course_act_imagebutton1)
    public void back() {
        finish();
    }

    @BindView(R.id.course_act_coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.course_act_textview1)
    TextView courseNameTextView;

    @BindView(R.id.course_act_textview2)
    TextView cardViewTextView;

    @BindView(R.id.course_act_button1)
    Button audioButton;

    @OnClick(R.id.course_act_button1)
    public void clickAudioButton() {
        presenter.tryToPlayAudio();
    }

    @BindView(R.id.course_act_button2)
    Button videoButton;

    @OnClick(R.id.course_act_button2)
    public void clickVideoButton() {
        presenter.tryToPlayVideo();
    }

    @BindView(R.id.course_act_button3)
    Button lastLessonButton;

    @OnClick(R.id.course_act_button3)
    public void clickLastLessonButton() {
        presenter.goToTheLastLesson();
    }

    @BindView(R.id.course_act_button4)
    Button nextLessonButton;

    @OnClick(R.id.course_act_button4)
    public void clickNextLessonButton() {
        presenter.goToTheNextLesson();
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void created(Bundle bundle) {
        presenter = new CoursePresenter(this);
        presenter.start();
    }

    @Override
    protected void resumed() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeBroadcast = new NetworkChangeBroadcast();
        registerReceiver(netWorkChangeBroadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(netWorkChangeBroadcast);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public String getCourseIdFromIntent() throws Exception {
        Courses courses = (Courses) getIntent().getSerializableExtra(ConstantData.COURSE);
        if (courses != null) {
            courseId = courses.getCourse_id();
            return courseId;
        } else {
            if (courseId != null) {
                return courseId;
            } else {
                throw new Exception("Can not get courseId.");
            }
        }
    }

    @Override
    public void setAudioButtonEnable(Boolean enable) {
        audioButton.setEnabled(enable);
    }

    @Override
    public void setVideoButtonEnable(Boolean enable) {
        videoButton.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.exit();
    }

    @Override
    public void setAudioButtonText(String text) {
        audioButton.setText(text);
    }

    @Override
    public void setAudioButtonText(int resId) {
        audioButton.setText(resId);
    }

    @Override
    public void setVideoButtonText(String text) {
        videoButton.setText(text);
    }

    @Override
    public void setVideoButtonText(int resId) {
        videoButton.setText(resId);
    }

    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return coordinatorLayout;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setUpView(Courses courses) {
        courseNameTextView.setText(courses.getCourse_name());
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadUrl("http://111.231.71.150/sunshinebox/fuck.html");
    }

    @Override
    public void setCardViewTextViewVisibility(boolean isVisible) {
        if (isVisible) {
            cardViewTextView.setVisibility(View.VISIBLE);
        } else {
            cardViewTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setWebViewVisibility(boolean isVisible) {
        if (isVisible) {
            webView.setVisibility(View.VISIBLE);
        } else {
            webView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setWebViewContent(String url) {
        webView.loadUrl(url);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    private class NetworkChangeBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = false;
//            //获得网络连接服务
//            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
//            //获取wifi连接状态
//            NetworkInfo.State state = null;
//            if (connectivityManager != null) {
//                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//            }
//            //判断是否正在使用wifi网络
//            if (state == NetworkInfo.State.CONNECTED) {
//                success = true;
//            }
//            //获取4G状态
//            if (connectivityManager != null) {
//                state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
//            }
//            //判断是否在使用4G网络
//            if (state == NetworkInfo.State.CONNECTED) {
//                success = true;
//            }
            success = isConnected(CourseActivity.this);
            //如果没有连接成功
            if (!success) {
                presenter.networkChanged(false);
            } else {
                presenter.networkChanged(true);
            }
        }
    }
}
