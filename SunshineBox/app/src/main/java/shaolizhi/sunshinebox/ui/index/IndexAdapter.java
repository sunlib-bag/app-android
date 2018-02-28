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
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private LayoutInflater layoutInflater;

    private Context activity;

    private List<Course> data;

    IndexAdapter(Context activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
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

        @BindView(R.id.index_fgm_item_textview)
        TextView textView;

        void bind(Course course) {
            this.course = course;
            textView.setText(course.getCourseName());
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

        /**
         * @return true 权限检查通过
         * false  权限检查没有通过
         */
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
//            switch (course.getSituation()) {
//                case 0:
//                    //资源尚未下载
//                    AVFile avFile = new AVFile()
//                    break;
//                case 1:
//                    //资源已下载但有更新
//                    break;
//                case 2:
//                    //资源已下载且没更新
//                    break;
//            }
            if (checkPermissions()) {
                File file = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox II");
                if (!file.exists()) {
                    ToastUtils.showToast(String.valueOf(file.mkdirs()));
                }
            }

            AVFile avFile = new AVFile(course.getObjectId() + ".zip", course.getResourcePackageUrl(), new HashMap<String, Object>());
            avFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, AVException e) {
                    if (e == null) {
                        try {
                            ToastUtils.showToast(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox II");
                            OutputStream outputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox II", course.getObjectId() + ".zip"));
                            outputStream.write(bytes, 0, bytes.length);
                            outputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        ToastUtils.showToast(e.getMessage());
                    }
                }
            }, new ProgressCallback() {
                @Override
                public void done(Integer integer) {
                    textView.setText(String.valueOf(integer));
                }
            });
        }
    }
}
