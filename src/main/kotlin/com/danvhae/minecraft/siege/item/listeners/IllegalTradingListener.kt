package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.item.abstracts.SiegeItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

class IllegalTradingListener : Listener{

    @EventHandler
    fun useSiegeItemInTrading(event:InventoryClickEvent){
        if(event.inventory.type != InventoryType.MERCHANT)return
        if(event.whoClicked.location.world.name != DVHSiegeCore.masterConfig.wildWorldName)return
        SiegeItem.parseItem(event.currentItem)?:return
        event.isCancelled = true
    }
}