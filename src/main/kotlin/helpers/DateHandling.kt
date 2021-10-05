package helpers

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun addDaysToDate(date: LocalDateTime, days: Long): LocalDateTime {
    // adds days to the given time and cuts off minutes, seconds and nanoseconds
    return date.plusDays(days).withMinute(0).withSecond(0).withNano(0)
}

fun countDates(date: LocalDateTime, days: LongArray): MutableList<LocalDateTime> {
    // applies addDaysToDate to the given date several times
    val scheduledDates = mutableListOf<LocalDateTime>()

    for (element in days) {
        scheduledDates.add(addDaysToDate(date, element))
    }

    return scheduledDates
}

fun epochSecondToLocalDateTime(epochSeconds: Long): LocalDateTime {
    // converts LongInt of seconds to a date
    return LocalDateTime.ofInstant(Instant.ofEpochSecond(epochSeconds), ZoneId.systemDefault())
}