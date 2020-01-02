# Bedrock Break Helper

[中文](./README_zh-CN.md)

![img](./imgs/cover@0.5x.png)

* Helps you breaking bedrocks using Myren Eario's [method](https://youtu.be/BL98BDMwyWM).
* If [malilib](https://www.curseforge.com/minecraft/mc-mods/malilib) is installed, you can see all rendering effects.
    - But you can still run it with minimal contents(texts only) shown.
    
    
* Shows valid positions to place redstone signal sources.
    - Greed box: possible positions and it's empty;
        - Light Green: TNT can be placed high or low above the ground;
        - Yellowish Green: TNT can only be placed near the ground(explosion should be mostly blocked);
        - Orange: TNT can only be placed high(about 3 blocks) above the ground(explosion should only be slightly blocked);
    - Red box: possible positions but occupied by a non-redstone-singal-source block;
    - Blue box: possible positions and there's a redstone signal source block.
    
    
* Also some texts near center of the screen if Debug Screen is on(F3).
    - You need to point to a piston block to see them.
    - They are relative directions to possible signal source centered at the piston block.
    - Only shows possible positions 1-block far from piston(go and install malilib).
    - Direction you are facing is <font color="green">green</font>.
    - A "+" sign if there's already a signal source block.
    - A red cross <font color="red">"x"</font> if no possible positions.


**ATTENTION: The mechanics of breaking a bedrock is complicated.**
**Relative position of tnt, other damageable block and chunk border may affect the result.**

**The code is okay mostly. But if you fail multiple times, consider change the position of tnt, signal source or use a different method to break the bedrock.**

# How to use
* Use **grave(\`) key**(default) to toggle on/off;
    - **Ctrl + \`** to change rendering distance;
    - **Alt + \`** to toggle if positions two-blocks far from pistons will also be rendered.

# Thanks
Use the code from Fallen-Breath's [calculator](https://github.com/Fallen-Breath/IronHeadHelper) to calculate possible positions.

[Malilib](https://github.com/maruohon/malilib/tree/rift_1.13.2/), I had a hard time setting up Mixins, and using Malilib is much easier.
