package com.danvhae.minecraft.siege.item.abstracts

import com.danvhae.minecraft.siege.item.items.Cheque
import com.danvhae.minecraft.siege.item.items.MagicSpellItem
import org.bukkit.inventory.ItemStack

abstract class SiegeItem {

    companion object{
        fun parseItem(stack: ItemStack?):SiegeItem?{
            stack?:return null
            TicketAbstract.parseTicket(stack)?.let{return it}
            MagicSpellItem.parseItem(stack)?.let{return it}
            Cheque.parseItem(stack)?.let{return it}
            return null
        }
    }
    abstract fun toItemStack(): ItemStack
}