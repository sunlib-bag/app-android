package shaolizhi.sunshinebox.ui.index;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.objectbox.Box;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.data.ApiService;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.ObjectBoxUtils;
import shaolizhi.sunshinebox.ui.course.CourseActivity;
import shaolizhi.sunshinebox.utils.AlertDialogUtils;
import shaolizhi.sunshinebox.utils.NewZipUtils;
import shaolizhi.sunshinebox.utils.ServiceGenerator;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 * Perfect Code
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private NetworkStateHelper networkStateHelper;

    private final ApiService apiService = ServiceGenerator.createService(ApiService.class);

    private HashMap<String, String> downloadingTask;

    private LayoutInflater layoutInflater;

    private Context activity;

    private List<Course> data;

    private Box<Course> courseBox;

    private DownloadTask downloadTask;

    IndexAdapter(Context activity) {
        this.activity = activity;
        networkStateHelper = (NetworkStateHelper) activity;
//        networkStateHelper.setNetworkChangedListener(new NetworkStateHelper.NetworkChangedListener() {
//            @Override
//            public void networkChanged(boolean networkState) {
//                if (!networkState) {
//                    if (downloadTask != null && downloadTask.getStatus() == AsyncTask.Status.RUNNING) {
//                        downloadTask.cancel(true);
//                        downloadingTask
//                    }
//                }
//            }
//        });
        layoutInflater = LayoutInflater.from(activity);
        downloadingTask = new HashMap<>();
        initObjectBox((Activity) activity);
    }

    private void initObjectBox(Activity activity) {
        courseBox = ObjectBoxUtils.getCourseBox(activity);
    }

    boolean isDownloadNow() {
        return !(downloadingTask.size() == 0);
    }

    void setCourseData(List<Course> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.fragment_index_item, parent, false);
        return new IndexViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(final IndexViewHolder holder, int position) {
        if (holder != null) {
            holder.bind(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    class IndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Course course;

        @BindView(R.id.index_fgm_item_imageview)
        ImageView imageView;

        @BindView(R.id.index_fgm_item_textview1)
        TextView informationTextView;

        @BindView(R.id.index_fgm_item_textview2)
        TextView courseNameTextView;

        void bind(Course course) {
            this.course = course;
            loadInformation(course);
            loadCourseName(course);
            loadCardBackground(course);
        }

        private void loadCourseName(Course course) {
            courseNameTextView.setText(course.getCourseName());
        }

        private void loadInformation(Course course) {
            switch (course.getSituation()) {
                case 0:
                    informationTextView.setText("点击下载");
                    break;
                case 1:
                    informationTextView.setText("点击更新");
                    break;
                case 2:
                    informationTextView.setText("");
                    break;
            }
        }

        private void loadCardBackground(Course course) {
            switch (course.getSubject()) {
                case "nursery":
                    switch (course.getSituation()) {
                        case 0:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.nursery_gray).into(imageView);
                            break;
                        case 1:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.nursery_gray).into(imageView);
                            break;
                        case 2:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.nursery).into(imageView);
                            break;
                    }
                    break;
                case "music":
                    switch (course.getSituation()) {
                        case 0:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.music_gray).into(imageView);
                            break;
                        case 1:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.music_gray).into(imageView);
                            break;
                        case 2:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.music).into(imageView);
                            break;
                    }
                    break;
                case "reading":
                    switch (course.getSituation()) {
                        case 0:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.reading_gray).into(imageView);
                            break;
                        case 1:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.reading_gray).into(imageView);
                            break;
                        case 2:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.reading).into(imageView);
                            break;
                    }
                    break;
                case "game":
                    switch (course.getSituation()) {
                        case 0:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.game_gray).into(imageView);
                            break;
                        case 1:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.game_gray).into(imageView);
                            break;
                        case 2:
                            Glide.with(activity.getApplicationContext()).load(R.drawable.game).into(imageView);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

        IndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private boolean checkPermissions() {
            //检查运行时权限
            boolean result1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED;
            boolean result2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
            //result1 = true 说明缺少权限
            Log.e("result1", String.valueOf(result1));
            Log.e("result2", String.valueOf(result2));

            //有一个为true（缺少权限），就需要申请
            if (result2) {
                ActivityCompat.requestPermissions((Activity) activity, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
            return !result2;
        }

        @Override
        public void onClick(View v) {
            if (checkPermissions()) {
                final File folder = getRootFolder();
                if (checkItemIsDownloading()) {
                    ToastUtils.showToast("现在正在下载视频，请稍等片刻哦");
                } else {
                    switch (course.getSituation()) {
                        case 0:
                            //资源尚未下载
                            if (networkStateHelper.getNetworkState()) {
                                downloadZip(folder);
                            } else {
                                ToastUtils.showToast("对不起，请联网后再下载课程");
                            }
                            break;
                        case 1:
                            //资源已下载但有更新
                            if (networkStateHelper.getNetworkState()) {
                                AlertDialogUtils.showAlertDialog((Activity) activity, "资源有更新", "是否更新资源？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        downloadZip(folder);
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AVAnalytics.onEvent(activity, "单个课程被打开次数", course.getCourseName());
                                        AVAnalytics.onEvent(activity, "用户打开课程数", AVUser.getCurrentUser().getUsername());
                                        AVAnalytics.onEvent(activity, "类目下课程打开数", course.getSubject());
                                        AVAnalytics.onEvent(activity, "打开课程总数");
                                        activity.startActivity(CourseActivity.newIntent(activity, course.getResourceStorageAddress()));
                                    }
                                });
                            } else {
                                AVAnalytics.onEvent(activity, "单个课程被打开次数", course.getCourseName());
                                AVAnalytics.onEvent(activity, "用户打开课程数", AVUser.getCurrentUser().getUsername());
                                AVAnalytics.onEvent(activity, "类目下课程打开数", course.getSubject());
                                AVAnalytics.onEvent(activity, "打开课程总数");
                                activity.startActivity(CourseActivity.newIntent(activity, course.getResourceStorageAddress()));
                            }
                            break;
                        case 2:
                            //资源已下载且没更新
                            AVAnalytics.onEvent(activity, "单个课程被打开次数", course.getCourseName());
                            AVAnalytics.onEvent(activity, "用户打开课程数", AVUser.getCurrentUser().getUsername());
                            AVAnalytics.onEvent(activity, "类目下课程打开数", course.getSubject());
                            AVAnalytics.onEvent(activity, "打开课程总数");
                            activity.startActivity(CourseActivity.newIntent(activity, course.getResourceStorageAddress()));
                            break;
                    }
                }
            }
        }

        private void downloadZip(final File folder) {
            AVAnalytics.onEvent(activity, "用户下载课程数", AVUser.getCurrentUser().getMobilePhoneNumber());
            downloadingTask.put(course.getObjectId(), "");
            Call<ResponseBody> call = apiService.downloadFileWithDynamicUrl(course.getResourcePackageUrl());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.e("IndexAdapter", "server contacted and has file");
                        informationTextView.setText("等待下载中！");
                        downloadTask = new DownloadTask(response, course.getObjectId() + ".zip", folder, new DownloadTask.DownloadCallback() {
                            @Override
                            public void downloadSuccess(File file) {
                                downloadingTask.remove(course.getObjectId());
                                File outputFolder = decompressZip(file);
                                informationTextView.setText("");
                                switch (course.getSubject()) {
                                    case "nursery":
                                        Glide.with(activity.getApplicationContext()).load(R.drawable.nursery).into(imageView);
                                        break;
                                    case "music":
                                        Glide.with(activity.getApplicationContext()).load(R.drawable.music).into(imageView);
                                        break;
                                    case "reading":
                                        Glide.with(activity.getApplicationContext()).load(R.drawable.reading).into(imageView);
                                        break;
                                    case "game":
                                        Glide.with(activity.getApplicationContext()).load(R.drawable.game).into(imageView);
                                        break;
                                    default:
                                        break;
                                }
                                upDateDatabase(outputFolder);
                            }

                            @Override
                            public void progressUpdate(Long value) {
                                informationTextView.setText("正在下载：" + String.valueOf(value) + "%");
                            }
                        });
                        downloadTask.execute();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    downloadingTask.remove(course.getObjectId());
                }
            });
        }

        @NonNull
        private File decompressZip(File file) {
            File outputFolder = new File(getRootFolder(), course.getObjectId());
            try {
                NewZipUtils.unzip(file.getAbsolutePath(), outputFolder.getAbsolutePath());
//                ZipUtils.decompress(file, outputFolder.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return outputFolder;
        }

        private void upDateDatabase(File file) {
            try {
                course.downloadSuccess(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            courseBox.put(course);
        }

        private void storageZip(byte[] bytes, File file) {
            try {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(bytes, 0, bytes.length);
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        private boolean checkItemIsDownloading() {
            return downloadingTask.get(course.getObjectId()) != null;
        }

        private File getRootFolder() {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox_II");
            if (!file.exists()) {
                Log.e("Create Folder: ", String.valueOf(file.mkdirs()));
            }
            return file;
        }
    }
}
