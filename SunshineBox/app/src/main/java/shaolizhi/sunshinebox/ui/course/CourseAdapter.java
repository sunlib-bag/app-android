package shaolizhi.sunshinebox.ui.course;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import shaolizhi.sunshinebox.R;


/**
 * Created by 邵励治 on 2018/3/6.
 * Perfect Code
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private CourseMediaPlayer courseMediaPlayer;

    private LayoutInflater layoutInflater;

    private Context activity;

    private List<Materials> data;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.course_act_item_imageview1)
        ImageView imageView;

        @BindView(R.id.course_act_item_textview1)
        TextView textView;

        private Materials materials;

        void bind(Materials materials) {
            this.materials = materials;
            Log.e("CourseAdapter.bind()", materials.toString());
            loadMaterialPicture(materials);
            loadMaterialName(materials);
        }

        private void loadMaterialName(Materials materials) {
            if (materials != null) {
                switch (materials.getMaterialType()) {
                    case ALBUM:
                        textView.setText("图集： " + materials.getName());
                        break;
                    case AUDIO:
                        textView.setText("音频： " + materials.getName());
                        break;
                    case VIDEO:
                        textView.setText("视频： " + materials.getName());
                        break;
                    default:
                        break;
                }
            }
        }

        private void loadMaterialPicture(Materials materials) {
            if (materials != null) {
                switch (materials.getMaterialType()) {
                    case ALBUM:
                        Glide.with(activity).load(R.drawable.material_aublum).into(imageView);
                        break;
                    case AUDIO:
                        Glide.with(activity).load(R.drawable.material_audio).into(imageView);
                        break;
                    case VIDEO:
                        Glide.with(activity).load(R.drawable.material_video).into(imageView);
                        break;
                    default:
                        break;
                }
            }
        }

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AVAnalytics.onEvent(activity, "资源被打开数");

            switch (materials.getMaterialType()) {
                case ALBUM:
                    courseMediaPlayer.openAlbum(materials);
                    break;
                case AUDIO:
                    courseMediaPlayer.playAudio(materials);
                    break;
                case VIDEO:
                    courseMediaPlayer.playVideo(materials);
                    break;
                default:
                    break;
            }
        }
    }

    CourseAdapter(Context activity) {
        this.activity = activity;
        this.layoutInflater = LayoutInflater.from(activity);
        if (activity instanceof CourseMediaPlayer) {
            this.courseMediaPlayer = (CourseMediaPlayer) activity;
        }
    }

    void setMaterialData(List<Materials> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.activity_course_item, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
}
