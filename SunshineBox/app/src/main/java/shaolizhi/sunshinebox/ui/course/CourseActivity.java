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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @BindView(R.id.course_act_textview3)
    TextView sourceTextView;

    @BindView(R.id.course_act_textview4)
    TextView author;

    @BindView(R.id.course_act_recyclerview)
    RecyclerView recyclerView;

    CourseAdapter courseAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void created(Bundle bundle) {
        //设置RecyclerView
        courseAdapter = new CourseAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(courseAdapter);

        //获取resource的存储地址
        resourceStorageAddress = getResourceStorageAddress();

        //根据resource的地址构建Json和Material的存储地址
        jsonStorageAddress = getJsonAddress();
        materialStorageAddress = getMaterialAddress();

        //根据Json和Material的存储地址构建JsonFile和MaterialFile
        File jsonFile = getJsonFile();
        File materialFolder = getMaterialFolder();

        //解析JSON到bean中
        bean = deserializeJson(jsonFile);

        //读取bean中的内容到UI上
        courseNameTextView.setText(bean.getName());
        sourceTextView.setText(bean.getSource());
        author.setText(bean.getAuthor());

        //解析markdown
        final String markdown = rebuildMarkdown(materialFolder);
        renderingMarkdown(markdown);

        //遍历Json, 获取List<Materials>
        List<Materials> materialsList = getMaterialsList(materialFolder);

        //加载数据
        courseAdapter.setMaterialData(materialsList);
    }

    @NonNull
    private List<Materials> getMaterialsList(File materialFolder) {
        List<Materials> materialsList = new ArrayList<>();
        for (CourseBean.MaterialsBean materialsBean : bean.getMaterials()) {
            //非图集内图片
            if (materialsBean.getParent() == null) {
                Materials materials = new Materials();
                switch (materialsBean.getType()) {
                    case 0:
                        //图集
                        materials.setName(materialsBean.getName());
                        materials.setMaterialType(Materials.MaterialType.ALBUM);
                        materials.setResourceStorageAddress(materialFolder.getAbsolutePath());
                        //获取图集的儿子们
                        List<Materials.AlbumResource> albumResourceList = new ArrayList<>();
                        for (CourseBean.MaterialsBean materialsBean1 : bean.getMaterials()) {
                            if (materialsBean1.getParent() != null) {
                                if (Objects.equals(materialsBean1.getParent(), materialsBean.getFilename())) {
                                    Materials.AlbumResource albumResource = new Materials.AlbumResource();
                                    albumResource.setName(materialsBean1.getName());
                                    albumResource.setOrder(materialsBean1.getAlbum_index());
                                    albumResource.setResourceStorageAddress(materialFolder.getAbsolutePath() + File.separator + materialsBean1.getFilename());
                                    albumResourceList.add(albumResource);
                                }
                            }
                        }
                        materials.setAlbumResourceList(albumResourceList);
                        materials.setOrder(materialsBean.getFile_index());
                        materialsList.add(materials);
                        break;
                    case 1:
                        //音频
                        materials.setName(materialsBean.getName());
                        materials.setMaterialType(Materials.MaterialType.AUDIO);
                        materials.setResourceStorageAddress(materialFolder.getAbsolutePath() + File.separator + materialsBean.getFilename());
                        materials.setOrder(materialsBean.getFile_index());
                        materialsList.add(materials);
                        break;
                    case 2:
                        //视频
                        materials.setName(materialsBean.getName());
                        materials.setMaterialType(Materials.MaterialType.VIDEO);
                        materials.setResourceStorageAddress(materialFolder.getAbsolutePath() + File.separator + materialsBean.getFilename());
                        materials.setOrder(materialsBean.getFile_index());
                        materialsList.add(materials);
                        break;
                    case 3:
                        //markdown内图片, do nothing
                        break;
                    default:
                        break;
                }

            }
        }
        return materialsList;
    }

    private void renderingMarkdown(final String markdown) {
        markdownTextView.setMovementMethod(LongPressLinkMovementMethod.getInstance());
        markdownTextView.post(new Runnable() {
            @Override
            public void run() {
                long time = System.nanoTime();
                Spanned spanned = MarkDown.fromMarkdown(markdown, new Html.ImageGetter() {
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

    private String rebuildMarkdown(File materialFolder) {
        String markdownReplace = "[$1](" + materialFolder.getAbsolutePath() + "/$2)";
        final String markdown = bean.getContent().replaceAll("\\[(\\S+)]\\((\\S+)\\)", markdownReplace);
        Log.e("FUCK:Markdown content", markdown);
        return markdown;
    }

    public static Drawable drawableFromUrl(String url) throws IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap x;

//        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//        connection.connect();
        Log.e("FUCK:URL", url);
        File file = new File(url);
        InputStream inputStream = new FileInputStream(file);
//        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(inputStream);
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
