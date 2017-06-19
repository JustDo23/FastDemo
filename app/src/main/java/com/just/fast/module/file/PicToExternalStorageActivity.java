package com.just.fast.module.file;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.just.fast.R;
import com.just.utils.toast.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Here is an example of typical code to manipulate a picture on the public shared storage.
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/reference/android/os/Environment.html#getExternalStoragePublicDirectory(java.lang.String)]
 * @since 2017年06月08日
 */
public class PicToExternalStorageActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pic_to_external_storage);
    if (hasExternalStoragePublicPicture()) {
      deleteExternalStoragePublicPicture();
    }
    createExternalStoragePublicPicture();
    ToastUtil.showShortToast(this, "复制完成");
  }

  private void createExternalStoragePublicPicture() {
    // Create a path where we will place our picture in the user's
    // public pictures directory.  Note that you should be careful about
    // what you place here, since the user often manages these files.  For
    // pictures and other media owned by the application, consider
    // Context.getExternalMediaDir().
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File file = new File(path, "DemoPicture.jpg");

    try {
      // Make sure the Pictures directory exists.
      path.mkdirs();

      // Very simple code to copy a picture from the application's
      // resource into the external file.  Note that this code does
      // no error checking, and assumes the picture is small (does not
      // try to copy it in chunks).  Note that if external storage is
      // not currently mounted this will silently fail.
      InputStream is = getResources().openRawResource(R.raw.ic_bird_video);
      OutputStream os = new FileOutputStream(file);
      byte[] data = new byte[is.available()];
      is.read(data);
      os.write(data);
      is.close();
      os.close();

      // Tell the media scanner about the new file so that it is
      // immediately available to the user.
      MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,

          new MediaScannerConnection.OnScanCompletedListener() {

            @Override
            public void onScanCompleted(String path, Uri uri) {
              Log.i("ExternalStorage", "Scanned " + path + ":");
              Log.i("ExternalStorage", "-> uri=" + uri);
            }
          });
    } catch (IOException e) {
      // Unable to create file, likely because external storage is
      // not currently mounted.
      Log.w("ExternalStorage", "Error writing " + file, e);
    }
  }

  private void deleteExternalStoragePublicPicture() {
    // Create a path where we will place our picture in the user's
    // public pictures directory and delete the file.  If external
    // storage is not currently mounted this will fail.
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File file = new File(path, "DemoPicture.jpg");
    file.delete();
  }

  private boolean hasExternalStoragePublicPicture() {
    // Create a path where we will place our picture in the user's
    // public pictures directory and check if the file exists.  If
    // external storage is not currently mounted this will think the
    // picture doesn't exist.
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    File file = new File(path, "DemoPicture.jpg");
    return file.exists();
  }
}
