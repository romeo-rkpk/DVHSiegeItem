package com.danvhae.minecraft.siege.item.items.tickets

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.DVHSiegeItem
import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import com.danvhae.minecraft.siege.item.utils.ItemUtil
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarFlag
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.math.abs

class WildTicket(val minutes:Int) : TicketAbstract(){
    companion object{
        private val TYPE = Material.PAPER
        private val NAME:String = TextUtil.toColor("&a&l야생티켓")
        private val regex = Regex("야생에서 (\\d+)분 머무를 수 있는 티켓")

        private val wildTicketUsing = HashMap<UUID, WildTicketInfo>()
        internal val deathNote = HashSet<UUID>()

        fun abortWhenUsing(player:Player){
            wildTicketUsing[player.uniqueId]?.let {
                it.abort()
                player.sendMessage("사용중이던 야생 티켓은 보상되지 않습니다")
            }
        }

        private fun lore(minute:Int):List<String>{
            return listOf(
                TextUtil.toColor("&f야생으로 이동합니다."),
                TextUtil.toColor("&7&o 잘 다녀오세요"),
                TextUtil.toColor("&7야생에서 ${minute}분 머무를 수 있는 티켓"),
                TextUtil.toColor("")
            )
        }

        private const val MINUTE_LINE = 2

        fun parseItem(stack:ItemStack?): WildTicket?{
            stack?.let{if(it.type != TYPE)return null}?:return null
            val meta = stack.itemMeta?:return null
            meta.lore?.let {
                if(it.size != lore(0).size) return null
                val (minutes) = regex.find(ChatColor.stripColor(it[MINUTE_LINE]))?.destructured?:return null
                return WildTicket(minutes.toIntOrNull()?:return null)
            }?:return null
        }

    }
    override fun useAndTeleport(player: Player) {

        val ticketItem = ItemUtil.targetItem(player, toItemStack())
        if(ticketItem == null){
            player.sendMessage("정말 티켓을 가지고 있는 것이 맞습니까?")
            return
        }
        ticketItem.amount--

        val info = WildTicketInfo(player.uniqueId, minutes)
        info.start()
        wildTicketUsing[player.uniqueId] = info
    }

    override fun toItemStack(): ItemStack {
        val stack = ItemStack(TYPE)
        val meta = stack.itemMeta
        meta.displayName = NAME
        meta.lore = lore(minutes)
        stack.itemMeta = meta
        return stack
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
            timeBar.addPlayer(player)
            bossBarTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(DVHSiegeItem.instance,
                {
                    val duration = Duration.between(timestamp, LocalDateTime.now())
                    /*
                    timeBar.title = if(duration.isNegative){
                        "의문의 보너스 시간 ${duration.seconds}초 부여 중..."
                    }else{
                        var seconds = max(allowedMinutes * 60 - duration.seconds, 0)
                        timeBar.progress = seconds.toDouble() / (allowedMinutes * 60)
                        var minutes = seconds / 60; seconds%=60
                        val hours = minutes / 60; minutes %=60
                        "야생에서 보낼 수 있는 시간... %s%s%s".format(
                            if(hours > 0) "${hours}시간 " else "",
                            if(minutes > 0) "${minutes}분 " else "",
                            "${seconds}초"
                        )
                    }

                     */
                    var seconds = allowedMinutes * 60 - duration.seconds
                    timeBar.title = if(seconds < 0){
                        "의문의 보너스 시간 ${abs(seconds)}초 부여 중..."
                    }else{
                        timeBar.progress = seconds.toDouble() / (allowedMinutes * 60)
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
            mainTaskID = Bukkit.getScheduler().runTaskLater(DVHSiegeItem.instance,
                {
                    abort()
                    wildTicketUsing.remove(uuid)
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