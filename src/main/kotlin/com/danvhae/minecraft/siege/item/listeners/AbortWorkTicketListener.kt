package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.item.items.tickets.WorkTicket
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class AbortWorkTicketListener : Listener{

    @EventHandler
    fun onPlayerMove(event:PlayerMoveEvent){
        val prev = event.from
        val next = event.to
        if( prev.world.name == next.world.name &&
            prev.x == next.x && prev.y == next.y && prev.z == next.z
            )return

        WorkTicket.abort(event.player)
        //Bukkit.getLogger().warning("${prev.x} ${next.x}, ${prev.y}, ${next.y}")
    }
}