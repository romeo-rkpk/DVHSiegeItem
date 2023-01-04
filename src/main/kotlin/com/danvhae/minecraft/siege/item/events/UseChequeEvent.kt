package com.danvhae.minecraft.siege.item.events

import com.danvhae.minecraft.siege.core.abstracts.KotlinBukkitEventAbstract
import com.danvhae.minecraft.siege.item.items.Cheque
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

class UseChequeEvent(val cheque:Cheque, val player:Player, val originEvent:PlayerInteractEvent) : KotlinBukkitEventAbstract() {

}