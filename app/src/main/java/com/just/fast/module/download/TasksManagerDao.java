package com.just.fast.module.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManagerDao {

  private TasksManagerOpenHelper sqLiteOpenHelper;

  public TasksManagerDao(Context context) {
    sqLiteOpenHelper = new TasksManagerOpenHelper(context);
  }

  /**
   * 获取数据库中所有数据
   *
   * @return 数据库所有数据
   */
  public List<TasksManagerModel> getTaskList() {
    SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TasksManagerOpenHelper.TABLE_NAME, null);
    List<TasksManagerModel> tasksManagerModelList = new ArrayList<>();
    if (!cursor.moveToLast()) {
      return tasksManagerModelList;
    }
    try {
      do {
        TasksManagerModel model = new TasksManagerModel();
        model.setId(cursor.getInt(cursor.getColumnIndex(TasksManagerModel.ID)));
        model.setName(cursor.getString(cursor.getColumnIndex(TasksManagerModel.NAME)));
        model.setUrl(cursor.getString(cursor.getColumnIndex(TasksManagerModel.URL)));
        model.setPath(cursor.getString(cursor.getColumnIndex(TasksManagerModel.PATH)));
        tasksManagerModelList.add(model);
      } while (cursor.moveToPrevious());
    } finally {
      closeCursor(cursor);
      closeDataBase(db);
    }
    return tasksManagerModelList;
  }

  /**
   * 向数据库中插入一条数据
   *
   * @param url  网络地址
   * @param path 文件地址
   * @return 成功返回对象
   */
  public TasksManagerModel insertTask(String url, String path) {
    if (TextUtils.isEmpty(url) || TextUtils.isEmpty(path)) {
      return null;
    }
    int id = FileDownloadUtils.generateId(url, path);// 生成下载 ID
    TasksManagerModel model = new TasksManagerModel();
    model.setId(id);
    model.setName("任务：" + id);
    model.setUrl(url);
    model.setPath(path);
    SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
    boolean success;
    try {
      success = db.insert(TasksManagerOpenHelper.TABLE_NAME, null, model.toContentValues()) != -1;
    } finally {
      closeDataBase(db);
    }
    return success ? model : null;
  }

  /**
   * 向数据库中插入一条数据
   *
   * @param model 插入的对象
   * @return 插入的对象
   */
  public TasksManagerModel insertTask(TasksManagerModel model) {
    if (model == null) {
      return null;
    }
    if (isExist(model.getId())) {
      return model;
    }
    SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
    boolean success;
    try {
      success = db.insert(TasksManagerOpenHelper.TABLE_NAME, null, model.toContentValues()) != -1;
    } finally {
      closeDataBase(db);
    }
    return success ? model : null;
  }

  /**
   * 通过 ID 查询是否存在于数据库
   *
   * @param id 下载任务的 ID
   * @return true, 已经存在
   */
  private boolean isExist(int id) {
    SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TasksManagerOpenHelper.TABLE_NAME + " WHERE " + TasksManagerModel.ID + " = ? ", new String[]{String.valueOf(id)});
    boolean exists;
    try {
      exists = cursor.moveToFirst();
    } finally {
      closeCursor(cursor);
    }
    return exists;
  }

  private static void closeDataBase(SQLiteDatabase database) {
    if (database != null) {
      database.close();
    }
  }

  private static void closeCursor(Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }

}
