# Bedrock Break Helper

[English](./README.md)

![img](./imgs/cover@0.5x.png)

* 帮助你用 newcake 的[方法](https://youtu.be/Tu4C3QNBdRY)（[b站搬运](http://acg.tv/av34865175)) 破坏基岩。
* **必须安装** [malilib](https://www.curseforge.com/minecraft/mc-mods/malilib) 和它的依赖项 [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)。
    
    
* 显示可以放置红石信号源的位置。
    - 绿/黄绿/橙色框：可以放置，并且空着的位置；
        - 绿：TNT 可以悬空摆，也可以摆在地面附近；
        - 黄绿：TNT 只可以摆在地面附近（离地面两格左右，爆炸被阻挡较多）；
        - 橙色：TNT 只可以悬空摆（离地三格以上，爆炸被阻挡较少）；
    - 红框：可以放置的位置，但被非信号源方块占据了；
    - 蓝框：可以放置的位置，并且已经放了一个信号源方块。
  
    
**注意：破基岩的机制比较复杂，TNT 的相对位置，其它可破坏的方块和区块边界都可能影响效果。**

**代码大部分情况下是没问题的。但如果你在一个地方失败多次，考虑更换 TNT 和信号源的位置，或者换个破基岩的方法。**

# 咋用啊
* 默认用 **浪号（`）** 键开关；
    - **Ctrl + \`** 来改变渲染距离；
    - **Alt + \`** 开选择是否渲染离活塞的距离为 2 的位置。

# 感谢
用了 Fallen-Breath 的[铁头功助手](https://github.com/Fallen-Breath/IronHeadHelper)的代码来计算位置。

[Malilib](https://github.com/maruohon/malilib/tree/rift_1.13.2/)，配置 Mixin 很难受，用 Malilib 就方便多了。