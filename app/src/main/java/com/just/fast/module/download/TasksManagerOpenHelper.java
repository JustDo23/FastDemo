package com.just.fast.module.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManagerOpenHelper extends SQLiteOpenHelper {

  private final static String DATABASE_NAME = "tasksManager.db";
  private final static int DATABASE_VERSION = 1;
  public final static String TABLE_NAME = "taskList";

  public TasksManagerOpenHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS "
        + TABLE_NAME
        + String.format(
        "("
            + "%s INTEGER PRIMARY KEY, " // id, download id
            + "%s VARCHAR, " // name
            + "%s VARCHAR, " // url
            + "%s VARCHAR " // path
            + ")"
        , TasksManagerModel.ID
        , TasksManagerModel.NAME
        , TasksManagerModel.URL
        , TasksManagerModel.PATH
    ));
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.delete("taskList", null, null);
    onCreate(db);
  }

  @Override
  public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    onUpgrade(db, oldVersion, newVersion);
  }

}
