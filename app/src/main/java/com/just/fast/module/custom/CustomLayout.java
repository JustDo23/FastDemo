package com.just.fast.module.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import com.just.fast.R;

/**
 * 关于 ViewGroup 官方自定义学习
 *
 * @author JustDo23
 * @webUrl [https://developer.android.com/reference/android/view/ViewGroup.html]
 * @since 2017年07月13日
 */
@RemoteViews.RemoteView
public class CustomLayout extends ViewGroup {

  private int mLeftWidth;// 左边宽度
  private int mRightWidth;// 右边宽度

  private Rect mTmpContainerRect = new Rect();
  private Rect mTmpChildRect = new Rect();

  public CustomLayout(Context context) {
    super(context);
  }

  public CustomLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  /**
   * 延时子控件的按下状态，避免包含ListView之类的可以滚动的控件
   *
   * @return true, 可以包含滚动控件 false, 不能包含可以滚动的控件
   */
  @Override
  public boolean shouldDelayChildPressedState() {
    return super.shouldDelayChildPressedState();
  }

  /**
   * 计算
   *
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int childCount = getChildCount();// 获取子控件个数

    mLeftWidth = 0;
    mRightWidth = 0;

    int maxHeight = 0;
    int maxWidth = 0;
    int childState = 0;

    for (int i = 0; i < childCount; i++) {// 循环所有的子控件
      View childView = getChildAt(i);// 子控件
      if (childView.getVisibility() != GONE) {// 不是隐藏状态
        measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0);// 计算子控件
        LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
        if (layoutParams.position == LayoutParams.POSITION_LEFT) {
          mLeftWidth += Math.max(maxWidth, childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        } else if (layoutParams.position == LayoutParams.POSITION_RIGHT) {
          mRightWidth += Math.max(maxWidth, childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        } else {
          maxWidth += Math.max(maxWidth, childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin);
        }
        maxHeight += Math.max(maxHeight, childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        childState = combineMeasuredStates(childState, childView.getMeasuredState());
      }
    }

    maxWidth += mLeftWidth + mRightWidth;
    maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
    maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

    setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState), resolveSizeAndState(maxHeight, heightMeasureSpec, childState << MEASURED_HEIGHT_STATE_SHIFT));
  }

  /**
   * 布局
   *
   * @param changed
   */
  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    int childCount = getChildCount();// 子控件个数
    int leftPos = getPaddingLeft();
    int rightPos = right - left - getPaddingRight();
    int middleLeft = leftPos + mLeftWidth;
    int middleRight = rightPos - mRightWidth;
    int parentTop = getPaddingTop();
    int parentBottom = bottom - top - getPaddingBottom();
    for (int i = 0; i < childCount; i++) {
      View childView = getChildAt(i);
      if (childView.getVisibility() != GONE) {
        LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
        int measuredWidth = childView.getMeasuredWidth();
        int measuredHeight = childView.getMeasuredHeight();
        if (layoutParams.position == LayoutParams.POSITION_LEFT) {
          mTmpContainerRect.left = leftPos + layoutParams.leftMargin;
          mTmpContainerRect.right = leftPos + measuredWidth + layoutParams.rightMargin;
          leftPos = mTmpContainerRect.right;
        } else if (layoutParams.position == LayoutParams.POSITION_RIGHT) {
          mTmpContainerRect.right = rightPos - layoutParams.rightMargin;
          mTmpContainerRect.left = rightPos - measuredWidth - layoutParams.leftMargin;
          rightPos = mTmpContainerRect.left;
        } else {
          mTmpContainerRect.left = middleLeft + layoutParams.leftMargin;
          mTmpContainerRect.right = middleRight - layoutParams.rightMargin;
        }
        mTmpContainerRect.top = parentTop + layoutParams.topMargin;
        mTmpContainerRect.bottom = parentBottom - layoutParams.bottomMargin;
        Gravity.apply(layoutParams.gravity, measuredWidth, measuredHeight, mTmpContainerRect, mTmpChildRect);
        childView.layout(mTmpChildRect.left, mTmpChildRect.top, mTmpChildRect.right, mTmpChildRect.bottom);
      }
    }
  }


  // ----------------------------------------------------------------------
  // The rest of the implementation is for custom per-child layout parameters.
  // If you do not need these (for example you are writing a layout manager
  // that does fixed positioning of its children), you can drop all of this.

  @Override
  public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new CustomLayout.LayoutParams(getContext(), attrs);
  }

  @Override
  protected LayoutParams generateDefaultLayoutParams() {
    return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new LayoutParams(p);
  }

  @Override
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof LayoutParams;
  }


  /**
   * 自定义 LayoutParams
   *
   * @since s2017年07月13日
   */
  public static class LayoutParams extends MarginLayoutParams {

    private int gravity = Gravity.START | Gravity.TOP;

    public static int POSITION_MIDDLE = 0;
    public static int POSITION_LEFT = 1;
    public static int POSITION_RIGHT = 2;

    private int position = POSITION_MIDDLE;

    public LayoutParams(Context context, AttributeSet attrs) {
      super(context, attrs);// 从 XML 文件中获取属性
      TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLayoutLP);
      gravity = typedArray.getInt(R.styleable.CustomLayoutLP_android_layout_gravity, gravity);
      position = typedArray.getInt(R.styleable.CustomLayoutLP_layout_position, position);
      typedArray.recycle();
    }

    public LayoutParams(int width, int height) {
      super(width, height);
    }

    public LayoutParams(MarginLayoutParams source) {
      super(source);
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
      super(source);
    }
  }
}
