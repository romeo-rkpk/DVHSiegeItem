package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.items.MagicSpellItem
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class SiegeMagicSpellItemListener : Listener {

    @EventHandler
    fun onUseSiegeSpellListener(event:PlayerInteractEvent){
        val player = event.player
        val item = event.item?:return

        val spellItem = MagicSpellItem.parseItem(item)?:return
        val remain = spellItem.useItem(player)?:return
        if(remain > 0){
            TextUtil.sendActionBar(player, "&c남은 쿨타임 : ${remain}초")
        }
    }
}