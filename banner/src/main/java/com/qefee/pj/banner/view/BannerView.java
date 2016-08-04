package com.qefee.pj.banner.view;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * BannerView.
 * <ul>
 * <li>date: 16/6/20</li>
 * </ul>
 *
 * @author tongjin
 */
public class BannerView extends ViewPager {

    String[] imageUris;
    SimpleDraweeView[] simpleDraweeViews;

    int currentItem;
    private ScheduledExecutorService executor;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void startAutoScroll() {
        if (imageUris != null && imageUris.length > 1) {
            stopAutoScroll();
            executor = Executors.newSingleThreadScheduledExecutor();
            Runnable command = new Runnable() {
                @Override
                public void run() {
                    selectNextItem();
                }

                private void selectNextItem() {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentItem(++currentItem);
                        }
                    });
                }
            };
            int delay = 2;
            int period = 2;
            TimeUnit timeUnit = TimeUnit.SECONDS;
            executor.scheduleAtFixedRate(command, delay, period, timeUnit);
        }
    }

    public void stopAutoScroll() {
        if (executor != null) {
            executor.shutdownNow();
        }
    }

    public void init(String[] imageUris) {
        this.imageUris = imageUris;
        int size = initSize();
        initSimpleDraweeViews(size);

        this.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE; // 取一个大数字
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                SimpleDraweeView t = simpleDraweeViews[position % simpleDraweeViews.length];
                container.addView(t);

                Uri uri = Uri.parse(BannerView.this.imageUris[position % BannerView.this.imageUris.length]);
                t.setImageURI(uri);

                return t;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        this.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;

                startAutoScroll(); // 手动切换完成后恢复自动播放
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        currentItem = imageUris.length * 1000000; // 取一个中间的大数字, 防止接近边界
        this.setCurrentItem(currentItem);
    }

    private void initSimpleDraweeViews(int size) {
        SimpleDraweeView[] tvs = new SimpleDraweeView[size];

        for (int i = 0; i < tvs.length; i++) {
            tvs[i] = new SimpleDraweeView(getContext());
//            tvs[i].getHierarchy().setPlaceholderImage(R.mipmap.ic_launcher); // 下载完成前的占位图
            tvs[i].getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tvs[i].setLayoutParams(layoutParams);
            simpleDraweeViews = tvs;
        }
    }

    private int initSize() {
        int size;
        if (imageUris.length > 3) {
            size = imageUris.length;
        } else if (imageUris.length > 1) {
            size = imageUris.length * 2; // 小于等于3个时候, 需要扩大一倍, 防止出错
        } else {
            size = 4;
        }
        return size;
    }
}
