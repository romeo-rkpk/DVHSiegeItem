package com.danvhae.minecraft.siege.item.completers

import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MoneyCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        sender?:return arrayListOf(); args?:return arrayListOf()
        val result = arrayListOf<String>()
        if(args.size == 1){
            result.addAll(listOf("도움", "수표", "수표확인"))
        }else if(args.size == 2){
            if(args[0] == "수표"){
                result.addAll(listOf("1", "10", "100", "5000000", "10000000"))
            }
        }

        return TextUtil.onlyStartsWith(result, args.last())
    }
}