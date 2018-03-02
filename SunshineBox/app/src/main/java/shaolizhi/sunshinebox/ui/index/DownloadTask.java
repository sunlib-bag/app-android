package shaolizhi.sunshinebox.ui.index;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by 邵励治 on 2018/3/2.
 * Perfect Code
 */

public class DownloadTask extends AsyncTask<Void, Long, Void> {

    public interface DownloadCallback {
        void downloadSuccess(File file);

        void progressUpdate(Long value);
    }

    private DownloadCallback downloadCallback;

    private Response<ResponseBody> responseBodyResponse;

    private Boolean isFileDownloadSuccess = false;

    private String outputFileName;

    private File outputFolder;

    private File downloadFileAddress;

    @Override
    protected Void doInBackground(Void... voids) {
        downloadFileAddress = new File(outputFolder, outputFileName);
        isFileDownloadSuccess = writeResponseBodyToDisk(responseBodyResponse.body(), downloadFileAddress);
        Log.e("IndexAdapter", "file download was a success?" + isFileDownloadSuccess);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (isFileDownloadSuccess) {
            downloadCallback.downloadSuccess(downloadFileAddress);
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Long... values) {
        super.onProgressUpdate(values);
        Long progress = values[0];
        if (progress != null) {
            downloadCallback.progressUpdate(progress);
        }
    }

    public DownloadTask(Response<ResponseBody> responseBodyResponse, String outputFileName, File outputFolder, DownloadCallback downloadCallback) {
        this.responseBodyResponse = responseBodyResponse;
        this.outputFileName = outputFileName;
        this.outputFolder = outputFolder;
        this.downloadCallback = downloadCallback;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {
            // todo change the file location/name according to your needs
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    float downloadPercent = (float) fileSizeDownloaded / (float) fileSize;
                    long result = (long) (downloadPercent * 100);
                    publishProgress(result);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
