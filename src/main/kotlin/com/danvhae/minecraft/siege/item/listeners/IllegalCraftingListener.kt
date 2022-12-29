package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.item.abstracts.SiegeItem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent

class IllegalCraftingListener :Listener{

    @EventHandler
    fun onUseSiegeItemInCrafting(event:PrepareItemCraftEvent){
        for(stack in event.inventory.matrix){
            stack?:continue
            SiegeItem.parseItem(stack)?:continue
            event.inventory.result = null
            break
        }
    }

    @EventHandler
    fun onCraftingBanItem(event:PrepareItemCraftEvent){
        if(event.recipe?.result?.type == Material.GOLD_SWORD)
            event.inventory.result = null
    }

}