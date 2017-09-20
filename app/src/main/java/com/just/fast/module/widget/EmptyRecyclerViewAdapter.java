package com.just.fast.module.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.just.fast.R;

import java.util.List;

/**
 * 可以设置空布局
 *
 * @webUrl [https://github.com/xiazdong/RecyclerViewDemo]
 */
public class EmptyRecyclerViewAdapter extends RecyclerView.Adapter<EmptyRecyclerViewAdapter.VH> {

  private List<String> mDatas;

  public EmptyRecyclerViewAdapter(List<String> data) {
    this.mDatas = data;
  }

  @Override
  public void onBindViewHolder(VH holder, int position) {
    holder.title.setText(mDatas.get(position));
  }

  @Override
  public int getItemCount() {
    return mDatas.size();
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_empty_recycler_view_item, parent, false);
    return new VH(v);
  }

  public static class VH extends RecyclerView.ViewHolder {
    public final TextView title;

    public VH(View v) {
      super(v);
      title = (TextView) v.findViewById(R.id.text);
    }
  }
}
