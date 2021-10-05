package commands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.mongodb.client.MongoDatabase
import dbDataClasses.BotUser
import dbDataClasses.MessageToRetain
import helpers.countDates
import helpers.createAnswerMsg
import helpers.epochSecondToLocalDateTime
import helpers.removeBotMention
import middleware.*


fun mem(bot: Bot, message: Message, database: MongoDatabase) {
    // Command to insert data into the database
    val cleanMsg = removeBotMention(message.text!!)
    val userId = message.chat.id

    if (cleanMsg.isEmpty()) {
        bot.sendMessage(chatId = ChatId.fromId(userId), text = "Sorry, nothing to save")
        return
    }

    val msgDate = epochSecondToLocalDateTime(message.date)
    val remindDates = countDates(msgDate, longArrayOf(1, 3, 10, 30))

    // add days
    remindDates.forEach { addDayToDB(it.toLocalDate(), database) }
    // add user
    addUserToDB(BotUser(userId, mutableListOf()), database)
    // add message, get its id
    val msgDBId = addMsgToMessages(MessageToRetain(cleanMsg, remindDates), database)
    // add message to the user
    addMsgToUser(msgDBId, userId, database)
    // schedule message
    for (element in remindDates) {
        addMsgToDays(userId, msgDBId, element, database)
    }

    bot.sendMessage(chatId = ChatId.fromId(userId), text = createAnswerMsg(cleanMsg))

//    println("---\n" +
//            "Date: ${message.date}; " +
//            "Chat ID: ${userId}\n" +
//            "Message: ${message.text?.let { removeBotMention(it) }}" +
//            "\n---")
}
