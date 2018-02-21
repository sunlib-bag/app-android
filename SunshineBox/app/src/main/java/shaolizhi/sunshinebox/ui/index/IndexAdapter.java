package shaolizhi.sunshinebox.ui.index;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import shaolizhi.sunshinebox.R;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 */

public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.IndexViewHolder> {

    private LayoutInflater layoutInflater;

    private Context activity;

    IndexAdapter(Context activity) {
        this.activity = activity;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = layoutInflater.inflate(R.layout.fragment_index_item, parent, false);
        return new IndexViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 32;
    }

    class IndexViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.index_fgm_item_imageview)
        ImageView imageView;

        @BindView(R.id.index_fgm_item_textview)
        TextView textView;

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
