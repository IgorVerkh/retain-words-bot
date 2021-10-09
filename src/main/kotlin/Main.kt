import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import commands.*
import org.litote.kmongo.KMongo
import java.net.ServerSocket
import java.util.Timer

object MyBotConfig {
    val API_TOKEN: String = System.getenv("API_TOKEN") ?: error("No api token was passed")
    val DATABASE_URI: String = System.getenv("DATABASE_URI") ?: error("No database address was passed")
    val PORT: Int = System.getenv("PORT")?.toInt() ?: 8080
}

fun main() {
    val server = ServerSocket(MyBotConfig.PORT)
    val dbClient = KMongo.createClient(MyBotConfig.DATABASE_URI)
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
