package com.github.hisuie08

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

class BotClient : ListenerAdapter(){
    lateinit var jda: JDA
    
    fun main(token: String) {
        jda = JDABuilder.createLight(token,
            GatewayIntent.GUILD_MESSAGES)
            .addEventListeners(this)
            .build()
    }
    
    override fun onReady(event: ReadyEvent) {
        println("起動しました")
    }
    
    override fun onGuildMessageReceived(event : GuildMessageReceivedEvent) {
        if(event.message.contentDisplay == "/neko"){
            event.channel.sendMessageFormat("にゃーん").queue()
        }
    }
}

fun main() {
    val bot = BotClient()
    bot.main("トークン文字列")
}