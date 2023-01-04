package com.danvhae.minecraft.siege.item.objects

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.google.gson.Gson
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class KeepItem(type: Material, private val name:String?, private val lore:List<String>?){
    private val type:String = type.toString()
    private fun typeMaterial():Material{
        return Material.valueOf(type)
    }
    companion object{

        private val DATA = HashSet<KeepItem>()
        private const val FILE_NAME = "keepItem.json"

        operator fun contains(stack:ItemStack):Boolean{
            for(target in DATA){
                if(target.equals(stack))return true
            }
            return false
        }
        fun load(){
            val json = FileUtil.readTextFile(FILE_NAME)?:"[]"
            DATA.clear()
            val array = Gson().fromJson(json, Array<KeepItem>::class.java)
            for(e in array)
                DATA.add(e)
        }

    }

    override fun equals(other: Any?): Boolean {
        if(other !is ItemStack)return false
        if(typeMaterial() != other.type)return false

        val meta = other.itemMeta
        if(TextUtil.toColor(name?:return true) != meta.displayName)return false
        val tempList = ArrayList<String>()
        for(line in lore?:return true)
            tempList.add(TextUtil.toColor(line))
        return tempList.toList() == meta.lore.toList()
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lore.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }

}