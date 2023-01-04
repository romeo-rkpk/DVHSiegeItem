package com.danvhae.minecraft.siege.item.commands

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.item.items.MagicSpellItem
import com.danvhae.minecraft.siege.item.utils.ItemUtil
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SiegeSpellItemCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false; args?:return false
        if(!PermissionUtil.supportTeamOrConsole(sender)){
            sender.sendMessage("이 명령어를 실행할 권한이 없습니다!");return false;
        }
        if(args.isEmpty()){
            sender.sendMessage("siege-spell-item cooling")
            sender.sendMessage("siege-spell-item <ID> [<player>]")
            return false
        }

        if(args[0] == "cooling"){
            MagicSpellItem.forceCooling()
            return true
        }

        val spell = MagicSpellItem.items[args[0]]
        if(spell == null){
            sender.sendMessage("알 수 없는 스펠 아이템입니다")
            return false
        }

        val player = sender as? Player ?:return false
        if(!ItemUtil.giveItemIfAvailable(player, spell.toItemStack())){
            sender.sendMessage("플레이어의 인벤토리가 충분하지 않습니다")
            return false
        }
        return true
    }
}