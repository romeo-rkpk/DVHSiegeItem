package com.danvhae.minecraft.siege.item.abstracts

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class TicketAbstract :SiegeItem(){

    companion object{
        fun parseTicket(stack: ItemStack):TicketAbstract?{
            return null
        }
    }
    abstract fun useAndTeleport(player: Player)

}