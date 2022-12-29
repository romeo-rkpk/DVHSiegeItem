package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import org.bukkit.entity.Player

class AttackTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        if(destination.status == SiegeCastleStatus.INIT){
            player.sendMessage("그 별은 주인이 없습니다")
            return
        }else if(destination.status == SiegeCastleStatus.ELIMINATED){
            player.sendMessage("이미 멸망한 별입니다")
            return
        }

        player.teleport(destination.attackPosition)
    }
}