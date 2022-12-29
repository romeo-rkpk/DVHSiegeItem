package com.danvhae.minecraft.siege.item.abstracts

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.objects.SiegeCastle
import com.danvhae.minecraft.siege.core.utils.Hangul
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.items.AttackTicket
import com.danvhae.minecraft.siege.item.items.WorkTicket
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import sun.security.krb5.internal.Ticket

abstract class StarTicketAbstract(val destination:SiegeCastle) :TicketAbstract() {


    override fun toItemStack(): ItemStack {
        val result = ItemStack(
            if(this is WorkTicket) Material.WATCH
            else Material.PAPER
        )

        val meta = result.itemMeta
        val color = when(destination.id){
            "ALTAIR" -> "&6"; "REGULUS" -> "&2"; "VEGA" ->"&8"; "DENEB" -> "&b"
            "ACRUX" -> "&e"; "SIRIUS" -> "&3"; else -> "&f"
        }

        val prefix = if(this is AttackTicket) "공격" else "귀환"

        meta.displayName = TextUtil.toColor("&f&l[ &c&l${prefix} &f&l] ${color}&l${destination.name}")
        val hasLastHangul = Hangul(destination.name[destination.name.lastIndex]).last() != null
        meta.lore = arrayListOf(
            TextUtil.toColor(
                "&f%s%s".format(destination.name,
                    if(this is AttackTicket){
                        "${if(hasLastHangul)"을" else "를"} 공격합니다."
                    }
                    else{
                        "${if(hasLastHangul)"으" else ""}로 이동합니다."
                    }
                )
            )
        )

        result.itemMeta = meta
        return result
    }
}