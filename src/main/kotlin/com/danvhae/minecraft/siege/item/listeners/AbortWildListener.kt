package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.item.items.tickets.WildTicket
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent

class AbortWildListener : Listener {

    @EventHandler
    fun onPlayerTeleport(event:PlayerTeleportEvent){
        if(event.from.world.name != DVHSiegeCore.masterConfig.wildWorldName)return
        if(event.to.world.name == DVHSiegeCore.masterConfig.wildWorldName)return
        WildTicket.abortWhenUsing(event.player)
    }

    @EventHandler
    fun onPlayerDeath(event:PlayerDeathEvent){
        val player = event.entity
        if(player.location.world.name == DVHSiegeCore.masterConfig.wildWorldName)
            WildTicket.abortWhenUsing(player)
    }

    @EventHandler
    fun onPlayerQuit(event:PlayerQuitEvent){
        WildTicket.abortWhenUsing(event.player)
    }

    @EventHandler
    fun onPlayerJoin(event:PlayerJoinEvent){
        val player = event.player
        if(player.location.world.name != DVHSiegeCore.masterConfig.wildWorldName)return
        player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
        player.sendMessage("저런! 야생 티켓을 날려먹은것 같군요!")
    }
}