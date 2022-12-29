package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import org.bukkit.entity.Player

class WorkTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        if(destination.status !in listOf(SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE))
            return
        player.teleport(destination.workPosition)
        player.sendMessage("${destination.name} 일꾼 텔레포트 지점으로 이동하였습니다")

    }
}