package com.danvhae.minecraft.siege.item.commands

import com.danvhae.minecraft.siege.item.objects.KeepItem
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class KeepItemDataCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?,
        command: Command?,
        label: String?,
        args: Array<out String>?
    ): Boolean {
        sender?:return false;args?:return false
        if(args.isEmpty())return false
        if(args[0] == "load") KeepItem.load()

        return true
    }
}