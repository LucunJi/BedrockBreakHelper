# Bedrock Break Helper

[English](./README.md)

![img](./imgs/cover@0.5x.png)

* 帮助你用 newcake 的[方法](https://youtu.be/Tu4C3QNBdRY)（[b站搬运](http://acg.tv/av34865175)) 破坏基岩。
* 如果安装了 [malilib](https://www.curseforge.com/minecraft/mc-mods/malilib)，能看到全部的渲染效果。
    - 不过即使不安装，也可以在只有少量显示（仅文字）的情况下运行。
    
    
* 显示可以放置红石信号源的位置。
    - 绿框：可以放置，并且空着的位置；
    - 红框：可以放置的位置，但被非信号源方块占据了；
    - 蓝框：可以放置的位置，并且已经放了一个信号源方块。
    
    
* 在打开 F3 调试界面的时候，也会在屏幕中间显示一些文字。
    - 需要指着一个活塞方块才能看到。
    - 他们是以活塞为中心的，可以放置信号源的方向。
    - 只显示离活塞的距离为 1 的位置（所以快去装 malilib）。
    - 你面对的方向是<font clolor="green">绿色</font>的。
    - 在已经存在信号源方块的位置显示一个加号 “+”。
    - 在没有可以放置的位置时显示一个红色的 <font color="red">“x”</font>。
  
    
**注意：破基岩的机制比较复杂，TNT 的相对位置，其它可破坏的方块和区块边界都可能影响效果。**

**代码大部分情况下是没问题的。但如果你在一个地方失败多次了，考虑更换 TNT 和信号源的位置，或者换个破基岩的方法。**

# 咋用啊
* 默认用 **浪号（`）** 键开关；
    - **Ctrl + \`** 来改变渲染距离；
    - **Alt + \`** 开选择是否渲染离活塞的距离为 2 的位置。

# 感谢
用了 Fallen-Breath 的[铁头功助手](https://github.com/Fallen-Breath/IronHeadHelper)的代码来计算位置。

[Malilib](https://github.com/maruohon/malilib/tree/rift_1.13.2/)，配置 Mixin 很难受，用 Malilib 就方便多了。

感谢 DimensionalDevelopment 的 [Rift-MDK](https://github.com/DimensionalDevelopment/Rift-MDK/tree/1.13.2)。
