package com.cetcme.shipmonitor.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cetcme.shipmonitor.R;
import com.qiuhong.qhlibrary.Utils.DensityUtil;


public class MoreInfoFragment_2 extends Fragment {


    private View view;
    private Context context;

    private String TAG = "MoreInfoFragment_2";

    private TextView pointImage;
    private TextView pointImage2;


    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more_info_fragment_2, container, false);
        context = this.getActivity();

        initView(view);
        rotatePoint();

        return view;
    }


    private void initView(View view) {

        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int margin = DensityUtil.dip2px(getActivity(), 30);
        int frameWidth = (screenWidth - 3 * margin) / 2;

        //改变两个横向layout的宽高
        LinearLayout zhuanpanLayout = (LinearLayout) view.findViewById(R.id.zhuanpan_layout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, frameWidth * 6 / 10);
        params.setMargins(0, margin / 3, 0, 0);
        zhuanpanLayout.setLayoutParams(params);

        view.findViewById(R.id.zhuanpan_layout_2).setLayoutParams(params);

        //设置frame 宽等于高
        FrameLayout frame_1 = (FrameLayout) view.findViewById(R.id.frame_1);
        FrameLayout frame_2 = (FrameLayout) view.findViewById(R.id.frame_2);
        FrameLayout frame_3 = (FrameLayout) view.findViewById(R.id.frame_3);
        FrameLayout frame_4 = (FrameLayout) view.findViewById(R.id.frame_4);
        LinearLayout.LayoutParams newFrameLp = new LinearLayout.LayoutParams(frameWidth , frameWidth);
        newFrameLp.setMargins(margin, 0, 0 ,0);
        frame_1.setLayoutParams(newFrameLp);
        frame_2.setLayoutParams(newFrameLp);
        frame_3.setLayoutParams(newFrameLp);
        frame_4.setLayoutParams(newFrameLp);

        //隐藏第四个转盘
        view.findViewById(R.id.frame_4).setVisibility(View.INVISIBLE);

        //设置执行档位layout尺寸
        int layoutWidth = screenWidth - 2 * margin;
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(layoutWidth,  layoutWidth * 555 / 1620 );
        params1.setMargins(margin, margin * 2, margin, margin);
        view.findViewById(R.id.zhidingdang).setLayoutParams(params1);
    }

    private void rotatePoint() {
        pointImage = (TextView) view.findViewById(R.id.imageView_point);
        pointImage2 = (TextView) view.findViewById(R.id.imageView_point_2);
        rotateImage(pointImage, 0f, 180f);
        rotateImage(pointImage2, 180f, 0f);
    }


    private void rotateImage(View v, float from, float to) {
        // 第二个参数"rotation"表明要执行旋转
        // 0f -> 360f，从旋转360度，也可以是负值，负值即为逆时针旋转，正值是顺时针旋转。
        ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation", from, to);

        // 动画的持续时间，执行多久？
        anim.setDuration(10000);

        // 回调监听
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
            }
        });

        // 正式开始启动执行动画
        anim.start();
    }

}