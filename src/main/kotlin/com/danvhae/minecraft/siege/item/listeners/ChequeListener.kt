package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.NumberUtil
import com.danvhae.minecraft.siege.item.events.UseChequeEvent
import com.danvhae.minecraft.siege.item.items.Cheque
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

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

        DVHSiegeCore.economy!!.depositPlayer(event.player, cheque.amount.toDouble())
        event.player.sendMessage("수표를 사용하여 ${amount}(${NumberUtil.numberToHangul(amount)})스타" +
                "를 얻었습니다.${cheque.footNote?.let { " (${it})" }}")
    }
}