Android属性动画上手实现各种效果,包括实现基本的透明度，缩放，平移，旋转，以及组合动画，还有就是自定义动画仿
QQ运动和抛物线动画。效果图如下：
![效果图](https://github.com/linglongxin24/AnimatorDemo/blob/master/screenshorts/effect.gif?raw=true)
#1.为什么要用属性动画
属性动画：顾名思义，属性动画就是通过改变一个控件的属性值而达到动画的效果。是3.0之后新出的动画框架。
注意：只要控件的属性提供了set属性的方法，就可以通过属性动画去操作。
属性动画和普通动画的区别：一个普通的动画，比如当发生位移动画的时候普通的补间动画只是改变了其显示效果
并没有真正去改变其属性，所以当点击位移发生移动后的view是没有任何的效果，因为它的真是位置还在原来的地方，比如去动态改变一个view的背景颜色
补间动画都无法去实现，真正是属性动画的好处。
#2.两个核心类

 * ValueAnimator 
 
 ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
 
 * ObjectAnimator是ValueAnimator的子类
  
  ObjectAnimator是ValueAnimator的子类，他本身就已经包含了时间引擎和值计算，所以它拥有为对象的某个属性设置动画的功能。
  这使得为任何对象设置动画更加的容易。你不再需要实现 ValueAnimator.AnimatorUpdateListener接口，
  因为ObjectAnimator动画自己会自动更新相应的属性值。
  ObjectAnimator的实例和ValueAnimator是类似的，但是你需要描叙该对象，需要设置动画的属性的名字(一个字符串)，以及动画属性值的变化范围。
  举例：
  
   /**
    * 第一个参数：所要作用的目标控件
    * 第二个参数：所要操作该控件的属性值
    * 第三个参数：所要操作的属性的开始值
    * 第四个参数：所要操作属性的结束值
    */
  ObjectAnimator anim = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
  
  /**设置时长**/
  
  anim.setDuration(1000);
  
   /**开始动画**/
   
  anim.start();
  
#3.实现动画效果

* (1)透明度动画

```java
   /**
    * 第一个参数：所要作用的目标控件
    * 第二个参数：所要操作该控件的属性值
    * 第三个参数：所要操作的属性的开始值
    * 第四个参数：所要操作属性的结束值
    */
   ObjectAnimator objectAnimator = ObjectAn
   objectAnimator.setDuration(1000);
   objectAnimator.start();
```

* (2)缩放动画

```java
 /**动画组合**/
 PropertyValuesHolder objectAnimatorScaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
 PropertyValuesHolder objectAnimatorScaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
 /**同时播放两个动画**/
 ObjectAnimator.ofPropertyValuesHolder(iv, objectAnimatorScaleX, objectAnimatorScaleY).setDuration(1000).start();

```

* (3)旋转动画

```java
    ObjectAnimator objectAnimatorScale = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f);
     objectAnimatorScale.setDuration(1000);
     objectAnimatorScale.start();
```

* (4)位移动画

```java
ObjectAnimator objectAnimatorTransl
objectAnimatorTranslate.setDuration
objectAnimatorTranslate.start();

```

* (5)组合动画一：先播放缩放动画，完成后播放旋转动画

```java
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
```

* (6))组合动画二：先播放旋转动画，完成后播放位移动画

```java
AnimatorSet animatorSetGroup2 = new AnimatorSet();
ObjectAnimator objectAnimatorTranslate2 = ObjectAnimator.ofFloat(iv, "translationX", 0f, 500f);
ObjectAnimator objectAnimatorRotateX2 = ObjectAnimator.ofFloat(iv, "rotationX", 0f, 360f);
ObjectAnimator objectAnimatorRotateY2 = ObjectAnimator.ofFloat(iv, "rotationY", 0f, 360f);
animatorSetGroup2.setDuration(1000);
animatorSetGroup2.play(objectAnimatorTranslate2).after(objectAnimatorRotateX2)
        .after(objectAnimatorRotateY2);
animatorSetGroup2.start();
```

* (7)重复动画一：重复的透明度动画-闪烁

```java
ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(iv, "alpha", 0f, 1f);
objectAnimator2.setDuration(500);
objectAnimator2.setRepeatCount(3);
objectAnimator2.start();
```

* (8)重复动画二：重复的位移动画-抖动

```java
 ObjectAnimator objectAnimatorTranslate3 = ObjectAnimator.ofFloat(iv, "translationX", -50f, 50f);
 objectAnimatorTranslate3.setDuration(500);
 objectAnimatorTranslate3.setRepeatCount(3);
 objectAnimatorTranslate3.start();objectAnimator2.start();
```

* (9)动态改变控件颜色

```java
  ObjectAnimator objectAnimatorBg = ObjectAnimator.ofInt(iv, "backgroundColor", Color.BLUE, Color.YELLOW, Color.RED);
  objectAnimatorBg.setDuration(3000);
  objectAnimatorBg.start();
```
 
* (10)仿QQ运动动画

```java
   /**
       * 为进度设置动画
       * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
       * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
       * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
       * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
       * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
       *
       * @param last
       * @param current
       */
      @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
      private void setAnimation(float last, float current, int length) {
          ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
          progressAnimator.setDuration(length);
          progressAnimator.setTarget(currentAngleLength);
          progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
              @Override
              public void onAnimationUpdate(ValueAnimator animation) {
                  currentAngleLength = (float) animation.getAnimatedValue();
                  invalidate();
              }
          });
          progressAnimator.start();
      }
```

* (11)抛物线动画

```java
  
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
```

#4.[GiHub](https://github.com/linglongxin24/AnimatorDemo)

