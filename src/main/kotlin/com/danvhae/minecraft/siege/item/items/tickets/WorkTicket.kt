package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import org.bukkit.entity.Player

class WorkTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        if(destination.status !in listOf(SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE))
            return
        //if(destination.team != SiegePlayer[player.uniqueId]?.team)
        SiegePlayer[player.uniqueId].let {
            if(it == null || (it.team != destination.team)){
                player.sendMessage("일꾼 티켓을 사용할 수 없습니다. 정말 일하러 가는 것이 맞습니까?")
                if(it == null){
                    player.sendMessage("공성 플레이어 등록 절차를 먼저 진행하여주십시오")
                }
                return@useAndTeleport
            }
        }
        player.teleport(destination.workPosition)
        player.sendMessage("${destination.name} 일꾼 텔레포트 지점으로 이동하였습니다")

    }
}