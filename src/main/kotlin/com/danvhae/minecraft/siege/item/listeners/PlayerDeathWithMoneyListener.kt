package com.danvhae.minecraft.siege.item.listeners

import com.danvhae.minecraft.siege.core.utils.FileUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class PlayerDeathWithMoneyListener {

    /**
     * 플레이어가 죽을 때 스타를 얼마나 떨어뜨리는지 정하는 설정입니다.
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