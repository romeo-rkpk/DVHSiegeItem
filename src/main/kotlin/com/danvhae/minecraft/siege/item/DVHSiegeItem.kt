package com.danvhae.minecraft.siege.item

import com.danvhae.minecraft.siege.item.commands.SiegeSpellItemCommand
import com.danvhae.minecraft.siege.item.commands.TicketItemCommand
import com.danvhae.minecraft.siege.item.completers.SpellItemCompleter
import com.danvhae.minecraft.siege.item.completers.TicketCompleter
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class DVHSiegeItem : JavaPlugin(){
    companion object{
        var instance:DVHSiegeItem? = null
            get(){return field!!}
            private set

    }

    override fun onEnable() {
        instance = this
        Bukkit.getLogger().info("단비해 화이팅")

        getCommand("siege-spell-item").executor = SiegeSpellItemCommand()
        getCommand("siege-spell-item").tabCompleter = SpellItemCompleter()

        getCommand("ticket-item").executor = TicketItemCommand()
        getCommand("ticket-item").tabCompleter = TicketCompleter()
    }

}