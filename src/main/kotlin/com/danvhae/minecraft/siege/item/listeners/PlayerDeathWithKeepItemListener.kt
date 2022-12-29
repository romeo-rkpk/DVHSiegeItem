package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.TicketAbstract
import com.danvhae.minecraft.siege.item.items.tickets.WorkTicket
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
        private var savedItem = HashMap<UUID, ArrayList<ItemStack>>()

        private const val FILE_NAME = "savedItem-kotlin.dvh";
        fun loadItemData(){
            val bytes = FileUtil.readBytes(FILE_NAME, true)?: byteArrayOf()
            savedItem = FileUtil.fromBytes(bytes) as? HashMap<UUID, ArrayList<ItemStack>> ?: HashMap()
        }

        fun saveItemData(){
            val bytes = FileUtil.toBytes(savedItem)?: byteArrayOf()
            FileUtil.writeBytes(bytes, FILE_NAME)
        }

        private fun isKeepItem(stack: ItemStack):Boolean{
            /*
            return when(stack.type){
                GOLD_SWORD, CHAINMAIL_BOOTS, CHAINMAIL_CHESTPLATE, CHAINMAIL_HELMET, CHAINMAIL_LEGGINGS -> true
                BOW -> {
                    if(stack.itemMeta!!.displayName != TextUtil.toColor("&3&l알타이르&f의&b&l천궁")) false
                    else if(stack.itemMeta.lore!!.size != 3) false
                    else if(stack.itemMeta.lore[0] != TextUtil.toColor("&7-----------------------------------------------------")) false
                    else if(stack.itemMeta.lore[1] != TextUtil.toColor("&b&l천공&f을 &4&l제패&f할 준비는 되었는가.")) false
                    else stack.itemMeta.lore[2] == TextUtil.toColor("&7-----------------------------------------------------")
                }
                else -> false
            }
             */

            if(stack.type in listOf(
                    Material.GOLD_SWORD, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE,
                    Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS
                )) return true

            //알타이르 성주 무기 보존
            if(stack.type == Material.BOW){
                if(stack.itemMeta!!.displayName != TextUtil.toColor("&3&l알타이르&f의&b&l천궁")) return false
                else if(stack.itemMeta.lore!!.size != 3) return false
                else if(stack.itemMeta.lore[0] != TextUtil.toColor("&7-----------------------------------------------------"))
                    return false
                else if(stack.itemMeta.lore[1] != TextUtil.toColor("&b&l천공&f을 &4&l제패&f할 준비는 되었는가."))
                    return false
                return  stack.itemMeta.lore[2] == TextUtil.toColor("&7-----------------------------------------------------")
            }

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