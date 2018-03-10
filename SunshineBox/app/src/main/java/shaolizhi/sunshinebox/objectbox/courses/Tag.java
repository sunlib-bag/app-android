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
    public long id;

    public String field;

    public ToOne<Course> course;
}
