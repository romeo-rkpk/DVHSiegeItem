package com.danvhae.minecraft.siege.item.abstracts

import org.bukkit.inventory.ItemStack

abstract class SiegeItem {

    companion object{
        fun parseItem(stack: ItemStack?):SiegeItem?{
            stack?:return null

            return null
        }
    }
    abstract fun toItemStack(): ItemStack
}