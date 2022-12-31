package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import com.danvhae.minecraft.siege.item.items.tickets.WorkTicket
import com.danvhae.minecraft.siege.item.objects.KeepItem
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import java.util.*

class PlayerDeathWithKeepItemListener : Listener {
    companion object{
        //private var savedItem = HashM
        init {
            loadItemData()
        }
        private var savedItem = HashMap<UUID, ArrayList<ItemStack>>()

        private const val FILE_NAME = "savedItem-kotlin.dvh";
        private fun loadItemData(){
            val bytes = FileUtil.readBytes(FILE_NAME, true)?: byteArrayOf()
            savedItem = FileUtil.fromBytes(bytes) as? HashMap<UUID, ArrayList<ItemStack>> ?: HashMap()
        }

        fun saveItemData(){
            val bytes = FileUtil.toBytes(savedItem)?: byteArrayOf()
            FileUtil.writeBytes(bytes, FILE_NAME)
        }

        private fun isKeepItem(stack: ItemStack):Boolean{
            if(stack in KeepItem) return true
            if(TicketAbstract.parseTicket(stack) is WorkTicket)return true
            return false
        }
    }
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent){
        //val items = ArrayList(event.drops.f)
        val items = ArrayList<ItemStack>()
        //event.drops.forEach()
        event.drops.forEach{item -> if(isKeepItem(item))items.add(item)}
        event.drops.removeIf { item -> isKeepItem(item) }
        savedItem[event.entity.uniqueId] = items
        //save
        saveItemData()
    }
    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent){
        // savedItem[event.player.uniqueId] ?: return
        val items = savedItem[event.player.uniqueId] ?: return
        items.forEach{item -> event.player.inventory.addItem(item)}
        savedItem.remove(event.player.uniqueId)
        saveItemData()

    }
}