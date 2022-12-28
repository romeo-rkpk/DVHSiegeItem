package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.item.abstracts.StarTicketAbstract
import org.bukkit.entity.Player

class AttackTicket(destination:SiegeCastle) : StarTicketAbstract(destination) {
    override fun useAndTeleport(player: Player) {
        TODO("Not yet implemented")
    }
}