package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.SiegeItem
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ExpCoinSiegeItem(val amount:Int) : SiegeItem() {
    companion object{
        private val regex = Regex("사냥의 정수 \\[ (\\d+) ]")
        private val TYPE = Material.MAGMA_CREAM
        fun parseItem(stack:ItemStack):ExpCoinSiegeItem?{
            if(stack.type != TYPE)return null
            val text = ChatColor.stripColor(stack.itemMeta.displayName?:return null)
            val (amount) = regex.find(text)?.destructured?:return null
            return ExpCoinSiegeItem(amount.toIntOrNull()?:return null)
        }
    }
    override fun toItemStack(): ItemStack {

        val stack = ItemStack(TYPE)
        val meta = stack.itemMeta
        meta.displayName = TextUtil.toColor("&l&4사냥&7의 정수 [ &a%d &7]".format(amount))
        meta.lore = arrayListOf(
            TextUtil.toColor("&4전투&7의 경험이 담겨있는 &b정수이다."),
            TextUtil.toColor("&6우클릭 &7시 &a경험치&7를 흡수할 수 있다.")
        )
        stack.itemMeta = meta
        return stack
    }
}