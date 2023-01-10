package com.danvhae.minecraft.siege.item.commands


import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.EconomyUtil
import com.danvhae.minecraft.siege.core.utils.NumberUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.DVHSiegeItem
import com.danvhae.minecraft.siege.item.items.Cheque
import org.bukkit.command.BlockCommandSender
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MoneyCommand : CommandExecutor {
    companion object{
        val PREFIX = TextUtil.toColor("&a[&6돈&a]&f")

    }
    override fun onCommand(
        sender: CommandSender?, command: Command?, label: String?, args: Array<out String>?
    ): Boolean {
        sender?:return false;args?:return false
        if(sender is BlockCommandSender)return false
        //val eco =  DVHSiegeCore.economy!!
        val player = sender as? Player
        if(args.isEmpty()){
            if(player == null){
                sender.sendMessage("너어는 돈을 가질 수 없습니다.")
                return false
            }
            val amount = EconomyUtil.balance(player)
            player.sendMessage("$PREFIX 소지 금액 : %d스타 (%s)".format(amount, NumberUtil.numberToHangul(amount)))
            return true
        }

        when(args[0]){
            "수표" ->{
                if(player == null){
                    sender.sendMessage("너어는 수표에 관심을 가질 필요가 없습니다")
                    return false
                }
                if(args.size != 2){
                    player.sendMessage("$PREFIX${TextUtil.toColor("&6/스타 수표 <금액> &f: 수표를 만듭니다.")}")
                    return true
                }

                val amount = args[1].toIntOrNull()
                if(amount == null){
                    player.sendMessage("$PREFIX${TextUtil.toColor("&f$args[1] &c은(는) 올바른 금액이 아닙니다. 1 이상의 자연수를 입력해주세요.")}")
                    return false
                }else if(amount < 1){
                    player.sendMessage("$PREFIX${TextUtil.toColor("&c1스타 미만의 수표는 발행할 수 없습니다")}")
                    return false
                }else if(EconomyUtil.balance(player) < amount){
                    player.sendMessage("$PREFIX${TextUtil.toColor("&c스타가 부족합니다.")}")
                    return false
                }
                val cheque = Cheque(amount)
                if(player.inventory.firstEmpty() == -1){
                    for(stack in player.inventory){
                        stack?:continue
                        if(stack.isSimilar(cheque.toItemStack())  && stack.amount < stack.maxStackSize)
                            break
                    }

                    player.sendMessage("$PREFIX${TextUtil.toColor("&c인벤토리 공간이 부족합니다.")}")
                    return false
                }
                //eco.withdrawPlayer(player, amount.toDouble())
                EconomyUtil.withDraw(player, amount, "수표 발행", DVHSiegeItem.instance!!)
                player.inventory.addItem(cheque.toItemStack())
                //eco.withdrawPlayer(player, amount.toDouble())
                player.sendMessage("$PREFIX${amount}스타(${NumberUtil.numberToHangul(amount)}) 수표를 만들었습니다.")


            }
            "수표확인"->{
                if(player == null){
                    sender.sendMessage("너어는 수표를 들고 있을 수가 없습니다")
                    return false
                }
                val cheque = Cheque.parseItem(player.inventory.itemInMainHand)
                if(cheque == null){
                    player.sendMessage("$PREFIX 이것은 아무래도 수표가 아닌 것 같습니다!")
                    return false
                }
                player.sendMessage("$PREFIX : ${NumberUtil.numberToHangul(cheque.amount)} 스타")
                return true
            }
        }

        return true
    }
}