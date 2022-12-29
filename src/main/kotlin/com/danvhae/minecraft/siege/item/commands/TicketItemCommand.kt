package com.danvhae.minecraft.siege.item.commands

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import com.danvhae.minecraft.siege.item.items.tickets.AttackTicket
import com.danvhae.minecraft.siege.item.items.tickets.WildTicket
import com.danvhae.minecraft.siege.item.items.tickets.WorkTicket
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.util.*

class TicketItemCommand : CommandExecutor{
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false;args?:return false;
        if(!PermissionUtil.supportTeamOrConsole(sender)){
            sender.sendMessage("이 명령어를 실행할 권한이 없습니다")
            return false
        }
        if(args.isEmpty())return false

        var player: Player? = null;
        var ticket:TicketAbstract? = null
        var castle:SiegeCastle? = SiegeCastle.DATA[args[0]]


        /*
        if (args[0] == "spawn") {
            ticket = MeetingRoomTicket()
            if(args.size == 2){
                player = Bukkit.getPlayer(args[1])
            }else if(args.size == 3){
                return false
            }else if(sender is Player){
                player = sender as Player
            }else{
                sender.sendMessage("후 얼 유?")
                return false
            }
        }
        else*/

        if (args[0] == "wild") {
            if(args.size == 2){
                if(sender !is Player)return false
                else
                    player = sender as Player

            }else if(args.size == 3){
                player = Bukkit.getPlayer(args[2])?:return false
            }else{
                return false
            }
            val minute = args[1].toIntOrNull()?:return false
            if(minute <= 0)return false
            ticket = WildTicket(minute)

        }else if(args.size >= 2){
            if(args[1].lowercase(Locale.getDefault()) !in listOf("attack", "work"))return false
            if(castle == null)return false
            if(args.size == 3)
                player = Bukkit.getPlayer(args[2])
            else if(args.size > 3)
                return false
            else if(sender is Player)
                player = sender as Player
            else
                return false

            if(args[1] == "attack")
                ticket = AttackTicket(castle)
            else if(args[1] == "work")
                ticket = WorkTicket(castle)
        }

        if(ticket == null){
            sender.sendMessage("알 수 없는 티켓입니다")
            return false
        }
        if(player == null){
            sender.sendMessage("누구에게 티켓을 주라는 건가요?")
            return false
        }

        val MAX_SIZE = player.inventory.size
        var founded = false
        for(i in 0 until MAX_SIZE){
            val temp = player.inventory.getItem(i)?:continue
            if(temp.isSimilar(ticket.toItemStack())){
                founded = true
                break
            }
        }
        if(!founded && player.inventory.firstEmpty() == -1){
            sender.sendMessage("플레이어 인벤토리 여유 공강 부족!!")
            return false
        }

        player.inventory.addItem(ticket.toItemStack())
        return true
    }
}