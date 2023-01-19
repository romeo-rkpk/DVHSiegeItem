package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegeOperator
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.Hangul
import com.danvhae.minecraft.siege.core.utils.LocationUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import com.danvhae.minecraft.siege.item.utils.ItemUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class AttackTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        if(player.uniqueId.let { return@let it !in SiegePlayer && it !in SiegeOperator }){
            player.sendMessage("귀하는 공성 플레이어가 아닙니다. 관리자에게 문의하십시오")
            return
        }
        if(destination.status == SiegeCastleStatus.INIT){
            player.sendMessage("그 별은 주인이 없습니다")
            return
        }else if(destination.status == SiegeCastleStatus.ELIMINATED){
            player.sendMessage("이미 멸망한 별입니다")
            return
        }else if(!DVHSiegeCore.masterConfig.period){
            player.sendMessage("지금은 공성을 개시할 수 없습니다")
            return
        }else if(LocationUtil.attacking(player)){
            player.sendMessage("공성 중에는 이 티켓을 사용할 수 없습니다")
            return
        }
        ItemUtil.targetItem(player, toItemStack())?.let { it.amount-- }
        SiegePlayer[player.uniqueId]?.let {
            if(destination.team == it.team){
                player.sendMessage(TextUtil.toColor("&c&l★50만 스타★&f가 &7&l우주의 먼지&f가 되었습니다."))

                Bukkit.getServer().broadcastMessage(TextUtil.toColor(
                    if(it.isOwner)
                        "${player.name}가 &c&l>&6&l>&e&l> &a&l자신의 성 &b&l<&9&l<&5&l< &f을 &4&l공격&f중입니다! \n"
                    else
                        "${player.name}가 &4&l쿠데타&f를 계획한 것 같군요! 모두 ${player.name}의 용기에 커다란 환호와 박수 부탁드립니다!"
                    )
                )
                return
            }
        }
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