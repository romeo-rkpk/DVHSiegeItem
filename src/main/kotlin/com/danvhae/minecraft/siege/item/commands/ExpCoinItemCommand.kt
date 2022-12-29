package com.danvhae.minecraft.siege.item.commands

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.item.items.ExpCoinSiegeItem
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ExpCoinItemCommand : CommandExecutor {
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false;args?:return false;
        if(!PermissionUtil.supportTeamOrConsole(sender)){
            sender.sendMessage("이 메시지를 실행할 권한이 없습니다")
            return false
        }
        val player = sender as? Player ?: return false
        if(args.isEmpty()){
            player.sendMessage("경험치를 입력해주세요")
            return false
        }
        val amount = args[0].toIntOrNull()
        if(amount == null){
            player.sendMessage("${args[0]}은 올바른 정수가 아닙니다")
            return false
        }else if(amount < 0){
            player.sendMessage("경험치가 음수입니다!")
        }

        val stack = ExpCoinSiegeItem(amount).toItemStack()
        if(player.inventory.firstEmpty() == -1){
            for(item in player.inventory){
                item?:continue
                if(item.amount < item.maxStackSize && item.isSimilar(stack))break
            }
            player.sendMessage("인벤토리 여유 공간이 부족합니다")
            return false
        }

        player.inventory.addItem(stack)
        return true

    }
}