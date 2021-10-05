import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import commands.*
import org.litote.kmongo.KMongo
import java.util.Timer


object MyBotConfig {
    const val API_TOKEN = ""
}

fun main() {
    val dbClient = KMongo.createClient()
    val database = dbClient.getDatabase(DBConfig.DB_NAME)
    val timer = Timer()
    val bot = bot {
        token = MyBotConfig.API_TOKEN

        dispatch {

            command("mem") {
                mem(bot, message, database)
            }

            command("remove_last") {
                removeLast(bot, message, database)
            }

        }

    }
    bot.startPolling()
    timer.schedule(MassMailingTask(bot, database), 0, 300000)
}
