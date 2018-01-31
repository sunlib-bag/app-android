package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.data.ConstantData;
import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.course.CourseActivity;

/**
 * 由邵励治于2017/12/11创造.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Courses> coursesList;

    private Activity activity;

    private int[] backgroundList = {R.drawable.background1, R.drawable.background2,
            R.drawable.background3, R.drawable.background4, R.drawable.background5,
            R.drawable.background6, R.drawable.background7, R.drawable.background8,
            R.drawable.background9, R.drawable.background10, R.drawable.background11,
            R.drawable.background13};


    IndexAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        activity = (Activity) context;
    }

    //for view
    void setCoursesList(List<Courses> coursesList) {
        this.coursesList = coursesList;
        notifyDataSetChanged();
    }


    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.item_index, parent, false);
        return new IndexViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        if (holder != null) {
            holder.bind(coursesList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (coursesList != null) {
            return coursesList.size();
        } else {
            return 0;
        }
    }

    class IndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.index_item_imageview)
        ImageView backgroundImageView;

        @BindView(R.id.index_item_textview2)
        TextView courseNameTextview;

        private Courses courses;

        private final int random = new Random().nextInt(15);

        void bind(Courses courses, int position) {
            this.courses = courses;
            courseNameTextview.setText(this.courses.getCourse_name());
            int pos = random + position;
            int background = backgroundList[pos % 12];
            Glide.with(activity).load(background).into(backgroundImageView);
        }

        IndexViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity, CourseActivity.class);
            intent.putExtra(ConstantData.COURSE, this.courses);
            activity.startActivity(intent);
        }
    }
}
