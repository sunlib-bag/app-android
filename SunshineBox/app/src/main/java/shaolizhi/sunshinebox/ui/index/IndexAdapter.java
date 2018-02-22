package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
    public void onBindViewHolder(IndexViewHolder holder, int position) {
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
        @BindView(R.id.index_fgm_item_imageview)
        ImageView imageView;

        @BindView(R.id.index_fgm_item_textview)
        TextView textView;

        void bind(Course course) {
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

        @Override
        public void onClick(View v) {
            ToastUtils.showToast("点击！");
        }
    }
}
