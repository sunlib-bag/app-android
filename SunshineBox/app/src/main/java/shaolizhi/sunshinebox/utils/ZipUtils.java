package shaolizhi.sunshinebox.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by 邵励治 on 2018/3/1.
 * Perfect Code
 */

public class ZipUtils {
    public static void decompress(File zip, String outputDirectory) throws IOException {
        File outputDirectoryFile = new File(outputDirectory);
        if (!outputDirectoryFile.exists()) {
            Boolean isCreateFileSuccess = outputDirectoryFile.mkdirs();
            Log.e("ZipUtils", "CreateOutputDirectory" + String.valueOf(isCreateFileSuccess));
        }
        byte[] buffer = new byte[1024];
        File outFile;
        ZipFile zipFile = new ZipFile(zip);
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry;
        InputStream inputStream;
        OutputStream outputStream;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            outFile = new File(outputDirectory + File.separator + zipEntry.getName());

            if (zipEntry.isDirectory()) {
                outFile.mkdir();
            } else {

                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdirs();
                }

                outFile.createNewFile();
                inputStream = zipFile.getInputStream(zipEntry);
                outputStream = new FileOutputStream(outFile);
                int temp;
                while ((temp = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, temp);
                }
                inputStream.close();
                outputStream.close();
            }
        }
        zipFile.close();
        zipInputStream.close();
    }
}
