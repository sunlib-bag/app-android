package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;

import com.avos.avoscloud.AVException;

import java.util.List;

import shaolizhi.sunshinebox.data.LessonLCBean;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.ui.base.BasePresenter;
import shaolizhi.sunshinebox.ui.base.BaseView;

/**
 * Created by shaol on 2018/2/21.
 */

public interface IndexContract {
    public enum FragmentType {
        NURSERY, MUSIC, READING, GAME, HEALTHY, LANGUAGE, SOCIAL, SCIENCE, ART
    }

    interface View extends BaseView {

        FragmentType getFragmentType();

        void loadDataToRecyclerView(List<Course> courseList);

        void startRefresh();

        void stopRefresh();

        Activity getFuckingActivity();
    }

    interface Presenter extends BasePresenter {
        void tryToLoadDataIntoRecyclerView();
    }

    interface Model {
        void requestDataFromNet();

        void updateDatabase(List<LessonLCBean> netData);

        void requestDataFromDatabase(FragmentType fragmentType);
    }

    interface CallBack {
        void requestDataFromNetSuccess(List<LessonLCBean> netData);

        void requestDataFromNetFailure(AVException e);

        void updateDatabaseSuccess();

        void requestDataFromDatabaseSuccess(List<Course> localData);
    }
}
