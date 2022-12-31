package com.danvhae.minecraft.siege.item

import com.danvhae.minecraft.siege.item.commands.*
import com.danvhae.minecraft.siege.item.completers.MoneyCompleter
import com.danvhae.minecraft.siege.item.completers.SpellItemCompleter
import com.danvhae.minecraft.siege.item.completers.TicketCompleter
import com.danvhae.minecraft.siege.item.listeners.*
import com.danvhae.minecraft.siege.item.objects.KeepItem
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

        val pm = Bukkit.getPluginManager()
        pm.registerEvents(IllegalCraftingListener(), this)
        pm.registerEvents(IllegalTradingListener(), this)
        pm.registerEvents(TicketItemListener(), this)
        pm.registerEvents(SiegeMagicSpellItemListener(), this)
        pm.registerEvents(AbortWildListener(), this)
        pm.registerEvents(PlayerDeathWithKeepItemListener(), this)
        pm.registerEvents(ChequeListener(), this)
        pm.registerEvents(ExpCoinUseListener(), this)

        getCommand("siege-spell-item").executor = SiegeSpellItemCommand()
        getCommand("siege-spell-item").tabCompleter = SpellItemCompleter()

        getCommand("ticket-item").executor = TicketItemCommand()
        getCommand("ticket-item").tabCompleter = TicketCompleter()

        getCommand("exp-coin").executor = ExpCoinItemCommand()

        getCommand("스타").executor = MoneyCommand()
        getCommand("스타").tabCompleter = MoneyCompleter()
        getCommand("siege-keep-item").executor = KeepItemDataCommand()

        try {
            KeepItem.load()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}