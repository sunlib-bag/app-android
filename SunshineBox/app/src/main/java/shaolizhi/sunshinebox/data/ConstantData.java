package shaolizhi.sunshinebox.data;

import android.os.Environment;

import java.io.File;

/**
 * 由邵励治于2017/12/3创造.
 */

public class ConstantData {
    //SharedPreferences
    public static final String UUID = "uuid";

    //Intent from IndexFragment to CourseActivity
    public static final String COURSE = "course";

    //Root Directory's name
    private static final String ROOT_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + File.separator + "SunshineBox";

    //Music Directory's name
    public static final String MUSIC_DIRECTORY = ROOT_DIRECTORY + File.separator + "music";

    //Rhymes Directory's name
    public static final String RHYMES_DIRECTORY = ROOT_DIRECTORY + File.separator + "rhymes";

    //Game Directory's name
    public static final String GAME_DIRECTORY = ROOT_DIRECTORY + File.separator + "game";

    //Reading Directory's name
    public static final String READING_DIRECTORY = ROOT_DIRECTORY + File.separator + "reading";


}
