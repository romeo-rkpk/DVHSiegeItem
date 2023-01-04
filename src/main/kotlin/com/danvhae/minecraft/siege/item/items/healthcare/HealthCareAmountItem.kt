package com.danvhae.minecraft.siege.item.items.healthcare

import com.danvhae.minecraft.siege.item.abstracts.HealthCareItemAbstract
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HealthCareAmountItem(val amount: Int) : HealthCareItemAbstract(){

    companion object{
        fun parseItem(stack:ItemStack?): HealthCareAmountItem?{
            if(!commonParseItem(stack))return null;
            val meta = stack!!.itemMeta!!
            val regex = Regex("(\\d+)의 체력을 회복한다.");
            //return null;
            val match = regex.find(ChatColor.stripColor(meta.lore[1]), 0) ?: return null
            val (amount) = match.destructured;
            //val amount = result.groupValues[0].toInt()
            return HealthCareAmountItem(amount.toInt())
        }
    }

    override fun effectAmountString(): String {
        return amount.toString()
    }

    override fun applyEffect(player: Player) {
        giveHealth(player, amount.toDouble())
    }
}