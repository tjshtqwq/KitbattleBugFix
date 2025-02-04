# KitbattleBugFix
** 本插件修复了KitBattle插件的一个bug **  
这个bug会导致玩家战斗时物品消失。在玩家喝汤时，如果Soup-Auto-Disappear启用，那么插件会这样：  
补充生命值 -> 把汤变成碗 -> 延迟1tick后把玩家当前槽位设为空气  
重点就是这个延迟1tick，如果玩家在1tick内切换了槽位，那么这个槽位会被设为空气  
代码如下：  
```
if (var3.getType().equals(this.soup.getType())) {
    if (var2.getHealth() == var2.getMaxHealth()) {
        return;
    }
    var2.setHealth(var2.getHealth() + 7.0 > var2.getMaxHealth() ? var2.getMaxHealth() : var2.getHealth() + 7.0);
    var2.getItemInHand().setType(Material.BOWL);
    if (this.plugin.config.SoupAutoDisappear) {
        (new 4(this, var2)).runTaskLater(this.plugin, 1L); // Delayed 1 tick!!
    }

    ((PlayerData)this.plugin.playerData.get(var2.getName())).addSoupsEaten(var2);
    return;
}
```  
4的实现如下（这个代码是反编译出来的）：  

```
class KBListener$4 extends BukkitRunnable {
    KBListener$4(KBListener var1, Player var2) {
        this.this$0 = var1;
        this.val$p = var2;
    }

    public void run() {
        this.val$p.setItemInHand(this.this$0.air);
    }
}
```   
看懂了吧？setItemInHand(this.this$0.air)害死人呐！
本插件代码非常狗屎，是一个临时修复方法。将就着用吧，我懒得解释了。
