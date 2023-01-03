package com.danvhae.minecraft.siege.item.abstracts

import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.items.healthcare.HealthCareAmountItem
import com.danvhae.minecraft.siege.item.items.healthcare.HealthCarePercentItem
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import kotlin.math.max
import kotlin.math.min

abstract class HealthCareItemAbstract :SiegeItem(){
    companion object{

        val TYPE: Material = Material.GLOWSTONE_DUST
        val NAME:String = TextUtil.toColor(
            "&a&l활력&r이 담긴 &6&l별의 가루"
        )

        const val LORE_SIZE = 2

        const val COOL_TIME_SEC = 10.0;

        val lastUsed:HashMap<UUID, LocalDateTime> = HashMap()

        /**
         * 이것이 헬스케어 아이템인지 최소한의 체크를 해 보는 것
         */
        fun commonParseItem(stack:ItemStack?):Boolean{
            stack?:return false;
            if(stack.itemMeta == null)return false;
            val meta: ItemMeta = stack.itemMeta!!;

            if(meta.displayName == null)return false;
            if(meta.lore == null)return false;

            if(meta.displayName != NAME)return false;
            if(meta.lore.size != LORE_SIZE)return false;

            return true
        }
        fun parseItem(stack:ItemStack?):HealthCareItemAbstract?{
            if(!commonParseItem(stack))return null;

            var temp:HealthCareItemAbstract?;
            temp = HealthCareAmountItem.parseItem(stack);
            if(temp != null)return temp;

            temp = HealthCarePercentItem.parseItem(stack);
            if(temp != null)return temp;

            return null;
        }

        /**
         * @param amount 얼마만큼의 체력을 회복시킬것인지
         */
        fun lore(amount:String):List<String> {
            return  listOf(
                TextUtil.toColor("&6&l별의 가루&r에 &2회복의 기운&r이 담겨있다."),
                TextUtil.toColor("&2$amount&r의 체력을 &a회복&r한다.")
            )
        }

        fun giveHealth(player:Player, health:Double):Double{
            val effectiveHealth = min(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).value - player.health, health)
            player.health += effectiveHealth
            return health
        }
    }

    override fun toItemStack(): ItemStack {
        val stack = ItemStack(TYPE)
        val meta = stack.itemMeta;
        meta.displayName = NAME
        meta.lore = lore(effectAmountString())
        stack.itemMeta = meta
        return stack
    }

    protected abstract fun effectAmountString():String

    protected abstract fun applyEffect(player: Player)

    fun applyEffectWithCoolTime(player:Player):Double{
        val last = lastUsed.getOrDefault(player.uniqueId, null)
        val now = LocalDateTime.now()
        val remain =
            if(last == null) 0.0
            else max(COOL_TIME_SEC - Duration.between(last, now).seconds.toDouble(), 0.0)

        if(remain == 0.0){
            applyEffect(player)
            lastUsed[player.uniqueId] = now
        }

        return remain
    }
}