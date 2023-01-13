package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.enums.SiegeCastleStatus
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.objects.SiegeOperator
import com.danvhae.minecraft.siege.core.objects.SiegePlayer
import com.danvhae.minecraft.siege.core.utils.LocationUtil
import com.danvhae.minecraft.siege.item.DVHSiegeItem
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class WorkTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {

    companion object{
        private val TASK_ID = HashMap<UUID, Int>()
        const val DELAY_TICK = 100
        fun abort(player:Player){
            if(player.uniqueId !in TASK_ID.keys)return
            Bukkit.getScheduler().cancelTask(TASK_ID[player.uniqueId]!!)
            player.sendMessage("움직임으로 인해 귀환 티켓 사용이 취소되었습니다")
            TASK_ID.remove(player.uniqueId)
        }
    }
    override fun useAndTeleport(player: Player) {
        if(player.uniqueId.let { return@let it !in SiegePlayer && it !in SiegeOperator }){
            player.sendMessage("귀하는 공성 플레이어가 아닙니다. 관리자에게 문의하십시오")
            return
        }
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
        val sPlayer = SiegePlayer[player.uniqueId]
        if(sPlayer != null){
            var founded = false
            for(c in LocationUtil.locationAtStars(player.location)){
                if(c.status in listOf(SiegeCastleStatus.PEACEFUL, SiegeCastleStatus.UNDER_BATTLE) && c.team != sPlayer.team){
                    founded = true
                    break
                }
            }

            if(founded){

                //player.sendMessage("제약 조건 발동")
                val id = Bukkit.getScheduler().runTaskLater(DVHSiegeItem.instance, {
                    player.teleport(destination.workPosition)
                    TASK_ID.remove(player.uniqueId)
                    //player.sendMessage("귀환 완료")
                    player.sendMessage("${destination.name} 일꾼 텔레포트 지점으로 이동하였습니다")
                }, DELAY_TICK.toLong()).taskId
                TASK_ID[player.uniqueId] = id
                return
            }

        }
        player.teleport(destination.workPosition)
        player.sendMessage("${destination.name} 일꾼 텔레포트 지점으로 이동하였습니다")

    }
}