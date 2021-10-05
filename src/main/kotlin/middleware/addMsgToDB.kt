package middleware

import com.mongodb.client.MongoDatabase
import dbDataClasses.*
import org.litote.kmongo.*
import java.time.LocalDateTime

fun addMsgToDays(userId: Long, msgId: String, date: LocalDateTime, database: MongoDatabase) {
    val col = database.getCollection<Day>(DBConfig.DAYS_COLLECTION)

    val localTime = date.toLocalTime()
    val localDate = date.toLocalDate()

    col.updateOne(
        and(Day::date eq localDate, Day::events / Hour::hour eq localTime),
        push(Day::events.posOp / Hour::events, BotEvent(userId, msgId))
    )

}

fun addMsgToMessages(messageToRetain: MessageToRetain, database: MongoDatabase): String {
    val col = database.getCollection<MessageToRetain>(DBConfig.MESSAGES_COLLECTION)
    val generatedId = messageToRetain.apply { col.insertOne(this) }._id

    // I believe it won't crush
    return generatedId!!

}

fun addMsgToUser(msgId: String, userId: Long, database: MongoDatabase) {
    val col = database.getCollection<BotUser>(DBConfig.USERS_COLLECTION)

    col.updateOne(BotUser::id eq userId, push(BotUser::messages, msgId))

}