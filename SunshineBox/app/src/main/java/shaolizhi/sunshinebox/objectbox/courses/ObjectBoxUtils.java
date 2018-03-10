package shaolizhi.sunshinebox.objectbox.courses;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import shaolizhi.sunshinebox.data.LessonLCBean;
import shaolizhi.sunshinebox.ui.index.IndexContract;
import shaolizhi.sunshinebox.utils.App;

/**
 * Created by 邵励治 on 2018/3/10.
 * Perfect Code
 */

public class ObjectBoxUtils {

    public static Box<Course> getCourseBox(Activity activity) {
        BoxStore boxStore = ((App) activity.getApplication()).getBoxStore();
        return boxStore.boxFor(Course.class);
    }

    public static Box<Tag> getTagBox(Activity activity) {
        BoxStore boxStore = ((App) activity.getApplication()).getBoxStore();
        return boxStore.boxFor(Tag.class);
    }

    public static Long getCourseIdByObjectId(String objectId, Activity activity) {
        QueryBuilder<Course> builder = getCourseBox(activity).query();
        Query<Course> query = builder.equal(Course_.objectId, objectId).build();
        if (query.find().get(0) == null) {
            return 0L;
        } else {
            return query.find().get(0).getId();
        }
    }

    /**
     * API1: 获取数据库Course表中的全部数据，并按照ObjectId生序排列
     *
     * @param activity 需要使用activity来构建CourseBox
     * @return List<Course> localData
     */
    public static List<Course> getAllCourseOrderByObjectId(Activity activity) {
        QueryBuilder<Course> builder = getCourseBox(activity).query();
        Query<Course> query = builder.build();
        List<Course> courses = query.find();
        Collections.sort(courses, new Comparator<Course>() {
            @Override
            public int compare(Course o1, Course o2) {
                return o1.getObjectId().compareTo(o2.getObjectId());
            }
        });
        return courses;
    }

    /**
     * API2: 对比netData与localData，对数据库进行同步操作
     */
    public static void synchronizeCourse(List<Course> localData, List<LessonLCBean> netData, Activity activity) {
        Log.e("localData Size", String.valueOf(localData.size()));
        Log.e("netData Size", String.valueOf(netData.size()));

        List<Course> addList = new ArrayList<>();
        List<Course> updateList = new ArrayList<>();
        List<Course> deleteList = new ArrayList<>();

        //构建addList
        boolean getIt;
        for (LessonLCBean net : netData) {
            //默认是找一圈没找着
            getIt = false;
            for (Course local : localData) {
                if (Objects.equals(net.getObjectId(), local.getObjectId())) {
                    getIt = true;
                    break;
                }
            }
            //遍历结果：1. getIt = true，意思是net中的数据local也有，所以不用添加到addList
            //        2. getIt = false，意思是找了一圈，net中的数据在local里也没有，所以得添加到addList
            if (!getIt) {
                Course course = new Course();
                try {
                    course.update(net);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                addList.add(course);
            }
        }

        //构建updateList
        Course course;
        for (LessonLCBean net : netData) {
            //默认是没找着
            course = null;
            for (Course local : localData) {
                if (Objects.equals(local.getObjectId(), net.getObjectId())) {
                    //找到了
                    course = local;
                    break;
                }
            }
            if (course != null) {
                //找了一圈找到了
                try {
                    course.update(net);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateList.add(course);
            }
        }

        //构建deleteList
        for (Course local : localData) {
            //默认是没找着
            getIt = false;
            for (LessonLCBean net : netData) {
                if (Objects.equals(local.getObjectId(), net.getObjectId())) {
                    getIt = true;
                    break;
                }
            }
            if (!getIt) {
                //找了一圈，local中的数据，并没有在net中被找到
                deleteList.add(local);
            }
        }

        Log.e("addList size", String.valueOf(addList.size()));
        Log.e("updateList size", String.valueOf(updateList.size()));
        Log.e("deleteList size", String.valueOf(deleteList.size()));

        Box<Course> courseBox = getCourseBox(activity);
        courseBox.put(addList);
        courseBox.put(updateList);
        courseBox.remove(deleteList);

        //Test
        String string = "localData Size: " + String.valueOf(localData.size()) + "\n"
                + "netData Size: " + String.valueOf(netData.size()) + "\n"
                + "addList size: " + String.valueOf(addList.size()) + "\n"
                + "updateList size" + String.valueOf(updateList.size()) + "\n"
                + "deleteList size: " + String.valueOf(deleteList.size());
    }

    public static List<Course> getCourseOrderByUpdateTime(IndexContract.FragmentType fragmentType, Activity activity) {
        QueryBuilder<Course> builder = getCourseBox(activity).query();
        Query<Course> query = null;
        switch (fragmentType) {
            case NURSERY:
                builder.equal(Course_.subject, "nursery");
                builder.order(Course_.updateAt);
                query = builder.build();
                return query.find();
            case MUSIC:
                builder.equal(Course_.subject, "music");
                builder.order(Course_.updateAt);
                query = builder.build();
                return query.find();
            case READING:
                builder.equal(Course_.subject, "reading");
                builder.order(Course_.updateAt);
                query = builder.build();
                return query.find();
            case GAME:
                builder.equal(Course_.subject, "game");
                builder.order(Course_.updateAt);
                query = builder.build();
                return query.find();
            case HEALTHY:
                return null;
            case LANGUAGE:
                return null;
            case SOCIAL:
                return null;
            case SCIENCE:
                return null;
            case ART:
                return null;
            default:
                return null;
        }
    }
}
