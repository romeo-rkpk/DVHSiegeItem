package com.danvhae.minecraft.siege.item.utils

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ItemUtil {

    companion object{
        fun targetItem(player: Player, target: ItemStack): ItemStack?{
            player.inventory.itemInMainHand.let{
                if(it.isSimilar(target)) return it
            }

            for(item in player.inventory){
                item?.let { if(it.isSimilar(target)) return it }?:continue
            }

            return null
        }
    }
}