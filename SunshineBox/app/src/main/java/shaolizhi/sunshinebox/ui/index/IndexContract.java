package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Courses;
import shaolizhi.sunshinebox.ui.base.BasePresenter;
import shaolizhi.sunshinebox.ui.base.BaseView;

/**
 * 由邵励治于2017/12/27创造.
 */

class IndexContract {
    interface View extends BaseView {
        void setRefresh(boolean refresh);

        void setDataInAdapter(@NonNull List<Courses> coursesList);

        Activity getFuckingActivity();

        String getCourseType();
    }

    interface Presenter extends BasePresenter {
        void tryToLoadDataIntoRecyclerView();
    }

    interface Model {
        Boolean isThereAnyDataInTheDatabase(@NonNull String courseType);

        void requestDataFromNet(@NonNull String courseType, @NonNull String maxLastModificationTime);

        List<Courses> requestDataFromDatabase(@NonNull String courseType);

        void storedInTheDatabase(@NonNull IndexBean bean);

        String getMaxModificationTime(@NonNull String courseType);
    }

    interface CallBack {
        void requestDataFromNetSuccess(@NonNull IndexBean bean);

        void requestDataFromNetFailure();
    }
}
