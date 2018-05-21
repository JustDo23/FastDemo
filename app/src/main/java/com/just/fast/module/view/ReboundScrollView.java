package com.just.fast.module.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.just.utils.log.LogUtils;

/**
 * [弹性 ScrollView][上拉][下拉]
 *
 * @WebUrl [https://github.com/KaiLife/ReboundScrollView]
 * @since 2018年05月21日
 */
public class ReboundScrollView extends ScrollView {

  private View contentView;// 内容布局
  private Rect originalRect = new Rect();// 记录正常布局位置
  private float startY;// 记录手指按下的位置
  private boolean canPullDown;// 顶部向下拉
  private boolean canPullUp;// 底部向上拉
  private boolean isMoved;// 是否移动了布局
  private int deltaY;// 滑动距离可判断方向
  private static final float MOVE_FACTOR = 0.5f;// 运动因子延时效果
  private static final int ANIMATION_TIME = 300;// 动画时间

  public ReboundScrollView(Context context) {
    super(context);
  }

  public ReboundScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ReboundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    if (getChildCount() > 0) {
      contentView = getChildAt(0);
    }
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (contentView != null) {
      originalRect.set(contentView.getLeft(), contentView.getTop(), contentView.getRight(), contentView.getBottom());
    }
  }

  /**
   * 触摸事件拦截
   *
   * @param event 事件
   * @return []消费[]传递
   */
  @Override
  public boolean dispatchTouchEvent(MotionEvent event) {
    LogUtils.e("event.getY() = " + event.getY());
    LogUtils.e("this.getScrollY() = " + this.getScrollY());
    LogUtils.e("this.getHeight() = " + this.getHeight());
    LogUtils.e("contentView.getHeight() = " + contentView.getHeight());
    if (contentView != null) {
      boolean isTouchOutOfScrollView = event.getY() >= this.getHeight() || event.getY() <= 0;// 是否移出了 ScrollView
      if (isTouchOutOfScrollView) {// contentView 移出了 ScrollView
        boundBack();
        return true;// 消费
      }
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          LogUtils.e("ACTION_DOWN");
          canPullDown = isCanPullDown();
          canPullUp = isCanPullUp();
          startY = event.getY();
          break;
        case MotionEvent.ACTION_MOVE:
          LogUtils.e("ACTION_MOVE");
          if (!canPullDown && !canPullUp) {// 移动中既不能顶部下拉又不能底部上拉
            canPullDown = isCanPullDown();
            canPullUp = isCanPullUp();
            startY = event.getY();
            break;
          }
          deltaY = (int) (event.getY() - startY);// 差值
          // 是否应该移动布局
          boolean shouldMove =
              (canPullDown && deltaY > 0)// 可下拉并且手势向下
                  || (canPullUp && deltaY < 0)// 可以上拉并且手势向上
                  || (canPullUp && canPullDown);// 既可以下拉又可以上拉
          if (shouldMove) {
            int offset = (int) (deltaY * MOVE_FACTOR);// 偏移量
            LogUtils.e("offset = " + offset);
            contentView.layout(originalRect.left, originalRect.top + offset, originalRect.right, originalRect.bottom + offset);
            isMoved = true;
          }
          break;
        case MotionEvent.ACTION_UP:
          LogUtils.e("ACTION_UP");
          boundBack();
          break;
      }
    }
    return super.dispatchTouchEvent(event);
  }

  /**
   * 判断是否在顶部并可以向下拉超界
   */
  private boolean isCanPullDown() {
    return getScrollY() <= 0;
  }

  /**
   * 判断是否在底部并可以向下拉超界
   */
  private boolean isCanPullUp() {
    return contentView.getHeight() <= getHeight() + getScrollY();
  }

  /**
   * 回弹至原来位置
   */
  private void boundBack() {
    LogUtils.e("canPullDown = " + canPullDown);
    LogUtils.e("canPullUp = " + canPullUp);
    if (!isMoved) {// 没有移动布局则跳过
      return;
    }
    // 开始动画
    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, contentView.getTop(), originalRect.top);
    translateAnimation.setDuration(ANIMATION_TIME);
    final boolean isPullUp = deltaY < 0;
    LogUtils.e("isPullUp = " + isPullUp);
    translateAnimation.setAnimationListener(new Animation.AnimationListener() {

      @Override
      public void onAnimationStart(Animation animation) {

      }

      @Override
      public void onAnimationEnd(Animation animation) {
        if (onReboundFinishListener != null) {
          onReboundFinishListener.onReboundFinish(isPullUp);
        }
      }

      @Override
      public void onAnimationRepeat(Animation animation) {

      }
    });
    contentView.startAnimation(translateAnimation);
    // 回到正常位置
    contentView.layout(originalRect.left, originalRect.top, originalRect.right, originalRect.bottom);
    // 标志位更新
    canPullDown = false;
    canPullUp = false;
    isMoved = false;
  }


  /**
   * 回弹结束
   *
   * @author JustDo23
   */
  public interface OnReboundFinishListener {

    /**
     * 反弹结束
     *
     * @param isPullUp [true]向上拉[false]向下拉
     */
    void onReboundFinish(boolean isPullUp);

  }

  private OnReboundFinishListener onReboundFinishListener;

  public void setOnReboundFinishListener(OnReboundFinishListener onReboundFinishListener) {
    this.onReboundFinishListener = onReboundFinishListener;
  }

}
