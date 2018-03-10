package shaolizhi.sunshinebox.objectbox.courses;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import shaolizhi.sunshinebox.data.LessonLCBean;

/**
 * Created by 邵励治 on 2018/2/22.
 */

@Entity
public class Course {

    public static final int NOT_DOWNLOADED = 0;
    public static final int HAVE_UPDATE = 1;
    public static final int DOWNLOADED = 2;


    @Id
    private long id;

    private String objectId;

    private String resourcePackageUrl;

    private String resourceStorageAddress;

    private String courseName;

    private String subject;

    private Date createdAt;

    private Date updateAt;

    private int situation;

    private int versionCode;

    public void update(LessonLCBean lessonLCBean) throws Exception {
        objectId = lessonLCBean.getObjectId();
        resourcePackageUrl = lessonLCBean.getZipPackageUrl();
        courseName = lessonLCBean.getName();
        subject = lessonLCBean.getSubject();
        createdAt = lessonLCBean.getCreatedAt();
        updateAt = lessonLCBean.getUpdateAt();
        if (versionCode == 0) {
            //第一次UPDATE
            situation = NOT_DOWNLOADED;
            versionCode = lessonLCBean.getVersionCode();
        } else {
            //非第一次UPDATE
            switch (situation) {
                case NOT_DOWNLOADED:
                    situation = NOT_DOWNLOADED;
                    versionCode = lessonLCBean.getVersionCode();
                    break;
                case HAVE_UPDATE:
                    situation = HAVE_UPDATE;
                    versionCode = lessonLCBean.getVersionCode();
                    break;
                case DOWNLOADED:
                    if (versionCode == lessonLCBean.getVersionCode()) {
                        situation = DOWNLOADED;
                        versionCode = lessonLCBean.getVersionCode();
                    } else if (versionCode < lessonLCBean.getVersionCode()) {
                        situation = HAVE_UPDATE;
                        versionCode = lessonLCBean.getVersionCode();
                    } else {
                        throw new Exception("VersionCode Error, Server VersionCode < Local VersionCode");
                    }
                    break;
            }
        }
    }

    public void downloadSuccess(String resourceStorageAddress) throws Exception {
        switch (situation) {
            case NOT_DOWNLOADED:
                this.resourceStorageAddress = resourceStorageAddress;
                situation = DOWNLOADED;
                break;
            case HAVE_UPDATE:
                this.resourceStorageAddress = resourceStorageAddress;
                situation = DOWNLOADED;
                break;
            case DOWNLOADED:
                throw new Exception("Already Downloaded this Course");
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getResourcePackageUrl() {
        return resourcePackageUrl;
    }

    public String getResourceStorageAddress() {
        return resourceStorageAddress;
    }

    public String getCourseName() {
        return courseName;
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

    public int getSituation() {
        return situation;
    }

    public int getVersionCode() {
        return versionCode;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", objectId='" + objectId + '\'' +
                ", resourcePackageUrl='" + resourcePackageUrl + '\'' +
                ", resourceStorageAddress='" + resourceStorageAddress + '\'' +
                ", courseName='" + courseName + '\'' +
                ", subject='" + subject + '\'' +
                ", createdAt=" + createdAt +
                ", updateAt=" + updateAt +
                ", situation=" + situation +
                ", versionCode=" + versionCode +
                '}';
    }
}
