import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.mongodb.client.MongoDatabase
import middleware.getMsgFromMessages
import middleware.getTopicalEvents
import java.time.LocalDateTime
import java.util.*

class MassMailingTask(private val bot: Bot, val database: MongoDatabase): TimerTask() {
    var dateTime = countDateNow()

    override fun run() {
        val dateNow = countDateNow()

        if ( dateTime.isEqual(dateNow).not() ) {
            dateTime = dateNow

            val events = getTopicalEvents(database) ?: return
            for (element in events) {
                val message = getMsgFromMessages(element.msgId, database) ?: continue
                bot.sendMessage(chatId = ChatId.fromId(element.userId), text = message.msgText)
            }
        }

    }
}

fun countDateNow(): LocalDateTime {
    return LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)
}