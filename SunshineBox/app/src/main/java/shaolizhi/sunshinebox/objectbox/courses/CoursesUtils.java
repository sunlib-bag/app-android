package shaolizhi.sunshinebox.objectbox.courses;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import shaolizhi.sunshinebox.utils.App;

/**
 * 由邵励治于2017/12/27创造.
 */

public class CoursesUtils {
    private static CoursesUtils coursesUtils;

    public static CoursesUtils getInstance() {
        if (coursesUtils == null) {
            coursesUtils = new CoursesUtils();
        }
        return coursesUtils;
    }

    private CoursesUtils() {

    }

    public Box<Courses> getCoursesBox(Activity activity) {
        BoxStore boxStore = ((App) activity.getApplication()).getBoxStore();
        return boxStore.boxFor(Courses.class);
    }

    //数据库中是否存在类型为courseType的数据
    public Boolean isCoursesBoxHasData(Box<Courses> coursesBox, String courseType) {
        if (coursesBox != null) {
            QueryBuilder<Courses> builder = coursesBox.query();
            builder.equal(Courses_.course_type, courseType);
            Query<Courses> query = builder.build();
            List<Courses> list = query.find();
            long count = list.size();
            return count != 0;
        } else {
            return false;
        }
    }

    public void printAllObject(Box<Courses> coursesBox) {
        if (coursesBox != null) {
            List<Courses> list = coursesBox.getAll();
            for (Courses item : list) {
                Log.e("FUCK", item.toString());
            }
        }
    }
}
