package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.SiegeItem
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class Cheque(val amount:Int, val footNote:String? = null) : SiegeItem() {

    companion object{
        private val TYPE = Material.EMERALD
        private val regex = Regex("금액 \\[(\\d+)] 스타")
        private val NAME = TextUtil.toColor("&a[&l수표&f&a]")

        fun parseItem(stack:ItemStack) : Cheque?{
            if(stack.type != TYPE)return null
            val meta = stack.itemMeta?:return null
            if(NAME != meta.displayName)return null
            if(meta.lore.size !in listOf(1, 3))return null
            val amountText = meta.lore[0]
            val (str) = regex.find(ChatColor.stripColor(amountText))?.destructured!!
            val amount = str.toIntOrNull()?:return null
            return if(meta.lore.size == 1){
                Cheque(amount)
            }else{
                Cheque(amount, meta.lore[2])
            }
        }
    }
    override fun toItemStack(): ItemStack {
        val stack = ItemStack(TYPE)
        val meta = stack.itemMeta
        meta.displayName = NAME
        val lore = arrayListOf(
            TextUtil.toColor("&6금액 &f[&e%d&f] &f스타".format(amount)),
        )
        footNote?.let {
            lore.addAll(listOf("", it))
        }
        meta.lore = lore
        stack.itemMeta = meta
        return stack
    }
}