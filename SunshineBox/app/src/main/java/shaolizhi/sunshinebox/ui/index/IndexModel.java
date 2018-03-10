package shaolizhi.sunshinebox.ui.index;

import android.app.Activity;

import com.avos.avoscloud.AVException;

import java.util.List;

import io.objectbox.Box;
import shaolizhi.sunshinebox.data.API;
import shaolizhi.sunshinebox.data.LessonLCBean;
import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.objectbox.courses.ObjectBoxUtils;

/**
 * Created by 邵励治 on 2018/2/22.
 * Perfect Code
 */

class IndexModel implements IndexContract.Model {
    private IndexContract.CallBack callBack;
    private Activity activity;
    private Box<Course> courseBox;


    IndexModel(IndexContract.CallBack callBack, Activity activity) {
        this.callBack = callBack;
        this.activity = activity;
    }

    @Override
    public void requestDataFromNet() {
        API.getAllLesson(new API.QueryListener() {
            @Override
            public void success(List<LessonLCBean> beanList) {
                callBack.requestDataFromNetSuccess(beanList);
            }

            @Override
            public void failure(AVException e) {
                callBack.requestDataFromNetFailure(e);
            }
        });
    }

    @Override
    public void updateDatabase(List<LessonLCBean> netData) {
        List<Course> localData = ObjectBoxUtils.getAllCourseOrderByObjectId(activity);
        ObjectBoxUtils.synchronizeCourse(localData, netData, activity);
        callBack.updateDatabaseSuccess();
    }

    @Override
    public void requestDataFromDatabase(IndexContract.FragmentType fragmentType) {
        List<Course> courseList = ObjectBoxUtils.getCourseOrderByUpdateTime(fragmentType, activity);
        callBack.requestDataFromDatabaseSuccess(courseList);
    }
}
