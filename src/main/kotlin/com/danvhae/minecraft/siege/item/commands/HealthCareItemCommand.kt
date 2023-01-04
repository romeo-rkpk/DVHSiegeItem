package com.danvhae.minecraft.siege.item.commands

import com.danvhae.minecraft.siege.core.utils.PermissionUtil
import com.danvhae.minecraft.siege.item.abstracts.HealthCareItemAbstract
import com.danvhae.minecraft.siege.item.items.healthcare.HealthCareAmountItem
import com.danvhae.minecraft.siege.item.items.healthcare.HealthCarePercentItem
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HealthCareItemCommand:CommandExecutor {
    private fun giveWhenEmpty(player:Player, item:ItemStack):Boolean{
        if(player.inventory.firstEmpty() == -1)
            return false;

        player.inventory.addItem(item);
        return true;
    }
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        if(!PermissionUtil.supportTeamOrConsole(sender?:return false))return false
        if(args == null)return false;
        //val player: Player;

        return when(args.size){
            0 -> {
                sender!!.sendMessage("/health-item <type> <amount> [player]")
                false
            }

            1,2,3 ->{
                val player = if(args.size == 2){
                    sender as? Player ?: return false;
                }else{
                    Bukkit.getPlayer(args[2]) ?: return false;
                }
                val healItem: HealthCareItemAbstract =
                if(args[0].equals("percent", true)){
                    val amount = args[1].toDoubleOrNull()?:return false;
                    if(amount > 100)return false
                    HealthCarePercentItem(amount)
                }else if(args[0].equals("amount", true)){
                    HealthCareAmountItem(args[1].toIntOrNull()?:return false)
                }else
                    return false
                //HealthCareItemAbstract
                giveWhenEmpty(player, healItem.toItemStack())
                true
            }
            else -> false
        }
    }
}