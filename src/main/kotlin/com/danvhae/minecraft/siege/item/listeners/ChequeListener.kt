package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.EconomyUtil
import com.danvhae.minecraft.siege.core.utils.NumberUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.DVHSiegeItem
import com.danvhae.minecraft.siege.item.events.UseChequeEvent
import com.danvhae.minecraft.siege.item.items.Cheque
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent

class ChequeListener : Listener {

    @EventHandler
    fun onPlayerInteractWithCheque(event: PlayerInteractEvent){
        if(event.action !in arrayOf(Action.RIGHT_CLICK_BLOCK, Action.RIGHT_CLICK_AIR))return
        val cheque = Cheque.parseItem(event.item?:return)?:return
        Bukkit.getPluginManager().callEvent(UseChequeEvent(cheque, event.player, event))
    }

    @EventHandler
    fun onUseCheque(event:UseChequeEvent){
        val cheque = event.cheque
        val amount = cheque.amount
        val stack = event.originEvent.item?:return
        if(stack.amount == 0)return
        stack.amount -= 1

        //DVHSiegeCore.economy!!.depositPlayer(event.player, cheque.amount.toDouble())
        EconomyUtil.deposit(event.player, cheque.amount, "수표 사용", DVHSiegeItem.instance!!)
        event.player.sendMessage("수표를 사용하여 ${amount}(${NumberUtil.numberToHangul(amount)})스타" +
                "를 얻었습니다.")
    }

    @EventHandler
    fun onChequeHeld(event:PlayerItemHeldEvent){
        Cheque.parseItem(event.player.inventory.getItem(event.newSlot)?:return)?.let {
            TextUtil.sendActionBar(event.player, "&b&l${NumberUtil.numberToHangul(it.amount)} &a스타")
        }
    }
}