package com.danvhae.minecraft.siege.item.items.healthcare

import com.danvhae.minecraft.siege.item.abstracts.HealthCareItemAbstract
import org.bukkit.ChatColor
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class HealthCarePercentItem(val percent: Double) : HealthCareItemAbstract(){

    companion object{
        fun parseItem(stack: ItemStack?): HealthCarePercentItem?{
            if(!commonParseItem(stack))return null;
            val meta = stack!!.itemMeta!!;
            val regex = Regex("(\\d+.\\d+)%의 체력을 회복한다.");
            val match = regex.find(ChatColor.stripColor(meta.lore[1]), 0) ?: return null
            val percent = match.destructured.toList()[0].toDouble()
            //Bukkit.getLogger().info("parsed $percent")
            return HealthCarePercentItem(percent)
        }
    }

    override fun effectAmountString(): String {
        return "%.2f%%".format(percent)
    }

    override fun applyEffect(player: Player) {
        giveHealth(player,player.getAttribute(Attribute.GENERIC_MAX_HEALTH).value * percent / 100)
    }
}