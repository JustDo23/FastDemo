package com.just.fast.module.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.just.fast.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以设置空布局
 *
 * @webUrl [https://github.com/xiazdong/RecyclerViewDemo]
 */
public class EmptyRecyclerViewActivity extends AppCompatActivity {

  private EmptyRecyclerView mRv;
  private List<String> mData;
  private EmptyRecyclerViewAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_empty_recycler_view);
    mRv = (EmptyRecyclerView) findViewById(R.id.rv);
    mRv.setLayoutManager(new LinearLayoutManager(this));
    mData = new ArrayList<>();
    mAdapter = new EmptyRecyclerViewAdapter(mData);
    //View view = LayoutInflater.from(this).inflate(R.layout.empty, null);
    View view = findViewById(R.id.text_empty);
    mRv.setEmptyView(view);
    mRv.setAdapter(mAdapter);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_empty_recycler_view, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.item_add:
        mData.add(0, "hello");
        mAdapter.notifyItemInserted(0);
        break;
      case R.id.item_delete:
        if (!mData.isEmpty()) {
          mData.remove(0);
          mAdapter.notifyItemRemoved(0);
        }
        break;
    }

    return super.onOptionsItemSelected(item);
  }
}
