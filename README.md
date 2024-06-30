# SwitchPageTabDemo
 POC of using Tab to switch pages

## Page实现
需求实际上是两级的Tab和Page滑动联动, 
目前是采用Compose实现, 是参考[Compose HorizontalPager二级联动](https://juejin.cn/post/7288628985322733583)和
[nested-horizontal-pager](https://github.com/Ovaltinezz/nested-horizontal-pager),
两个方案都有参考, 第二个方案虽然代码简洁,但是在拖动滑动时, 会有在中间过程滑动不下去的问题. 而第一个方案还没有遇到什么问题.

效果可以下载工程根目录下的apk查看

也可以采用传统View + Fragment实现
