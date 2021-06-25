package com.github.hisuie08.tutorialbot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent
/*ここから下の3つのimportが今回初めて使うコマンド用ライブラリ*/
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.command.CommandEvent

class Neko:Command(){/*Commandクラスを継承してコマンドを定義*/
init {
    this.name = "neko" /*コマンド文字列の定義はinitブロックの中に書く必要があります。*/
}
    override fun execute(event: CommandEvent?){
/*executeメソッドはコマンドを叩かれたイベントをキャッチして対応する処理を実行する中核部分です*/
        event?.reply("にゃーん")
/*
ここで使われているreplyメソッドは
event?.message?.channel?.sendMessageFormat("")?.queue()
の簡易呼び出しです。Discordの「返信」とは異なりますのでご注意ください。
*/
    }
}

class Capitalize:Command(){
    init {
        this.name = "capitalize"
    }
    
    override fun execute(event : CommandEvent?) {
        if(event?.args?.isEmpty()==false){
            /*argsはコマンドに引数が与えられたとき自動的にここに文字列として格納されます。
            空でなかったとき、つまりコマンドに引数がついて呼び出されたときの分岐を記述することになります。
            複数の引数を与えたいときはargsに格納された文字列をスペース毎に区切る等の処理が必要になるでしょう。*/
            event.reply(event.args.capitalize())
        }
    }
}

class BotClient{
    lateinit var jda: JDA
    private val commandPrefix = "/" /*コマンドプレフィックスの指定
                                     空文字列にするとBotへのメンションがプレフィックスとして機能します。
                                     このほうがいいかも。*/
    
    fun main(token: String) {
        val commandClient = CommandClientBuilder()
            .setPrefix(commandPrefix)
            .setOwnerId("") /*本来であれば開発者のIDを入れますが、空文字列でもOKです。*/
            .addCommand(Neko())
            .addCommand(Capitalize())
            /*複数コマンドを登録するときには
              .addCommands(Hoge(),Fuga(),Piyo())
              となります。
            */
            .build()
        
        jda = JDABuilder.createLight(token,
            GatewayIntent.GUILD_MESSAGES)
            .addEventListeners(commandClient)
            .build()
    }
}


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