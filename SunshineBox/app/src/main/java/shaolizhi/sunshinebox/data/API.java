package shaolizhi.sunshinebox.data;

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邵励治 on 2018/3/9.
 * Perfect Code
 */

public class API {
    /**
     * API1: 查询Lesson表中的全部课程，按照objectId从小到大排列
     *
     * @param queryListener 调用成功的话会返回List<LessonLCBean>
     */
    public static void getAllLesson(final QueryListener queryListener) {
        AVQuery<AVObject> query = new AVQuery<>("Lesson");
        query.whereEqualTo("isPublished", true);
        query.include("package");
        query.include("subject");
        query.orderByAscending("objectId");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    queryListener.success(getLessonLCBeanList(list));
                } else {
                    queryListener.failure(e);
                }
            }
        });
    }

    /**
     * API2: 按领域查找Lesson表中的数据
     *
     * @param tag           只能为HEALTH；LANGUAGE；SOCIAL；SCIENCE；ART；这五种
     * @param queryListener 调用成功的话会返回List<LessonLCBean>
     */
    public static void getLessonByTag(@TAG String tag, final QueryListener queryListener) {
        Log.e("Call Method", "getLessonByTag");
        final AVQuery<AVObject> query = new AVQuery<>("Lesson");
        query.whereEqualTo("isPublished", true);
        query.include("package");
        query.include("subject");
        query.whereStartsWith("tags", tag);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    queryListener.success(getLessonLCBeanList(list));
                } else {
                    queryListener.failure(e);
                }
            }
        });
    }


    @StringDef({HEALTH, LANGUAGE, SOCIAL, SCIENCE, ART})
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    public @interface TAG {
    }

    public static final String HEALTH = "domain.健康";
    public static final String LANGUAGE = "domain.语言";
    public static final String SOCIAL = "domain.社会";
    public static final String SCIENCE = "domain.科学";
    public static final String ART = "domain.艺术";


    public interface QueryListener {
        void success(List<LessonLCBean> beanList);

        void failure(AVException e);
    }


    @NonNull
    private static List<LessonLCBean> getLessonLCBeanList(List<AVObject> list) {
        List<LessonLCBean> beans = new ArrayList<>();
        for (AVObject avObject : list) {
            LessonLCBean lessonLCBean = new LessonLCBean(avObject);
            beans.add(lessonLCBean);
        }
        return beans;
    }
}
