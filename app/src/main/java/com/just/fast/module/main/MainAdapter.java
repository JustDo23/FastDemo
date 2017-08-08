package com.just.fast.module.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.just.fast.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 列表适配器
 *
 * @webUrl [https://github.com/emilsjolander/StickyListHeaders]
 * @since 2017年08月08日
 */
public class MainAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {

  private Context context;
  private List<String> titleList;// 头部内容集合
  private Map<Integer, List<String>> contentMap;// 内容集合
  private List<String> contentList;// 所有内容
  private int[] titleIndex;// 位置集合

  public MainAdapter(Context context, List<String> titleList, Map<Integer, List<String>> contentMap) {
    this.context = context;
    this.titleList = titleList;
    this.contentMap = contentMap;
    parseData();
  }

  /**
   * 解析数据
   */
  private void parseData() {
    contentList = new ArrayList<>();
    Set<Integer> keySet = contentMap.keySet();
    titleIndex = new int[keySet.size()];
    titleIndex[0] = 0;
    for (int integer = 0; integer < keySet.size(); integer++) {
      contentList.addAll(contentMap.get(integer));
      if (integer != 0) {
        titleIndex[integer] = titleIndex[integer - 1] + contentMap.get(integer - 1).size();
      }
    }
  }

  @Override
  public int getCount() {
    return contentList.size();
  }

  @Override
  public Object getItem(int position) {
    return contentList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ContentViewHolder contentViewHolder;
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_content, parent, false);
      contentViewHolder = new ContentViewHolder(convertView);
      convertView.setTag(contentViewHolder);
    } else {
      contentViewHolder = (ContentViewHolder) convertView.getTag();
    }
    contentViewHolder.tv_content.setText(contentList.get(position));
    return convertView;
  }


  class ContentViewHolder {

    TextView tv_content;

    public ContentViewHolder(View rootView) {
      tv_content = (TextView) rootView.findViewById(R.id.tv_content);
    }

  }


  @Override
  public View getHeaderView(int position, View convertView, ViewGroup parent) {
    HeadViewHolder headViewHolder;
    if (convertView == null) {
      convertView = LayoutInflater.from(context).inflate(R.layout.activity_main_title, parent, false);
      headViewHolder = new HeadViewHolder(convertView);
      convertView.setTag(headViewHolder);
    } else {
      headViewHolder = (HeadViewHolder) convertView.getTag();
    }
    headViewHolder.tv_title.setText(titleList.get(getSectionForPosition(position)));
    return convertView;
  }

  @Override
  public long getHeaderId(int position) {
    return getSectionForPosition(position);// 控制头的个数
  }


  class HeadViewHolder {

    TextView tv_title;

    public HeadViewHolder(View rootView) {
      tv_title = (TextView) rootView.findViewById(R.id.tv_title);
    }

  }


  @Override
  public Object[] getSections() {
    return titleList.toArray();
  }

  @Override
  public int getPositionForSection(int sectionIndex) {
    if (titleIndex.length == 0) {
      return 0;
    }
    if (sectionIndex >= titleIndex.length) {
      sectionIndex = titleIndex.length - 1;
    } else if (sectionIndex < 0) {
      sectionIndex = 0;
    }
    return titleIndex[sectionIndex];
  }

  @Override
  public int getSectionForPosition(int position) {
    for (int i = 0; i < titleIndex.length; i++) {
      if (position < titleIndex[i]) {
        return i - 1;
      }
    }
    return titleIndex.length - 1;
  }

}
