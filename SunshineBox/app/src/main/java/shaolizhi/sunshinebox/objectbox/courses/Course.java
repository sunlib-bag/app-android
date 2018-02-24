package shaolizhi.sunshinebox.objectbox.courses;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by 邵励治 on 2018/2/22.

 */

@Entity
public class Course {
    @Id
    long id;

    String objectId;

    String courseName;

    String subject;

    /*
        situation = 0 尚未下载
        situation = 1 已下载但有更新
        situation = 2 已下载且没有更新
     */
    int situation;

    int versionCode;

    String resourcePackageUrl;

    String resourceStorageAddress;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getResourcePackageUrl() {
        return resourcePackageUrl;
    }

    public void setResourcePackageUrl(String resourcePackageUrl) {
        this.resourcePackageUrl = resourcePackageUrl;
    }

    public String getResourceStorageAddress() {
        return resourceStorageAddress;
    }

    public void setResourceStorageAddress(String resourceStorageAddress) {
        this.resourceStorageAddress = resourceStorageAddress;
    }
}
