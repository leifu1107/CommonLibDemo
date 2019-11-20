package com.leifu.commonlib.view.titlebar;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;

import com.leifu.commonlib.view.titlebar.StatusBarUtil;


/**
 * 解决沉浸式标题栏下，键盘兼容问题
 * android:windowSoftInputMode="adjustUnspecified|stateHidden|adjustResize"
 * 注意:1.不能设置android:fitsSystemWindows="true" 2.不加adjustResize会抖动
 * 在onAttachedToWindow() 方法实现KeyboardConflictCompat.assistWindow(getWindow());  //添加这个类似聊天上移软键盘弹出RecyclerView会随之上移mLinearLayoutManager.setStackFromEnd(true);
 * <p>
 * 2.也可以通过ScrollView  android:fitsSystemWindows="true"实现
 */
public class KeyboardConflictCompat {
    private View mChildOfContent;
    private FrameLayout.LayoutParams frameLayoutParams;
    private int usableHeightPrevious;
    private int contentHeight;
    private boolean isfirst = true;
    private int statusBarHeight;

    public static void assistWindow(Window window) {
        new KeyboardConflictCompat(window);
    }

    private KeyboardConflictCompat(Window window) {
        FrameLayout content = (FrameLayout) window.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isfirst) {
                    contentHeight = mChildOfContent.getHeight();//兼容华为等机型
                    isfirst = false;
                }
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
        statusBarHeight = StatusBarUtil.INSTANCE.getStatusBarHeight();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference + statusBarHeight;
                } else {
                    frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                }
            } else {
                frameLayoutParams.height = contentHeight;
            }
            //7､ 重绘Activity的xml布局
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        // 全屏模式下：直接返回r.bottom，r.top其实是状态栏的高度
        return (r.bottom - r.top);
    }
}
