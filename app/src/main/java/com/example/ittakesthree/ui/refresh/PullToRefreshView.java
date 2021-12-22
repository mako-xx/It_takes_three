package com.example.ittakesthree.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

public class PullToRefreshView extends BasePullToRefreshView{
    protected AdapterView<?> mAdapterView;
    protected ScrollView mScrollView;

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PullToRefreshView(Context context) {
        super(context);
        this.context = context;
        init();
    }


    protected void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException("must contain a AdapterView or ScrollView in this layout!");
        }
    }


    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    protected boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 5) {
                View child = mAdapterView.getChildAt(0);
                if (!isHeader) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (child != null && mAdapterView.getFirstVisiblePosition() == 0 && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                if (child != null) {
                    int top = child.getTop();
                    int padding = mAdapterView.getPaddingTop();
                    if (mAdapterView.getFirstVisiblePosition() == 0 && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
                        mPullState = PULL_DOWN_STATE;
                        return true;
                    }
                } else {
                    if (mAdapterView.getFirstVisiblePosition() == 0) {// 这里之前用3可以判断,但现在不行,还没找到原因
                        mPullState = PULL_DOWN_STATE;
                        return true;
                    }
                }

            } else if (deltaY < -5) {
                View lastChild = mAdapterView.getChildAt(mAdapterView.getChildCount() - 1);
                if (!isFoot) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        return false;
    }
}
