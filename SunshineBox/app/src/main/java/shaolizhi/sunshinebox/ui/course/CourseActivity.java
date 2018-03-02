package shaolizhi.sunshinebox.ui.course;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zzhoujay.markdown.MarkDown;
import com.zzhoujay.markdown.method.LongPressLinkMovementMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.utils.IOUtils;

public class CourseActivity extends BaseActivity implements CourseContract.View {

    private CourseContract.Presenter presenter;

    private static final String RESOURCE_STORAGE_ADDRESS = "resource storage address";

    private String resourceStorageAddress;

    private String jsonStorageAddress;

    private String materialStorageAddress;

    private CourseBean bean;

    @OnClick(R.id.course_act_imagebutton)
    public void back() {
        finish();
    }

    @BindView(R.id.course_act_textview1)
    TextView courseNameTextView;

    @BindView(R.id.course_act_textview2)
    TextView markdownTextView;

    @Override
    protected int layoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void created(Bundle bundle) {
        resourceStorageAddress = getResourceStorageAddress();
        jsonStorageAddress = getJsonAddress();
        materialStorageAddress = getMaterialAddress();
        File jsonFile = getJsonFile();
        File materialFolder = getMaterialFolder();
        bean = deserializeJson(jsonFile);
        markdownTextView.setMovementMethod(LongPressLinkMovementMethod.getInstance());
        markdownTextView.post(new Runnable() {
            @Override
            public void run() {
                long time = System.nanoTime();
                Spanned spanned = MarkDown.fromMarkdown(bean.getContent(), new Html.ImageGetter() {
                    static final String TAG = "Markdown";

                    @Override
                    public Drawable getDrawable(String source) {
                        Log.d(TAG, "getDrawable() called with: source = [" + source + "]");

                        Drawable drawable;
                        try {
                            drawable = drawableFromUrl(source);
                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        } catch (IOException e) {
                            Log.w(TAG, "can't get image", e);
                            drawable = new ColorDrawable(Color.LTGRAY);
                            drawable.setBounds(0, 0, markdownTextView.getWidth() - markdownTextView.getPaddingLeft() - markdownTextView.getPaddingRight(), 400);
                        }
                        return drawable;
                    }
                }, markdownTextView);
                long useTime = System.nanoTime() - time;
                Toast.makeText(getApplicationContext(), "use time: " + useTime + "ns", Toast.LENGTH_LONG).show();
                markdownTextView.setText(spanned);
            }
        });
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    private CourseBean deserializeJson(File jsonFile) {
        CourseBean bean = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(jsonFile);
            String json = IOUtils.fileToString(fileInputStream);
            Gson gson = new Gson();
            bean = gson.fromJson(json, CourseBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @NonNull
    private File getMaterialFolder() {
        File materialFolder = new File(materialStorageAddress);
        if (materialFolder.exists()) {
            Log.e("CourseActivity", "Get MaterialFolder");
        } else {
            Log.e("CourseActivity", "Not Get MaterialFolder");
        }
        return materialFolder;
    }

    @NonNull
    private File getJsonFile() {
        File jsonFile = new File(jsonStorageAddress);
        if (jsonFile.exists()) {
            Log.e("CourseActivity", "Get Json File");
        } else {
            Log.e("CourseActivity", "Not Get Json File");
        }
        return jsonFile;
    }

    @NonNull
    private String getMaterialAddress() {
        return resourceStorageAddress + File.separator + "materials";
    }

    @NonNull
    private String getJsonAddress() {
        return resourceStorageAddress + File.separator + "manifest.json";
    }

    private String getResourceStorageAddress() {
        return getIntent().getStringExtra(RESOURCE_STORAGE_ADDRESS);
    }

    @Override
    protected void resumed() {

    }

    public static Intent newIntent(Context packageContext, String resourceStorageAddress) {
        Intent intent = new Intent(packageContext, CourseActivity.class);
        intent.putExtra(RESOURCE_STORAGE_ADDRESS, resourceStorageAddress);
        return intent;
    }

    @Override
    public void setUpView() {

    }
}
