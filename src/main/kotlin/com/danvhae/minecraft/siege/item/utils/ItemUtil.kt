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

        fun giveItemIfAvailable(player:Player, item:ItemStack):Boolean{
            var founded = false
            if(player.inventory.firstEmpty() != -1){
                founded = true
            }else{
                for(slot in player.inventory){
                    slot?:continue
                    if(slot.isSimilar(item) && slot.amount < slot.maxStackSize){
                        founded = true
                        break
                    }
                }
            }

            if(founded)
                player.inventory.addItem(item)
            return founded
        }
    }
}