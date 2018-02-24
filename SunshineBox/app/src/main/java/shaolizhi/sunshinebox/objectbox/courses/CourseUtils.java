package shaolizhi.sunshinebox.objectbox.courses;

import android.app.Activity;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import shaolizhi.sunshinebox.utils.App;

/**
 * 由邵励治于2017/12/27创造.
 */

public class CourseUtils {
    private static CourseUtils courseUtils;

    public static CourseUtils getInstance() {
        if (courseUtils == null) {
            courseUtils = new CourseUtils();
        }
        return courseUtils;
    }

    private CourseUtils() {

    }

    public Box<Course> getCourseBox(Activity activity) {
        BoxStore boxStore = ((App) activity.getApplication()).getBoxStore();
        return boxStore.boxFor(Course.class);
    }

//    //数据库中是否存在类型为courseType的数据
//    public Boolean isCoursesBoxHasData(Box<CoursesBefore> coursesBox, String courseType) {
//        if (coursesBox != null) {
//            QueryBuilder<CoursesBefore> builder = coursesBox.query();
//            builder.equal(Courses_.course_type, courseType);
//            Query<CoursesBefore> query = builder.build();
//            List<CoursesBefore> list = query.find();
//            long count = list.size();
//            return count != 0;
//        } else {
//            return false;
//        }
//    }
//
//    public void printAllObject(Box<CoursesBefore> coursesBox) {
//        if (coursesBox != null) {
//            List<CoursesBefore> list = coursesBox.getAll();
//            for (CoursesBefore item : list) {
//                Log.e("FUCK", item.toString());
//            }
//        }
//    }
}
