package dbDataClasses

import java.time.LocalTime

data class Hour (
    val hour: LocalTime,
    val events: MutableList<BotEvent>
)