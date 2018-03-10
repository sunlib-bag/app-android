package shaolizhi.sunshinebox.objectbox.courses;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * Created by 邵励治 on 2018/3/10.
 * Perfect Code
 */
@Entity
public class Tag {
    @Id
    private long id;

    private String field;

    public ToOne<Course> course;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
