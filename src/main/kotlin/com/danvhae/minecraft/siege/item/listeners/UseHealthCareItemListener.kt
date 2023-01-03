package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.HealthCareItemAbstract
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class UseHealthCareItemListener:Listener {
    @EventHandler
    fun onItemUse(event:PlayerInteractEvent){
        if (!when(event.action){
            Action.RIGHT_CLICK_AIR -> true
            Action.RIGHT_CLICK_BLOCK -> true
            else -> false
        } ) return

        val healCareItem = HealthCareItemAbstract.parseItem(event.item) ?: return
        val coolTime = healCareItem.applyEffectWithCoolTime(event.player)
        if(coolTime > 0){
            TextUtil.sendActionBar(event.player, "&c남은 쿨타임 %.2f초".format(coolTime))
        }else
            event.item.amount -= 1
    }
}