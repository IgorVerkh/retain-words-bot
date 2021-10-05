package middleware

import com.mongodb.client.MongoDatabase
import dbDataClasses.Day
import dbDataClasses.Hour
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.time.LocalDate
import java.time.LocalTime

fun addDayToDB(date: LocalDate,  database: MongoDatabase) {
    val col = database.getCollection<Day>(DBConfig.DAYS_COLLECTION)

    val dbDay: Day? = col.findOne(Day::date eq date)
    if (dbDay == null) {
        val hoursList = mutableListOf<Hour>()
        for (i in 0..23) {
            hoursList.add(Hour(LocalTime.of(i, 0), mutableListOf()))
        }
        col.insertOne(Day(date, hoursList))
    }
}