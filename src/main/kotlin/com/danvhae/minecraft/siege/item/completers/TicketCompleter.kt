package com.danvhae.minecraft.siege.item.completers

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class TicketCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        sender?:return arrayListOf();args?:return arrayListOf()
        if(!PermissionUtil.supportTeamOrConsole(sender))return arrayListOf()
        val result = ArrayList<String>()
        when(args.size){
            1->{
                for(castle in SiegeCastle.DATA.values)
                    result.add(castle.id)
                result.addAll(listOf("wild"))

            }

            2 ->{
                result.addAll(listOf("attack", "work"))
            }

            3 ->{
                for(p in Bukkit.getOnlinePlayers())
                    result.add(p.name)
            }
        }
        return TextUtil.onlyStartsWith(result, args[args.lastIndex])
    }
}