package shaolizhi.sunshinebox.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

/**
 * 由邵励治于2017/12/27创造.
 */

public class MyRefreshLayout extends SwipeRefreshLayout {
    public MyRefreshLayout(Context context) {
        this(context, null);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(Color.parseColor("#393a3f"));
    }
}