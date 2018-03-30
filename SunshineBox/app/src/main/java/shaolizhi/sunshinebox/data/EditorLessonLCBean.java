package shaolizhi.sunshinebox.data;

import com.avos.avoscloud.AVObject;

import java.util.Date;
import java.util.List;

/**
 * Created by 邵励治 on 2018/3/30.
 * Perfect Code
 */
public class EditorLessonLCBean {
    public final static String NURSERY = "nursery";
    public final static String MUSIC = "music";
    public final static String READING = "reading";
    public final static String GAME = "game";

    @Override
    public String toString() {
        return "LessonLCBean{" +
                "objectId='" + objectId + '\'' +
                ", isPublished=" + isPublished +
                ", tags=" + tags +
                ", zipPackageUrl='" + zipPackageUrl + '\'' +
                ", name='" + name + '\'' +
                ", versionCode=" + versionCode +
                ", subject='" + subject + '\'' +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                '}';
    }

    public EditorLessonLCBean(AVObject avObject) {
        objectId = avObject.getObjectId();
        isPublished = avObject.getBoolean("isPublished");
        tags = avObject.getList("tags");
        zipPackageUrl = avObject.getAVFile("staging_package").getUrl();
        name = avObject.getString("name");
        versionCode = avObject.getNumber("version_code").intValue();
        switch (avObject.getAVObject("subject").getObjectId()) {
            case "5a8e908dac502e0032b6225d":
                subject = GAME;
                break;
            case "5a701c82d50eee00444134b2":
                subject = READING;
                break;
            case "5a741bcb2f301e003be904ed":
                subject = MUSIC;
                break;
            case "5a701c8c1b69e6003c534903":
                subject = NURSERY;
                break;
        }
        createdAt = avObject.getCreatedAt();
        updateAt = avObject.getUpdatedAt();
    }

    private String objectId;

    private Boolean isPublished;

    private List<String> tags;

    private String zipPackageUrl;

    private String name;

    private int versionCode;

    private String subject;

    private Date createdAt;

    private Date updateAt;

    public String getObjectId() {
        return objectId;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getZipPackageUrl() {
        return zipPackageUrl;
    }

    public String getName() {
        return name;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getSubject() {
        return subject;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }
}
