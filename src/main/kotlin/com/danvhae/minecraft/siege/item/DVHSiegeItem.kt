package com.danvhae.minecraft.siege.item

import org.bukkit.plugin.java.JavaPlugin

class DVHSiegeItem : JavaPlugin(){
    companion object{
        var instance:DVHSiegeItem? = null
            get(){return field!!}
            private set

    }

    override fun onEnable() {
        instance = this
    }

}