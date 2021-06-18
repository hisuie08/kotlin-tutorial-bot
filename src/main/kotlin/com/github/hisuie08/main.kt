package com.github.hisuie08

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

class BotClient : ListenerAdapter(){
    lateinit var jda: JDA
    
    fun main(token: String) { //トークンを使ってBotを起動する部分
        jda = JDABuilder.createLight(token,
            GatewayIntent.GUILD_MESSAGES)
            .addEventListeners(this)
            .build()
    }
    
    override fun onReady(event: ReadyEvent) { //Botがログインしたときの処理
        println("起動しました")
    }
    
    override fun onGuildMessageReceived(event : GuildMessageReceivedEvent) {
        //Botがメッセージを受信したときの処理
        if(event.message.contentDisplay == "/neko"){//メッセージ内容を確認
            event.channel.sendMessageFormat("にゃーん").queue()//メッセージ送信
        }
    }
}

/*Kotlinではコード中から単体main関数を探して最初に実行します。
そこでmain関数にBotClientクラスのインスタンス作成とトークンを渡した起動処理mainメソッドを実行させる形でBotを起動します。
*/
fun main() {
    val bot = BotClient()
    bot.main("トークン文字列")
}

/*
jarファイルを作成するときはmain関数を以下のように置き換える
fun main(args:Array<String>){
    val bot = BotClient()
    bot.main(args[0])
}
 */