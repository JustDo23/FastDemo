package com.just.fast.module.media;

import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.just.fast.R;
import com.just.utils.log.LogUtils;
import com.just.utils.toast.ToastUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用SurfaceView 播放单个视频
 *
 * @author JustDo23
 */
public class SurfaceMediaActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

  @BindView(R.id.sv_single) SurfaceView sv_single;
  @BindView(R.id.bt_play) Button bt_play;// 开始和暂停
  @BindView(R.id.ll_control) LinearLayout ll_control;

  private MediaPlayer mediaPlayer;
  private SurfaceHolder surfaceHolder;
  private boolean isMediaPlayerPrepared;// MediaPlayer是否准备了
  private boolean isSurfaceHolderPrepared;// SurfaceHolder是否准备了
  private String videoUrl = "http://baobab.wdjcdn.com/145076769089714.mp4";// 视频地址

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    LogUtils.e("---> onCreate()");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_surface_media);
    ButterKnife.bind(this);
    bt_play.setOnClickListener(this);

    mediaPlayer = new MediaPlayer();
    surfaceHolder = sv_single.getHolder();
    surfaceHolder.addCallback(this);
    surfaceHolder.setFormat(PixelFormat.RGBA_8888);// 设置显示清晰
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_play:
        if ("播放".equals(bt_play.getText())) {
          prepareForVideo(videoUrl);
          bt_play.setText("暂停");
        } else if ("暂停".equals(bt_play.getText())) {
          mediaPlayerPause();
          bt_play.setText("播放");
        }
        break;
    }
  }

  /**
   * 准备播放视频
   *
   * @param videoUrl 视频地址
   */
  private void prepareForVideo(String videoUrl) {
    LogUtils.e("---> prepareForVideo()");
    mediaPlayerRelease();// 先进行释放
    mediaPlayer = new MediaPlayer();// 重新初始化
    mediaPlayer.setOnPreparedListener(this);
    mediaPlayer.setOnBufferingUpdateListener(this);
    mediaPlayer.setOnInfoListener(this);
    mediaPlayer.setOnCompletionListener(this);
    mediaPlayer.setOnErrorListener(this);
    mediaPlayer.setDisplay(surfaceHolder);// 位置问题
    try {
      mediaPlayer.setDataSource(videoUrl);// 设置数据源
    } catch (IOException e) {
      e.printStackTrace();
    }
    mediaPlayer.prepareAsync();// 异步准备
  }


  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    LogUtils.e("---> surfaceCreated()");
    isSurfaceHolderPrepared = true;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    LogUtils.e("---> surfaceChanged()");
    isSurfaceHolderPrepared = true;
    mediaPlayer.setDisplay(surfaceHolder);
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    LogUtils.e("---> surfaceDestroyed()");
    isSurfaceHolderPrepared = false;
  }


  @Override
  public void onPrepared(MediaPlayer mp) {
    LogUtils.e("---> onPrepared()");
    mp.start();
  }

  @Override
  public void onBufferingUpdate(MediaPlayer mp, int percent) {
    LogUtils.e("---> onBufferingUpdate() --> percent = " + percent);
  }

  @Override
  public boolean onInfo(MediaPlayer mp, int what, int extra) {
    LogUtils.e("---> onBufferingUpdate() --> what = " + what + " , extra = " + extra);
    switch (what) {
      case MediaPlayer.MEDIA_INFO_BUFFERING_START:
        break;
      case MediaPlayer.MEDIA_INFO_BUFFERING_END:
        break;
    }
    return false;
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    LogUtils.e("---> onCompletion()");

  }

  @Override
  public boolean onError(MediaPlayer mp, int what, int extra) {
    LogUtils.e("---> onError() --> what = " + what + " , extra = " + extra);
    isMediaPlayerPrepared = false;
    ToastUtil.showShortToast(this, "Error : " + what + "," + extra);
    switch (what) {
      case MediaPlayer.MEDIA_ERROR_UNKNOWN:// [1, Unspecified media player error. 未指定媒体播放器。]
        break;
      case MediaPlayer.MEDIA_ERROR_SERVER_DIED:// [100, Media server died. 媒体服务器死亡。]
        break;
    }
    switch (extra) {
      case MediaPlayer.MEDIA_ERROR_IO://  [-1004, File or network related operation errors. 文件或网络相关操作错误。]
        break;
      case MediaPlayer.MEDIA_ERROR_MALFORMED://  [-1007, Bitstream is not conforming to the related coding standard or file spec. 比特流不符合有关标准或文件的编码规范。]
        break;
      case MediaPlayer.MEDIA_ERROR_UNSUPPORTED://  [-1010, Bitstream is conforming to the related coding standard or file spec, but the media framework does not support the feature. 比特流是符合相关的编码标准或文件规格，但媒体框架不支持的功能。]
        break;
      case MediaPlayer.MEDIA_ERROR_TIMED_OUT://  [-110, Some operation takes too long to complete, usually more than 3-5 seconds. 一些操作需要太长时间才能完成，通常超过3-5秒。]
        break;
    }
    prepareForVideo(videoUrl);
    return false;
  }


  @Override
  protected void onStart() {
    super.onStart();
    LogUtils.e("---> onStart()");
    mediaPlayerStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    LogUtils.e("---> onStop()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    LogUtils.e("---> onDestroy()");
    mediaPlayerRelease();// 进行释放
  }


  /**
   * 开始播放
   */
  private void mediaPlayerStart() {
    if (mediaPlayer != null && isSurfaceHolderPrepared && isMediaPlayerPrepared) {// MediaPlayer 不空 && SurfaceHolder 正常 && MediaPlayer 准备好了
      LogUtils.e("---> mediaPlayerStart() ---> mPlayer.start()");
      mediaPlayer.start();
    }
  }

  /**
   * 暂停播放
   */
  private void mediaPlayerPause() {
    if (mediaPlayer != null) {
      mediaPlayer.pause();
    }
  }

  /**
   * 播放器释放资源
   */
  private void mediaPlayerRelease() {
    if (mediaPlayer != null) {
      if (mediaPlayer.isPlaying()) {// 如果正在播放
        mediaPlayer.stop();// 先停止进入停止状态
        mediaPlayer.reset();// 初始化
      }
      mediaPlayer.release();
      mediaPlayer = null;
      isMediaPlayerPrepared = false;
    }
  }

}
