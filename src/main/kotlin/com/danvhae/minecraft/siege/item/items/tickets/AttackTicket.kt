package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.Hangul
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import com.danvhae.minecraft.siege.item.utils.ItemUtil
import org.bukkit.entity.Player

class AttackTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        if(destination.status == SiegeCastleStatus.INIT){
            player.sendMessage("그 별은 주인이 없습니다")
            return
        }else if(destination.status == SiegeCastleStatus.ELIMINATED){
            player.sendMessage("이미 멸망한 별입니다")
            return
        }else if(!DVHSiegeCore.masterConfig.period){
            player.sendMessage("지금은 공성을 개시할 수 없습니다")
            return
        }
        ItemUtil.targetItem(player, toItemStack())?.let { it.amount-- }
        val hangul = Hangul(destination.name.last())
        player.sendMessage("${destination.name}%s 공격하러 갑니다".format(
            if(hangul.isHangul()){
                if(hangul.last() != null)
                    "을"
                else
                    "를"
            }else
                ""
        ))
        player.teleport(destination.attackPosition)
    }
}