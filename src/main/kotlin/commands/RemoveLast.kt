package commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.mongodb.client.MongoDatabase
import middleware.deleteMsgFromMessages
import middleware.deleteLastMsgFromUser

fun removeLast(bot: Bot, message: Message, database: MongoDatabase) {
    val userId = message.chat.id

    val msgId = deleteLastMsgFromUser(userId, database)

    if (msgId != null){
        deleteMsgFromMessages(msgId, database)
        bot.sendMessage(chatId = ChatId.fromId(userId), text = "Previous message was removed")
    } else {
        bot.sendMessage(chatId = ChatId.fromId(userId), text = "Sorry, nothing to remove")
    }

}