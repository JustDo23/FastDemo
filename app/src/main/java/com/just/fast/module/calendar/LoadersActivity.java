package com.just.fast.module.calendar;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.just.fast.R;

/**
 * 学习使用加载器
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/guide/components/loaders.html?hl=zh-cn]
 * @since 2017年08月04日
 */
public class LoadersActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_loaders);
    // 准备加载器。重连现有的或者重新启动一个加载器。
    //getLoaderManager().initLoader(23, null, this);// [加载器唯一标识][可选参数][回调]

    FragmentManager fm = getFragmentManager();
    // Create the list fragment and add it as our sole content.
    if (fm.findFragmentById(android.R.id.content) == null) {
      CursorLoaderListFragment list = new CursorLoaderListFragment();
      fm.beginTransaction().add(android.R.id.content, list).commit();
    }
  }


  /**
   * 针对指定的 ID 实例化并返回一个新的加载器
   *
   * @param id
   * @param args
   * @return
   */
  @Override
  public Loader<Object> onCreateLoader(int id, Bundle args) {
    return null;
  }

  /**
   * 在先前创建的加载器完成加载时回调
   *
   * @param loader
   * @param data
   */
  @Override
  public void onLoadFinished(Loader<Object> loader, Object data) {

  }

  /**
   * 在先前创建的加载器重置或其数据因此不可用时回调
   *
   * @param loader
   */
  @Override
  public void onLoaderReset(Loader<Object> loader) {

  }
}
