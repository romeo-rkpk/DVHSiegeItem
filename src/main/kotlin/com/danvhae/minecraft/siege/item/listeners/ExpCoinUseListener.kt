package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.item.items.ExpCoinSiegeItem
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class ExpCoinUseListener : Listener {
    @EventHandler
    fun onRightClickItem(event: PlayerInteractEvent){
        if(event.action !in listOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR))
            return

        val stack = event.item?:return
        val coin = ExpCoinSiegeItem.parseItem(stack)?:return
        stack.amount--
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "dpre add ${event.player.name} ${coin.amount}")
    }
}