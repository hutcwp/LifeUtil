package com.hutcwp.srw.music

import me.hutcwp.BaseConfig

/**
 *  author : kevin
 *  date : 2022/4/9 2:10 PM
 *  description :
 */
object GameMusicManager {

    //主界面的游戏音乐
    private const val MAIN_GAME_MUSIC = "audio/music2/87.mp3"


    fun playMainBGM() {
        BackgroundMusic.getInstance(BaseConfig.getApplicationContext())
                .playBackgroundMusic(MAIN_GAME_MUSIC, true)
    }

}