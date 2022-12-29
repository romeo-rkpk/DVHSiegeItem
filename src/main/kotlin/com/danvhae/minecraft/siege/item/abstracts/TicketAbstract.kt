package com.danvhae.minecraft.siege.item.abstracts

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.item.items.tickets.AttackTicket
import com.danvhae.minecraft.siege.item.items.tickets.WildTicket
import com.danvhae.minecraft.siege.item.items.tickets.WorkTicket
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class TicketAbstract :SiegeItem(){

    companion object{
        fun parseTicket(stack: ItemStack):TicketAbstract?{

            WildTicket.parseItem(stack)?.let{return it}
            for(castle in SiegeCastle.DATA.values){
                for(ticket in listOf(AttackTicket(castle), WorkTicket(castle))){
                    if(ticket.toItemStack().isSimilar(stack))return ticket
                }
            }

            return null
        }
    }
    abstract fun useAndTeleport(player: Player)

}