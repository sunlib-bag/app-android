package shaolizhi.sunshinebox.utils;

import android.util.Log;

import java.io.File;

import shaolizhi.sunshinebox.data.ConstantData;

/**
 * 由邵励治于2017/12/18创造.
 */

public class IOUtils {

    private static File musicDirectory = new File(ConstantData.MUSIC_DIRECTORY);

    private static File rhymesDirectory = new File(ConstantData.RHYMES_DIRECTORY);

    private static File readingDirectory = new File(ConstantData.READING_DIRECTORY);

    private static File gameDirectory = new File(ConstantData.GAME_DIRECTORY);


    public static void createDirectory() {
        if (!musicDirectory.exists()) {
            Boolean isSuccess = musicDirectory.mkdirs();
            Log.i("IOUtils", "Create music directory success:" + isSuccess);
        }
        if (!rhymesDirectory.exists()) {
            Boolean isSuccess = rhymesDirectory.mkdirs();
            Log.i("IOUtils", "Create rhymes directory success:" + isSuccess);
        }
        if (!readingDirectory.exists()) {
            Boolean isSuccess = readingDirectory.mkdirs();
            Log.i("IOUtils", "Create reading directory success:" + isSuccess);
        }
        if (!gameDirectory.exists()) {
            Boolean isSuccess = gameDirectory.mkdirs();
            Log.i("IOUtils", "Create game directory success:" + isSuccess);
        }
    }
}
