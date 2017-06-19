package com.just.fast.module.download;

import android.content.ContentValues;

/**
 * 实体类
 *
 * @author JustDo23
 * @since 2017年06月13日
 */
public class TasksManagerModel {

  private int id;// 下载的 ID
  private String name;// 名称
  private String url;// 网络地址
  private String path;// 文件路径(绝对路径)

  public final static String ID = "id";
  public final static String NAME = "name";
  public final static String URL = "url";
  public final static String PATH = "path";

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public ContentValues toContentValues() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(ID, id);
    contentValues.put(NAME, name);
    contentValues.put(URL, url);
    contentValues.put(PATH, path);
    return contentValues;
  }

}
