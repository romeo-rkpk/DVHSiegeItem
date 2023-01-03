package com.danvhae.minecraft.siege.item.completers

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class HealthItemCompleter :TabCompleter{
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String>? {
        if(!PermissionUtil.supportTeamOrConsole(sender?:return null))return null
        return TextUtil.onlyStartsWith(when(args!!.size){
            0 -> arrayListOf()
            1 -> arrayListOf("percent", "amount")
            2 -> arrayListOf("1", "10", "20", "50", "100")
            else -> arrayListOf()
        }, args[args.size - 1])
    }
}