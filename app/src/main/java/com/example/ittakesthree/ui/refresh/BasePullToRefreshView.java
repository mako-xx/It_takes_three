package com.example.ittakesthree.ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ittakesthree.R;

public abstract class BasePullToRefreshView extends LinearLayout {
    protected static final String TAG = "PullToRefreshView";
    // refresh states
    protected static final int PULL_TO_REFRESH = 2;
    protected static final int RELEASE_TO_REFRESH = 3;
    public static final int REFRESHING = 4;
    // pull state
    protected static final int PULL_UP_STATE = 0;
    protected static final int PULL_DOWN_STATE = 1;
    protected int mLastMotionY;
    protected View mHeaderView;
    protected View mFooterView;
    protected int mHeaderViewHeight;
    protected int mFooterViewHeight;
    protected ImageView mHeaderImageView;
    // protected ImageView mFooterImageView;
    protected TextView mHeaderTextView;// 表头提示语
    protected TextView mFooterTextView;// 表尾提示语
    protected RelativeLayout mHeaderProgressBar;// 表头进度条
    protected RelativeLayout mFooterProgressBar;// 表尾进度条
    protected LayoutInflater mInflater;
    public int mHeaderState;
    public int mFooterState;

    protected boolean isFoot = true;// 是否允许上拉加载
    public boolean isHeader = true;// 是否允许下拉刷新
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    protected int mPullState;
    /**
     * 变为向下的箭头,改变箭头方向
     */
    protected RotateAnimation mFlipAnimation;
    /**
     * 变为逆向的箭头,旋转
     */
    protected RotateAnimation mReverseFlipAnimation;
    /**
     * footer refresh listener
     */
    protected OnFooterRefreshListener mOnFooterRefreshListener;
    /**
     * footer refresh listener
     */
    protected OnHeaderRefreshListener mOnHeaderRefreshListener;
    protected Context context;

    public BasePullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BasePullToRefreshView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    protected void init() {
        // Load all of the animations we need in code rather than through XML
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        // header trendLeftView 在此添加,保证是第一个添加到linearlayout的最上端
        addHeaderView();
    }

    MaterialHeader materialHeader;

    protected void addHeaderView() {
        // header trendLeftView
        mHeaderView = mInflater.inflate(R.layout.view_refresh_header, this, false);

        mHeaderImageView = (ImageView) mHeaderView.findViewById(R.id.refresh_image);
        mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.refresh_text);

        mHeaderProgressBar = (RelativeLayout) mHeaderView.findViewById(R.id.refreshProgressLayout);
        materialHeader = new MaterialHeader(getContext());
        materialHeader.setPadding(0, 20, 0, 20);
        mHeaderProgressBar.removeAllViews();
        mHeaderProgressBar.addView(materialHeader);

        // header layout
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, mHeaderViewHeight);
        // 设置topMargin的值为负的header View高度,即将其隐藏在最上方
        params.topMargin = -(mHeaderViewHeight);
        // mHeaderView.setLayoutParams(params1);
        addView(mHeaderView, params);
    }

    public void setIsHead(boolean isHead) {
        this.isHeader = isHead;

    }

    public void setIsFooter(boolean isFoot) {
        this.isFoot = isFoot;

    }

    protected void addFooterView() {
        // footer trendLeftView
        mFooterView = mInflater.inflate(R.layout.view_refresh_footer, this, false);
        // mFooterImageView = (ImageView)
        // mFooterView.findViewById(R.id.load_image);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.load_text);
        mFooterProgressBar = (RelativeLayout) mFooterView.findViewById(R.id.loadProgressLayout);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin
        // =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
        // getHeight()什么时候会赋值,稍候再研究一下
        // 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // footer trendLeftView 在此添加保证添加到linearlayout中的最后
        addFooterView();
        initContentAdapterView();
    }

    protected abstract void initContentAdapterView();

    protected void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                if (isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent已经记录
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {
                    // PullToRefreshView执行下拉
                    headerPrepareToRefresh(deltaY);
                    // setHeaderPadding(-mHeaderViewHeight);
                } else if (mPullState == PULL_UP_STATE) {
                    // PullToRefreshView执行上拉
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
//					if (Math.abs(topMargin) >= mHeaderViewHeight + mFooterViewHeight) {
                    if (Math.abs(topMargin) > 0) {
                        // 开始执行footer 刷新
                        footerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        boolean flag = super.onTouchEvent(event);
        return flag;
    }

    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    protected abstract boolean isRefreshViewScroll(int deltaY);

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    protected void headerPrepareToRefresh(int deltaY) {

        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header trendLeftView 的提示状态

        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText("松开后加载");
            // mHeaderImageView.clearAnimation();
            // mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
            // mHeaderImageView.clearAnimation();
            // mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderTextView.setText("下拉刷新");
            mHeaderState = PULL_TO_REFRESH;
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header trendLeftView
     * 高度是一样，都是通过修改header view的topmargin的值来达到
     *
     * @param deltaY ,手指滑动的距离
     */
    protected void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 如果header trendLeftView topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer trendLeftView 完全显示出来了，修改footer trendLeftView 的提示状态
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight) && mFooterState != RELEASE_TO_REFRESH) {
            mFooterTextView.setText("松开后加载");
            // mFooterImageView.clearAnimation();
            // mFooterImageView.startAnimation(mFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
            // mFooterImageView.clearAnimation();
            // mFooterImageView.startAnimation(mFlipAnimation);
            mFooterTextView.setText("上拉加载更多");
            mFooterState = PULL_TO_REFRESH;
        }
    }

    /**
     * 修改Header trendLeftView top margin的值
     */
    protected int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了
        // 表示如果是在上拉后一段距离,然后直接下拉

        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }

        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * header 正在刷新
     */
    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText("正在刷新");
        materialHeader.onUIRefreshBegin();
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }

    }

    /**
     * footer 正在刷新
     */
    protected void footerRefreshing() {
        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        // mFooterImageView.setVisibility(View.GONE);
        // mFooterImageView.clearAnimation();
        // mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView.setText("正在加载");
        if (mOnFooterRefreshListener != null) {
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
    }

    /**
     * 设置header trendLeftView 的topMargin的值
     *
     * @param topMargin
     * @descriptiontop Margin为0时，说明header trendLeftView 刚好完全显示出来；
     * 为-mHeaderViewHeight时，说明完全隐藏了
     */
    protected void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header trendLeftView 完成更新后恢复初始状态 ,更新上次刷新时间
     *
     * @description hylin 2012-7-31上午11:54:23
     */
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        // mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderImageView.setImageResource(R.mipmap.ic_refresh_arrow);
        mHeaderTextView.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderState = PULL_TO_REFRESH;
        materialHeader.onUIRefreshComplete();
    }

    /**
     * 开始刷新
     */
    public void onHeaderRefreshBegin() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.setImageResource(R.mipmap.ic_refresh_arrow);
        mHeaderTextView.setText("正在刷新");
        materialHeader.onUIRefreshBegin();
        mHeaderProgressBar.setVisibility(View.VISIBLE);

    }

    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
        onHeaderRefreshComplete();
    }

    /**
     * footer trendLeftView 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        // mFooterImageView.setVisibility(View.VISIBLE);
        // mFooterImageView.setImageResource(R.drawable.ic_refresh_arrow);
        mFooterTextView.setText("control_pull_to_refresh_footer");
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * 获取当前header trendLeftView 的topMargin
     */
    protected int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    public void setOnHeaderRefreshListener(OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    public void setOnFooterRefreshListener(OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    /**
     * 表尾刷新监听器
     */
    public interface OnFooterRefreshListener {
        public void onFooterRefresh(BasePullToRefreshView view);
    }

    /**
     * 表头刷新监听器
     */
    public interface OnHeaderRefreshListener {
        public void onHeaderRefresh(BasePullToRefreshView view);
    }
}
