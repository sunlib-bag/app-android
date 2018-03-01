package shaolizhi.sunshinebox.ui.index;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
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
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.CourseUtils;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 * Perfect Code
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private HashMap<String, String> downloadingTask;

    private LayoutInflater layoutInflater;

    private Context activity;

    private List<Course> data;

    private Box<Course> courseBox;

    IndexAdapter(Context activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
        downloadingTask = new HashMap<>();
        initObjectBox((Activity) activity);
    }

    private void initObjectBox(Activity activity) {
        CourseUtils courseUtils = CourseUtils.getInstance();
        courseBox = courseUtils.getCourseBox(activity);
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
                    informationTextView.setText("资源未下载");
                    break;
                case 1:
                    informationTextView.setText("资源有更新");
                    break;
                case 2:
                    informationTextView.setText("资源已下载");
                    break;
            }
        }

        private void loadCardBackground(Course course) {
            switch (course.getSubject()) {
                case "nursery":
                    Glide.with(activity).load(R.drawable.nursery).into(imageView);
                    break;
                case "music":
                    Glide.with(activity).load(R.drawable.music).into(imageView);
                    break;
                case "reading":
                    Glide.with(activity).load(R.drawable.reading).into(imageView);
                    break;
                case "game":
                    Glide.with(activity).load(R.drawable.game).into(imageView);
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
                final File folder = createFolder();
                if (checkItemIsDownloading()) {
                    ToastUtils.showToast("现在正在下载视频，请稍等片刻哦");
                } else {
                    switch (course.getSituation()) {
                        case 0:
                            //资源尚未下载
                            downloadZip(folder);
                            break;
                        case 1:
                            //资源已下载但有更新
                            break;
                        case 2:
                            //资源已下载且没更新
                            break;
                    }
                }
            }
        }

        private void downloadZip(File folder) {
            downloadingTask.put(course.getObjectId(), "");
            AVFile zipPackage = new AVFile(course.getObjectId() + ".zip", course.getResourcePackageUrl(), new HashMap<String, Object>());
            final File file = new File(folder, course.getObjectId() + ".zip");

            zipPackage.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    storageZip(bytes, file);
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {
                    informationTextView.setText("正在下载：" + String.valueOf(integer) + "%");
                    downloadZipSuccess(integer, file);
                }
            });
        }

        private void downloadZipSuccess(Integer integer, File file) {
            if (integer == 100) {
                downloadingTask.remove(course.getObjectId());
                informationTextView.setText("资源已下载");
                upDateDatabase(file);
            }
        }

        private void upDateDatabase(File file) {
            course.setResourceStorageAddress(file.getAbsolutePath());
            course.setSituation(2);
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

        private File createFolder() {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox II");
            if (!file.exists()) {
                Log.e("Create Folder: ", String.valueOf(file.mkdirs()));
            }
            return file;
        }
    }
}
