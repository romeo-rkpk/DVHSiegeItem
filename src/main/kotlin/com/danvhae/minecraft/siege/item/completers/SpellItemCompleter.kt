package com.danvhae.minecraft.siege.item.completers

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.items.MagicSpellItem
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class SpellItemCompleter : TabCompleter{
    override fun onTabComplete(
        sender: CommandSender?, command: Command?, alias: String?, args: Array<out String>?
    ): MutableList<String> {
        sender?:return arrayListOf();args?:return arrayListOf()
        val player = sender as? Player?:return arrayListOf()

        if(args.size != 1)return arrayListOf()
        val result = ArrayList<String>()
        for(spell in MagicSpellItem.items.values){
            result.add(spell.skillID)
        }
        result.add("cooling")
        return TextUtil.onlyStartsWith(result, args[0])
    }
}