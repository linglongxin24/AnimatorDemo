package cn.bluemobi.dylan.animatordemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    private StepArcView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.iv);
         sv = (StepArcView) findViewById(R.id.sv);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onClick(View v) {
        iv.setBackgroundColor(Color.TRANSPARENT);
        switch (v.getId()) {//透明度动画
            case R.id.animation_alpha:
                /**
                 * 第一个参数：所要作用的目标控件
                 * 第二个参数：所要操作该控件的属性值
                 * 第三个参数：所要操作的属性的开始值
                 * 第四个参数：所要操作属性的结束值
                 */
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
                break;
            case R.id.animation_scale://缩放动画
                /**动画组合**/
                PropertyValuesHolder objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
                PropertyValuesHolder objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
                /**同时播放两个动画**/
                ObjectAnimator.ofPropertyValuesHolder(iv, objectAnimatorScaleX, objectAnimatorScaleY).setDuration(1000).start();

                break;
            case R.id.animation_rotate://旋转动画
                ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
                objectAnimatorScale.setDuration(1000);
                objectAnimatorScale.start();
                break;
            case R.id.animation_translate://位移动画
                ObjectAnimator objectAnimatorTranslate = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
                objectAnimatorTranslate.setDuration(1000);
                objectAnimatorTranslate.start();
                break;
            case R.id.animation_group1://先播放缩放动画，完成后播放旋转动画
                /**动画组合**/
                AnimatorSet animatorSetGroup1 = new AnimatorSet();
                ObjectAnimator objectAnimatorScaleX1 = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1f);
                ObjectAnimator objectAnimatorScaleY1 = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 1f);
                ObjectAnimator objectAnimatorRotateX1 = ObjectAnimator.ofFloat(iv, "rotationX", 0f, 360f);
                ObjectAnimator objectAnimatorRotateY1 = ObjectAnimator.ofFloat(iv, "rotationY", 0f, 360f);
                animatorSetGroup1.setDuration(1000);
                animatorSetGroup1.play(objectAnimatorScaleX1).with(objectAnimatorScaleY1)
                        .before(objectAnimatorRotateX1).before(objectAnimatorRotateY1);
                animatorSetGroup1.start();
                break;
            case R.id.animation_group2://先播放旋转动画，完成后播放位移动画，在xml中设置第二个动画执行的等待时间
                AnimatorSet animatorSetGroup2 = new AnimatorSet();
                ObjectAnimator objectAnimatorTranslate2 = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
                ObjectAnimator objectAnimatorRotateX2 = ObjectAnimator.ofFloat(iv, "rotationX", 0f, 360f);
                ObjectAnimator objectAnimatorRotateY2 = ObjectAnimator.ofFloat(iv, "rotationY", 0f, 360f);
                animatorSetGroup2.setDuration(1000);
                animatorSetGroup2.play(objectAnimatorTranslate2).after(objectAnimatorRotateX2)
                        .after(objectAnimatorRotateY2);
                animatorSetGroup2.start();
                break;
            case R.id.animation_group3://重复的透明度动画
                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
                objectAnimator2.setDuration(500);
                objectAnimator2.setRepeatCount(3);
                objectAnimator2.start();
                break;
            case R.id.animation_group4://重复的位移动画
                ObjectAnimator objectAnimatorTranslate3 = ObjectAnimator.ofFloat(iv, "translationX", -50f, 50f);
                objectAnimatorTranslate3.setDuration(500);
                objectAnimatorTranslate3.setRepeatCount(3);
                objectAnimatorTranslate3.start();
                break;
            case R.id.animation_frame:
                ObjectAnimator objectAnimatorBg = ObjectAnimator.ofInt(iv, "backgroundColor", Color.BLUE, Color.YELLOW, Color.RED);
                objectAnimatorBg.setDuration(3000);
                objectAnimatorBg.start();
                break;
            case R.id.animation_layout:
                sv.setCurrentCount(7000, 1000);
                break;
            case R.id.animation_activity:
                parabola();
                break;
        }

    }

    /**
     * 抛物线动画
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void parabola()
    {

        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(3000);
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>()
        {

            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue)
            {
                /**x方向200px/s ，则y方向0.5 * 200 * t**/
                PointF point = new PointF();
                point.x = 200 * fraction * 3;
                point.y = 0.5f * 200 * (fraction * 3) * (fraction * 3);
                return point;
            }
        });

        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF point = (PointF) animation.getAnimatedValue();
                iv.setX(point.x);
                iv.setY(point.y);

            }
        });
    }
}
