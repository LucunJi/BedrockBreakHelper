# Bedrock Break Helper

[English](./README.md)

![img](./imgs/cover@0.5x.png)

**由于 1.15 后爆炸计算完全随机，这个模组不会再更新。但可以使用 [这个](https://www.youtube.com/watch?v=o3JBaioeIpc) 手动方法或自动的飞行器来破坏基岩。**

* 帮助你用 Myren Eario 的[方法](https://youtu.be/BL98BDMwyWM)（[b站搬运](http://acg.tv/av34865175)，Myren 的视频搬运已挂，只有 newcake 视频的搬运） 破坏基岩。
* **必须安装** [malilib](https://www.curseforge.com/minecraft/mc-mods/malilib) 和它的依赖项 [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)。
    
    
* 显示可以放置红石信号源的位置。
* 提示框会在被非信号源方块/信号源方块占据的时候变红/变蓝。
* 避免使用闪烁的提示框。闪烁说明它不稳定。
  
    
**注意：破基岩的机制比较复杂，TNT 的相对位置，其它可破坏的方块和区块边界都可能影响效果。**

**代码大部分情况下是没问题的。但如果你在一个地方失败多次了，考虑更换 TNT 和信号源的位置，或者换个破基岩的方法。**

# 咋用啊

1. 放下一个背对基岩的活塞，再在边上放一个 TNT。
2. 在绿框中放置一个红石信号源方块来使活塞伸出。
3. 点燃 TNT，用连点器（推荐 tweakeroo）一直尝试放置一个反向的活塞。
* 默认用 **浪号（`）** 键开关；
    - **Ctrl + \`** 来改变渲染距离；
    - **Alt + \`** 开选择是否渲染离活塞的距离为 2 的位置。

# 感谢
用了 Fallen-Breath 的[铁头功助手](https://github.com/Fallen-Breath/IronHeadHelper)的代码来计算位置。

[Malilib](https://github.com/maruohon/malilib/tree/rift_1.13.2/)，配置 Mixin 很难受，用 Malilib 就方便多了。
