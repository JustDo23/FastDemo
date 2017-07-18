package com.just.fast.module.recorder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.fast.R;

import java.io.IOException;

/**
 * 使用[MediaRecorder]录制 Audio
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/reference/android/media/MediaRecorder.html]
 * @webUrl [https://developer.android.com/guide/topics/media/mediarecorder.html#example]
 * @since 2017年07月18日
 */
public class MediaRecorderAudioActivity extends AppCompatActivity {

  private LinearLayout ll_root;
  private RecordButton recordButton;
  private PlayButton playButton;

  private String mFileName;
  private MediaRecorder mediaRecorder;
  private MediaPlayer mediaPlayer;

  private boolean permissionToRecordAccepted;
  private String[] permissions = {Manifest.permission.RECORD_AUDIO};

  private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;// 请求录音权限

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_media_recorder_audio);
    ll_root = (LinearLayout) findViewById(R.id.ll_root);
    mFileName = getExternalCacheDir().getAbsolutePath();
    mFileName += "audioRecord.3gp";// 文件路径
    ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);// 申请权限
    recordButton = new RecordButton(this);
    playButton = new PlayButton(this);
    ll_root.addView(recordButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
    ll_root.addView(playButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case REQUEST_RECORD_AUDIO_PERMISSION:// 权限结果
        permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        break;
    }
    if (!permissionToRecordAccepted) {// 没有个权限
      finish();
    }
  }


  private void onRecord(boolean start) {
    if (start) {
      startRecording();
    } else {
      stopRecording();
    }
  }

  private void startRecording() {
    mediaRecorder = new MediaRecorder();
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mediaRecorder.setOutputFile(mFileName);
    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    try {
      mediaRecorder.prepare();
    } catch (IOException e) {
      e.printStackTrace();
    }
    mediaRecorder.start();
  }

  private void stopRecording() {
    mediaRecorder.stop();
    mediaRecorder.release();
    mediaRecorder = null;
  }

  private void onPlay(boolean start) {
    if (start) {
      startPlaying();
    } else {
      stopPlaying();
    }
  }

  private void startPlaying() {
    mediaPlayer = new MediaPlayer();
    try {
      mediaPlayer.setDataSource(mFileName);
      mediaPlayer.prepare();
      mediaPlayer.start();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void stopPlaying() {
    mediaPlayer.stop();
    mediaPlayer.release();
    mediaPlayer = null;
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (mediaRecorder != null) {
      mediaRecorder.stop();
      mediaRecorder.release();
      mediaRecorder = null;
    }
    if (mediaPlayer != null) {
      mediaPlayer.stop();
      mediaPlayer.release();
      mediaPlayer = null;
    }
  }


  /**
   * 录音的按钮
   *
   * @author JustDo23
   */
  public class RecordButton extends Button {

    private boolean mStartRecording = true;

    public RecordButton(Context context) {
      super(context);
      setText("Start Recording");
      setOnClickListener(recordOnClickListener);
    }

    OnClickListener recordOnClickListener = new OnClickListener() {

      @Override
      public void onClick(View v) {
        onRecord(mStartRecording);
        if (mStartRecording) {
          setText("Stop Recording");
        } else {
          setText("Start Recording");
        }
        mStartRecording = !mStartRecording;
      }
    };

  }


  /**
   * 播音的按钮
   *
   * @author JustDo23
   */
  public class PlayButton extends Button {

    private boolean mStartPlaying = true;

    public PlayButton(Context context) {
      super(context);
      setText("Start Playing");
      setOnClickListener(playOnClickListener);
    }

    OnClickListener playOnClickListener = new OnClickListener() {

      @Override
      public void onClick(View v) {
        onPlay(mStartPlaying);
        if (mStartPlaying) {
          setText("Stop Playing");
        } else {
          setText("Start Playing");
        }
        mStartPlaying = !mStartPlaying;
      }
    };
  }


}