package middleware

import com.mongodb.client.MongoDatabase
import dbDataClasses.BotEvent
import dbDataClasses.Day
import dbDataClasses.MessageToRetain
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.time.LocalDateTime

fun getMsgFromMessages(msgId: String, database: MongoDatabase): MessageToRetain? {
    val col = database.getCollection<MessageToRetain>(DBConfig.MESSAGES_COLLECTION)

    return col.findOne(MessageToRetain::_id eq msgId)
}

fun getTopicalEvents(database: MongoDatabase): List<BotEvent>? {
    val col = database.getCollection<Day>(DBConfig.DAYS_COLLECTION)
    val dateNow = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0)

    val day = col.findOne(Day::date eq dateNow.toLocalDate())
    if (day != null) {
        return day.events[dateNow.toLocalTime().hour].events.toList()
    }
    return null
}