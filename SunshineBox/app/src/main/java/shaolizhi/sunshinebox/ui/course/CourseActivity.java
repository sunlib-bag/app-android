package shaolizhi.sunshinebox.ui.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.ui.base.BaseActivity;
import shaolizhi.sunshinebox.utils.IOUtils;
import shaolizhi.sunshinebox.utils.ToastUtils;

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

    @BindView(R.id.course_act_textview)
    TextView courseNameTextView;

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
        Log.e("CourseActivity", "Json Analytical Results: " + bean.toString());
        ToastUtils.showToast(resourceStorageAddress);
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
