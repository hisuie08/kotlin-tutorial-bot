package com.github.hisuie08

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.requests.GatewayIntent

//前回も使用したコマンドライブラリ
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.command.CommandEvent

//Embedを使うのに必要なimport
import net.dv8tion.jda.api.EmbedBuilder
import java.io.File


class ShowEmbed:Command(){ //Embedを返すコマンド。
    init {
        this.name ="embed"
    }
    override fun execute(event : CommandEvent?) {
        
        //今回の肝。Embedの構築処理
        val embed = EmbedBuilder()//EmbedBuilderでインスタンスを作成して、後から中身をセットします。
            //タイトル文字列。第2引数にURLを入れるとタイトルを指定URLへのリンクにできます
            .setTitle("Tutorial Embed","https://example.com")
            
            //Botの情報。タイトルと同じくリンクを指定できる他、第3引数にアイコン画像を指定できます。
            //今回は自分のアバターアイコンを指定しました。
            .setAuthor("tutorial bot","https://repo.exapmle.com/bot",event?.selfUser?.avatarUrl)
            
            .appendDescription("Embed made with Kotlin JDA!!") //Embedの説明文
            .setColor(0x00ff00) //Embed左端の色を設定します。今回は緑。
            .addField("フィールド1","値1",false) //以下3つフィールドをセット
            .addField("フィールド2","値2",true)
            .addField("フィールド3","値3",true)
            .setThumbnail("https://image.example.com/thumbnail.png") //サムネイル(小さい画像)
            .setImage("https://image.example.com/main.png") //イメージ(大きい画像)
            
            //フッターには開発者情報を入れるといいでしょう。
            .setFooter("made by NashiroAoi","https://dev.exapmple.com/profile.png")
            .build() //buildは一番最後の組み立て処理です。書き忘れないようにしましょう。
        
        event?.reply(embed) //送信はreplyにembedを渡してやるだけです。
    }
}

/*ローカルの画像をEmbedに埋め込むコードです。*/
class ShowEmbedLocal:Command(){
    init {
        this.name ="embedlocal"
    }
    
    override fun execute(event : CommandEvent?) {
        val embed = EmbedBuilder().setTitle("Tutorial Embed with Local Image") //まずはEmbedを定義
        val file = File("ローカル画像ファイルのパス") //例:C:\Users\user\Pictures\image.jpg
        /**
        Discordでローカルの画像をアップロードするときは attachment://~ というURLを使います。
        ://以下は自由に決められるので、先程読み込んだ画像ファイルのファイル名を使いましょう。
         **/
        embed.setImage("attachment://${file.name}")
        
        /*Embed+画像 のような複雑なメッセージになるとreplyが使えません。
          ファイル送信に使うSendFileアクションは、embedを受け付けて一緒に送信することができます。
          一緒に送信されたembedが画像ファイルを求めた場合はファイルをembedにわたすこともできます。
          そこで、sendFileでの画像送信処理にembedをくっつけて送信しましょう。
        */
        event?.channel?.sendFile(file,file.name)?.embed(
            embed.build() //組み立てたembedを渡す。
        )?.queue() //送信処理
    }
}

/**ここから下は前回までのと基本的に同じです。
addCommand()でShowEmbedを登録するのを忘れないようにしましょう。
 **/
class BotClient{
    lateinit var jda: JDA
    private val commandPrefix = "/"
    
    fun main(token: String) {
        val commandClient = CommandClientBuilder()
            .setPrefix(commandPrefix)
            .setOwnerId("")
            .addCommand(ShowEmbed())
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