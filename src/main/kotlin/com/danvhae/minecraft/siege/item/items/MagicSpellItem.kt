package com.danvhae.minecraft.siege.item.items

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.abstracts.SiegeItem
import com.danvhae.minecraft.siege.item.utils.ItemUtil
import com.nisovin.magicspells.MagicSpells
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID
import kotlin.math.max

internal class MagicSpellItem(val name:String, val lore:List<String>, val skillID:String, val coolTime:Int,
                              val type:Material) : SiegeItem() {

    companion object{
        private val COOL_DOWN = HashMap<Pair<String, UUID>, LocalDateTime>()

        val WINGS2 = MagicSpellItem(TextUtil.toColor("&b비상"),
            listOf(TextUtil.toColor(" &7--------------------------------------"),
                "",
                TextUtil.toColor("&b 사용 시 빠른 속도로 위로 상승한다."),
                "",
                TextUtil.toColor("&7---------------------------------------"),
                TextUtil.toColor("&f[일회용] ")
            ), "wings2_multi", 30, Material.FIREWORK_CHARGE
        )

        val BACKSTEP = MagicSpellItem(TextUtil.toColor("&8전략적 후퇴"), listOf(
            TextUtil.toColor("&7-------------------------------------------"),
            "",
            TextUtil.toColor("&b 사용 시 연막을 터뜨리고 뒤로 멀리 도약합니다."),
            "",
            TextUtil.toColor("&7-------------------------------------------"),
            TextUtil.toColor("&f[일회용] ")
        ), "backstep_multi", 15, Material.FIREWORK_CHARGE)

        val NEVERDUMMY = MagicSpellItem(TextUtil.toColor("&a부활석"), listOf(
            TextUtil.toColor("&7--------------------------------------"),
            "",
            TextUtil.toColor("&b 우클릭 시 부활석이 몸에 깃듭니다."),
            TextUtil.toColor("&b 부활석이 죽음을 감지하면 몸을 회복시킵니다."),
            "",
            TextUtil.toColor("&7---------------------------------------"),
            TextUtil.toColor("&f[일회용]")
        ), "neverDummy", 60, Material.SPECKLED_MELON)

        val items = mapOf(
            Pair(WINGS2.skillID, WINGS2),
            Pair(BACKSTEP.skillID, BACKSTEP),
            Pair(NEVERDUMMY.skillID, NEVERDUMMY)
        )

        fun parseItem(stack:ItemStack) : MagicSpellItem?{
            for(item in listOf(WINGS2, BACKSTEP, NEVERDUMMY))
                item.let { if(stack.isSimilar(it.toItemStack())) return it }
            return null
        }

        fun forceCooling(){
            COOL_DOWN.clear()
        }
    }

    fun useItem(player: Player): Long?{
        val item = ItemUtil.targetItem(player, toItemStack())
        if(item == null){
            player.sendMessage("정말 아이템을 가지고 있는 것이 맞습니까?")
            return null
        }
        val key = Pair(skillID, player.uniqueId)
        val last = COOL_DOWN[key]
        val now = LocalDateTime.now()
        val remain = coolTime - Duration.between(last?:now.minusDays(1L), now).seconds
        if(remain <= 0){
            val spell = MagicSpells.getSpellByInternalName(skillID)
            spell.cast(player)
            //player.performCommand("c $skillID")
            COOL_DOWN[key] = now
            item.amount--
        }

        return max(remain, 0)
    }
    override fun toItemStack(): ItemStack {
        val result = ItemStack(type)
        val meta = result.itemMeta
        meta.displayName = name; meta.lore = lore
        result.itemMeta = meta
        return result
    }
}