package middleware

import com.mongodb.client.MongoDatabase
import dbDataClasses.BotUser
import DBConfig
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection


fun addUserToDB(botUser: BotUser, database: MongoDatabase) {
    val col = database.getCollection<BotUser>(DBConfig.USERS_COLLECTION)

    val user: BotUser? = col.findOne(BotUser::id eq botUser.id)
    if (user == null) {
        col.insertOne(BotUser(botUser.id, mutableListOf()))
    }
}