package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import com.danvhae.minecraft.siege.item.items.tickets.WildTicket
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class TicketItemListener: Listener {

    @EventHandler
    fun onTicketUse(event:PlayerInteractEvent){
        if(event.action !in listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK))return
        val player = event.player

        val ticket = TicketAbstract.parseTicket(event.item?:return)?:return
        /*
        if(ticket is WildTicket){
            if(!player.isSneaking){
                player.sendMessage("야생 티켓은 웅크린 상태에서만 사용할 수 있습니다")
                return
            }
        }

         */
        if(!player.isSneaking){
            player.sendMessage("웅크린 상태에서 티켓을 사용할 수 있습니다")
            return
        }

        ticket.useAndTeleport(player)
    }
}