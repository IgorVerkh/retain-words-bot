package dbDataClasses

import java.time.LocalDate

data class Day(
    val date: LocalDate,
    val events: List<Hour>
)
