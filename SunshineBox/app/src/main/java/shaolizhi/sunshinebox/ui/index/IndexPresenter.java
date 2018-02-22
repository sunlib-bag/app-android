package shaolizhi.sunshinebox.ui.index;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;
import java.util.List;

import shaolizhi.sunshinebox.objectbox.courses.Course;
import shaolizhi.sunshinebox.utils.ToastUtils;

/**
 * Created by 邵励治 on 2018/2/21.
 */

public class IndexPresenter implements IndexContract.Presenter, IndexContract.CallBack {

    private IndexContract.View view;

    private IndexContract.Model model;

    IndexPresenter(IndexContract.View view) {
        this.view = view;
        model = new IndexModel(this);
    }

    @Override
    public void start() {
        view.setUpView();
        tryToLoadDataIntoRecyclerView();
    }

    @Override
    public void tryToLoadDataIntoRecyclerView() {
        Log.e("FUCK", "tryToLoadDataIntoRecyclerView");
        model.requestDataFromNet(view.getCourseType());
    }

    @Override
    public void requestDataFromNetSuccess(List<AVObject> list, IndexContract.CourseType courseType) {
        Log.e("FUCK", "requestDataFromNetSuccess");
        if (list == null) {
            Log.e("FUCK", "list is null");
        } else {
            Log.e("FUCK", "list is not null");
            Log.e("FUCK", "list's size" + list.size());
        }
        List<Course> courses = new ArrayList<>();
        if (list != null) {
            for (AVObject avObject : list) {
                Course course = new Course();
                course.setObjectId(avObject.getObjectId());
                course.setCourseName(avObject.getString("name"));
                switch (courseType) {
                    case READING:
                        course.setSubject("reading");
                        break;
                    case MUSIC:
                        course.setSubject("music");
                        break;
                    case GAME:
                        course.setSubject("game");
                        break;
                    case NURSERY:
                        course.setSubject("nursery");
                        break;
                }
                courses.add(course);
            }
        }
        view.refreshRecyclerView(courses);
    }

    @Override
    public void requestDataFromNetFailure(AVException e) {
        Log.e("FUCK", "requestDataFromNetFailure");
        ToastUtils.showToast(e.getMessage());
    }
}
