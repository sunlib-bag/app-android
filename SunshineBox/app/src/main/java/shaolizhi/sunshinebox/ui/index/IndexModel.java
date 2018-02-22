package shaolizhi.sunshinebox.ui.index;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 邵励治 on 2018/2/22.
 */

class IndexModel implements IndexContract.Model {
    private IndexContract.CallBack callBack;

    IndexModel(IndexContract.CallBack callBack) {
        this.callBack = callBack;
    }

    //request data from net
    private void requestDataFromNetResult(List<AVObject> list, AVException e, IndexContract.CourseType courseType) {
        if (e == null) {
            callBack.requestDataFromNetSuccess(list, courseType);
        } else {
            callBack.requestDataFromNetFailure(e);
        }
    }

    @Override
    public void requestDataFromNet(final IndexContract.CourseType courseType) {

        final AVQuery<AVObject> query1 = new AVQuery<>("Lesson");
        query1.whereEqualTo("isPublished", true);
        final AVQuery<AVObject> query2 = new AVQuery<>("Lesson");
        switch (courseType) {
            case NURSERY:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c8c1b69e6003c534903"));
                break;
            case MUSIC:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a741bcb2f301e003be904ed"));
                break;
            case READING:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c82d50eee00444134b2"));
                break;
            case GAME:
                query2.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a8e908dac502e0032b6225d"));
                break;
        }
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(query1, query2));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                requestDataFromNetResult(list, e, courseType);
            }
        });
    }

    @Override
    public void requestDataFromDatabase() {

    }

    @Override
    public boolean isThereAnyDataInTheDatabase(IndexContract.CourseType courseType) {
        return false;
    }

    @Override
    public void storedInTheDatabase() {

    }
}
