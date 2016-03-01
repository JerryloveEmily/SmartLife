package com.jerry.smartlife.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jerry.smartlife.R;
import com.jerry.smartlife.app.AppConst;
import com.jerry.smartlife.utils.DensityUtil;
import com.jerry.smartlife.utils.SharedPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {

    @Bind(R.id.btn_enter_main)
    Button btnEnterMain;
    @Bind(R.id.vp_pager)
    ViewPager mViewPager;
    @Bind(R.id.ll_point)
    LinearLayout mLLPoint;
    @Bind(R.id.v_guide_red_point)
    View mRedPoint;

    private GuidePagerAdapter mAdapter;
    private GuidePagerChangeListener mPagerChangeListener;
    private List<ImageView> imageViews;
    private int mPointDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
        initData();
    }


    private void initView() {
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
    }

    private void initEvent() {
        btnEnterMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 保存第一次启动的状态
                saveFirstStartStatus();
                enterActivity(MainActivity.class, true);
            }
        });
        mPagerChangeListener = new GuidePagerChangeListener();
        mViewPager.addOnPageChangeListener(mPagerChangeListener);

        // 红点布局完成后，计算两点之间的距离
        mRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT > 15){
                    mRedPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }else {
                    mRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                mPointDistance = mLLPoint.getChildAt(1).getLeft() - mLLPoint.getChildAt(0).getLeft();
                System.out.println("两个点之间的距离: " + mPointDistance);
            }
        });
    }

    private void initData() {
        int[] imgs = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        imageViews = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            // 初始化图片
            ImageView guideImageView = new ImageView(this);
            guideImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            guideImageView.setImageResource(imgs[i]);
            imageViews.add(guideImageView);
            // 初始化3个点视图
            View vPoint = new View(this);
            vPoint.setBackgroundResource(R.drawable.gray_dot);
            int dp = 8;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            DensityUtil.dp2px(this,dp),
                            DensityUtil.dp2px(this,dp));
            if (i != 0){
                params.leftMargin = DensityUtil.dp2px(this,dp);
            }
            vPoint.setLayoutParams(params);

            mLLPoint.addView(vPoint);
        }

        mAdapter = new GuidePagerAdapter();
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 保存第一次启动的状态
     */
    private void saveFirstStartStatus(){
        SharedPUtil.setBoolean(this, AppConst.ISFIRSTSTART, true);
    }

    class GuidePagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View child = imageViews.get(position);
            container.addView(child);
            return child;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuidePagerChangeListener implements ViewPager.OnPageChangeListener {

        public GuidePagerChangeListener(){

        }

        /**
         * 页面滑动过程中触发
         * @param position              当前停留页面的位置
         * @param positionOffset        偏移的百分比
         * @param positionOffsetPixels  偏移的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 计算红点的移动距离
            float redPointDis = mPointDistance * (position + positionOffset);
            RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) mRedPoint.getLayoutParams();
            rlParams.leftMargin = Math.round(redPointDis);
            mRedPoint.setLayoutParams(rlParams);
        }

        @Override
        public void onPageSelected(int position) {
            // position在最后一页的时候显示，立即体验按钮
            if (position == imageViews.size() - 1){
                btnEnterMain.setVisibility(View.VISIBLE);
            }else {
                btnEnterMain.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(mPagerChangeListener);
        super.onDestroy();
    }
}
