package com.just.fast.module.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.just.fast.R;
import com.just.utils.log.LogUtils;

/**
 * 利用 ContentProvider 访问其他程序[数据库FirstLine]
 *
 * @author JustDo23
 * @since 2017年07月26日
 */
public class ContentProviderActivity extends AppCompatActivity {

  private String insertId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content_provider);
  }

  public void insert(View view) {
    Uri uri = Uri.parse("content://com.just.first.provider/book");
    ContentValues contentValues = new ContentValues();
    contentValues.put("name", "FirstLine");// 列名-数据
    contentValues.put("author", "Guo");
    contentValues.put("pages", "570");
    contentValues.put("price", 79.0);
    Uri insertUri = getContentResolver().insert(uri, contentValues);// 通过内容提供者进行插入数据
    insertId = insertUri.getPathSegments().get(1);
    LogUtils.e("insertId = " + insertId);
  }

  public void query(View view) {
    Uri uri = Uri.parse("content://com.just.first.provider/book");
    Cursor cursor = getContentResolver().query(uri, null, null, null, null);// 通过内容提供者查询数据
    if (cursor != null) {
      while (cursor.moveToNext()) {
        String author = cursor.getString(cursor.getColumnIndex("author"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String pages = cursor.getString(cursor.getColumnIndex("pages"));
        String price = cursor.getString(cursor.getColumnIndex("price"));
        LogUtils.e("Book: " + author + " -- " + name + " -- " + pages + " -- " + price);
      }
    }
    if (cursor != null) {
      cursor.close();
    }
  }

  public void update(View view) {
    Uri uri = Uri.parse("content://com.just.first.provider/book/" + insertId);
    ContentValues contentValues = new ContentValues();
    contentValues.put("pages", "555");
    contentValues.put("price", 99.9);
    int updateRows = getContentResolver().update(uri, contentValues, null, null);// 通过内容提供者更新数据
    LogUtils.e("updateRows = " + updateRows);
  }

  public void delete(View view) {
    Uri uri = Uri.parse("content://com.just.first.provider/book/" + insertId);
    int deleteRows = getContentResolver().delete(uri, null, null);
    LogUtils.e("deleteRows = " + deleteRows);
  }

}
