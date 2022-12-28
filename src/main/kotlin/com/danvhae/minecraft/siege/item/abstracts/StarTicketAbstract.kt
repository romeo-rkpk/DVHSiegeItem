package com.danvhae.minecraft.siege.item.abstracts

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sun.security.krb5.internal.Ticket

abstract class StarTicketAbstract(val destination:SiegeCastle) :TicketAbstract() {


    override fun toItemStack(): ItemStack {
        TODO("WIP")
    }
}