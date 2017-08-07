package com.just.fast.module.vlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.LayoutViewFactory;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.BaseLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.bumptech.glide.Glide;
import com.just.fast.R;

/**
 * 阿里巴巴推出 VLayout
 *
 * @author JustDo23
 * @webUrl [https://github.com/alibaba/vlayout]
 * @webUrl [http://pingguohe.net/2017/03/03/vlayout-guide-1.html?utm_medium=hao.caibaojian.com&utm_source=hao.caibaojian.com]
 * @webUrl [http://pingguohe.net/2017/03/03/vlayout-guide-2.html]
 * @since 2017年08月01日
 */
public class VirtualLayoutActivity extends AppCompatActivity {

  private RecyclerView rv_various;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_virtual_layout);

    rv_various = (RecyclerView) findViewById(R.id.rv_various);// 控件对象
    VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);// 布局管理器
    rv_various.setLayoutManager(virtualLayoutManager);// 设置布局管理器
    RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();// 复用池
    rv_various.setRecycledViewPool(recycledViewPool);// 给控件设置复用池
    recycledViewPool.setMaxRecycledViews(0, 10);// 指定 viewType 类型的最大复用个数

    DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);// 实例已有 Adapter
    rv_various.setAdapter(delegateAdapter);// 设置适配器
    FirstAdapter firstAdapter = new FirstAdapter(this, new LinearLayoutHelper(), 20);// 自定义适配器类似原生的适配器
    delegateAdapter.addAdapter(firstAdapter);// 将其添加进行列表

    LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();// 获取 LayoutHelper 对象
    linearLayoutHelper.setMargin(20, 20, 20, 20);// 设置这一部分布局的 Margin 外边距
    linearLayoutHelper.setPadding(20, 20, 20, 20);// 设置这一部分布局的 Padding 内边距
    linearLayoutHelper.setBgColor(getResources().getColor(R.color.DarkOrchid));// 设置背景颜色
    // 设置背景图片
    virtualLayoutManager.setLayoutViewFactory(new LayoutViewFactory() {

      @Override
      public View generateLayoutView(@NonNull Context context) {
        return new ImageView(context);// 为管理器提供加载图片控件
      }
    });
    String imageUrl = "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1501571369&di=8f91ba1c465aef47dbad810a7f7c5e21&src=http://pic40.nipic.com/20140408/12191421_182002234354_2.jpg";
    linearLayoutHelper.setLayoutViewBindListener(new BindListener(imageUrl));// 设置图加载的监听[绑定]
    linearLayoutHelper.setLayoutViewUnBindListener(new UnBindListener(imageUrl));// 设置图加载的监听[解绑]
    linearLayoutHelper.setAspectRatio(3.0f);// 设置纵横比[宽与高的比例][不知何用][布局大小有变化]
    linearLayoutHelper.setDividerHeight(10);// 分割线的高度
    FirstAdapter secondAdapter = new FirstAdapter(this, linearLayoutHelper, 10);
    delegateAdapter.addAdapter(secondAdapter);


    FirstAdapter thirdAdapter = new FirstAdapter(this, new LinearLayoutHelper(), 10);
    delegateAdapter.addAdapter(thirdAdapter);

    GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3, 10);// 网格样式
    gridLayoutHelper.setBgColor(getResources().getColor(R.color.Magenta));// 背景
    gridLayoutHelper.setWeights(new float[]{15.0f, 25.0f, 60.0f});// 设置比例[特殊][ColumnLayoutHelper][GridLayoutHelper]
    gridLayoutHelper.setHGap(30);// 设置水平间距[特殊][StaggeredGridLayoutHelper][GridLayoutHelper]
    gridLayoutHelper.setVGap(30);// 设置垂直间距[特殊][StaggeredGridLayoutHelper][GridLayoutHelper]
    gridLayoutHelper.setSpanCount(3);// 设置列数[特殊][GridLayoutHelper]
    gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {// 指定某个位置的视图占用多个网格区域。[特殊][GridLayoutHelper]

      @Override
      public int getSpanSize(int position) {
        return 1;
      }
    });
    gridLayoutHelper.setAutoExpand(true);// 末尾自动填充[特殊][GridLayoutHelper]
    FirstAdapter fourthAdapter = new FirstAdapter(this, gridLayoutHelper, 20);// 网格适配器
    delegateAdapter.addAdapter(fourthAdapter);

    StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();// 瀑布流
    staggeredGridLayoutHelper.setLane(3);// 瀑布流列数[特殊][StaggeredGridLayoutHelper]
    FirstAdapter fifthAdapter = new FirstAdapter(this, staggeredGridLayoutHelper, 20);
    delegateAdapter.addAdapter(fifthAdapter);

    firstAdapter.notifyDataSetChanged();// 数据有变化直接进行刷新
  }


  class FirstViewHolder extends RecyclerView.ViewHolder {

    public FirstViewHolder(View itemView) {
      super(itemView);
    }
  }


  class FirstAdapter extends DelegateAdapter.Adapter<FirstViewHolder> {

    private Context context;
    private LayoutHelper layoutHelper;
    private VirtualLayoutManager.LayoutParams layoutParams;
    private int count;

    public FirstAdapter(Context context, LayoutHelper layoutHelper, int count) {
      this(context, layoutHelper, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100), count);
    }

    public FirstAdapter(Context context, LayoutHelper layoutHelper, VirtualLayoutManager.LayoutParams layoutParams, int count) {
      this.context = context;
      this.layoutHelper = layoutHelper;
      this.layoutParams = layoutParams;
      this.count = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
      return layoutHelper;
    }

    @Override
    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_virtual_layout_item_text, parent, false);
      FirstViewHolder firstViewHolder = new FirstViewHolder(itemView);
      return firstViewHolder;
    }

    @Override
    public void onBindViewHolder(FirstViewHolder holder, int position) {
      holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(layoutParams));
    }

    @Override
    protected void onBindViewHolderWithOffset(FirstViewHolder holder, int position, int offsetTotal) {
      ((TextView) holder.itemView.findViewById(R.id.tv_title)).setText(String.valueOf(offsetTotal));
    }

    @Override
    public int getItemCount() {
      return count;
    }

  }


  /**
   * 图片加载监听[绑定]
   *
   * @since 2017年08月01日
   */
  private static class BindListener implements BaseLayoutHelper.LayoutViewBindListener {

    private String imageUrl;

    public BindListener(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    @Override
    public void onBind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
      // 加载图片
      Glide.with(layoutView.getContext()).load(imageUrl).into((ImageView) layoutView);
    }
  }

  /**
   * 图片加载监听[解绑]
   *
   * @since 2017年08月01日
   */
  private static class UnBindListener implements BaseLayoutHelper.LayoutViewUnBindListener {

    private String imageUrl;

    public UnBindListener(String imageUrl) {
      this.imageUrl = imageUrl;
    }

    @Override
    public void onUnbind(View layoutView, BaseLayoutHelper baseLayoutHelper) {
      // 取消加载图片
    }
  }

}
