package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.DVHSiegeItem
import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import kotlin.math.max

class WildTicket : TicketAbstract(){
    companion object{
        private val TYPE = Material.PAPER
        private val NAME:String = TextUtil.toColor("&a&l야생티켓")
        private val regex = Regex("야생에서 (\\d+)분 머무를 수 있는 티켓")


    }
    override fun useAndTeleport(player: Player) {
        TODO("Not yet implemented")
    }

    override fun toItemStack(): ItemStack {
        TODO("Not yet implemented")
    }

    private class WildTicketInfo(val uuid:UUID, val allowedMinutes:Int){
        val timestamp = LocalDateTime.now()
        var mainTaskID:Int? = null
            private set
        var bossBarTaskID:Int? = null
            private set
        private val timeBar = Bukkit.createBossBar("야생 잔여시간", BarColor.GREEN, BarStyle.SOLID, BarFlag.DARKEN_SKY)

        fun start(){
            val player = Bukkit.getPlayer(uuid)?:return
            bossBarTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(DVHSiegeItem.instance,
                {
                    val duration = Duration.between(timestamp, LocalDateTime.now())
                    timeBar.title = if(duration.isNegative){
                        "의문의 보너스 시간 ${duration.seconds}초 부여 중..."
                    }else{
                        var seconds = max(allowedMinutes * 60 - duration.seconds, 0)
                        var minutes = seconds / 60; seconds%=60
                        val hours = minutes / 60; minutes %=60
                        "야생에서 보낼 수 있는 시간... %s%s%s".format(
                            if(hours > 0) "${hours}시간 " else "",
                            if(minutes > 0) "${minutes}분 " else "",
                            "${seconds}초"
                        )
                    }
                }
                , 5L, 20L)


            player.teleport(Bukkit.getWorld(DVHSiegeCore.masterConfig.wildWorldName)!!.spawnLocation)
            timeBar.removeAll()
            mainTaskID = Bukkit.getScheduler().runTaskLater(DVHSiegeItem.instance,
                {
                    bossBarTaskID?.let { Bukkit.getScheduler().cancelTask(it) }
                    player.teleport(DVHSiegeCore.masterConfig.meetingRoom.toLocation()!!)
                }
                , allowedMinutes.toLong() * 60 * 20 ).taskId
        }

        fun abort(){
            for(id in listOf(mainTaskID, bossBarTaskID))
                id?.let { Bukkit.getScheduler().cancelTask(it) }
            timeBar.removeAll()
        }

    }
}