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

