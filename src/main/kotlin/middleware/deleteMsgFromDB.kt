package middleware

import com.mongodb.client.MongoDatabase
import dbDataClasses.BotUser
import dbDataClasses.MessageToRetain
import org.litote.kmongo.*

fun deleteMsgFromMessages(msgId: String, database: MongoDatabase) {
    val col = database.getCollection<MessageToRetain>(DBConfig.MESSAGES_COLLECTION)

    col.deleteOne(MessageToRetain::_id eq msgId)
}

fun deleteLastMsgFromUser(userId: Long, database: MongoDatabase): String? {
    val col = database.getCollection<BotUser>(DBConfig.USERS_COLLECTION)

    val user: BotUser? = col.findOne(BotUser::id eq userId)

    if (user != null) {
        if (user.messages.size == 0) {
            return null
        }
        val msgId = user.messages[user.messages.size-1]
        col.updateOne(BotUser::id eq userId, pull(BotUser::messages, msgId))
        return msgId
    } else {
        return null
    }

}