package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.DVHSiegeCore
import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.danvhae.minecraft.siege.core.utils.TextUtil
import com.danvhae.minecraft.siege.item.items.Cheque
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*
import kotlin.math.min
import kotlin.math.tanh


class PlayerDeathWithMoneyListener :Listener{

    private val config:MoneyConfig = MoneyConfig.load()

    @EventHandler
    fun onPlayerDeath(event:PlayerDeathEvent){
        val ratio = (0.5 * (1 + tanh(Random().nextGaussian())) / (Math.PI * 0.5))
            .let { r -> r * config.minRatio + (1 - r) * config.maxRatio }

        val economy = DVHSiegeCore.economy!!
        val player = event.entity
        for(stack in player.inventory){
            val cheque = Cheque.parseItem(stack?:continue)?:continue
            economy.depositPlayer(player, cheque.amount * stack.amount.toDouble())
            stack.amount = 0
        }

        val dropAmount = min((economy.getBalance(player) * ratio).toInt(), config.maxAmount)
        if(dropAmount <= 0) return

        economy.withdrawPlayer(player, dropAmount.toDouble())

        val cheque = Cheque(dropAmount, TextUtil.toColor("&f&4%s&f&l가 주머니에서 흘린 &6&l돈이다.".format(player.name)) )
        player.location.let { loc -> loc.world.dropItem(loc, cheque.toItemStack()) }

    }

    /**
     * 플레이어가 죽을 때 스타를 얼마나 떨어뜨리는지 정하는 설정입니다.
     * @property minRatio 돈을 떨어뜨리는 최소 비율
     * @property maxRatio 돈을 떨어뜨리는 최대 비율
     * @property maxAmount 떨어뜨리는 돈의 최댓값
     */
    private class MoneyConfig(var minRatio:Double = 0.05, var maxRatio:Double = 0.20, var maxAmount:Int = 300_0000){
        companion object{
            private const val FILE_NAME = "moneyConfig.json"
            fun save(config:MoneyConfig){
                FileUtil.writeTextFile(GsonBuilder().setPrettyPrinting().create().toJson(config), FILE_NAME)
            }

            fun load():MoneyConfig{
                return FileUtil.readTextFile(FILE_NAME)?.let { Gson().fromJson(it, MoneyConfig::class.java) }?: MoneyConfig()
            }
        }
    }
}