package com.efedaniel.bloodfinder.extensions

import com.efedaniel.bloodfinder.utils.Misc
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.getTime(): String {
    val today = Calendar.getInstance()
    val past = Calendar.getInstance()
    past.timeInMillis = this.toLong()

    return if (past.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
        when {
            past.get(Calendar.MONTH) != today.get(Calendar.MONTH) -> formatDate(past.time, Misc.DAY_MONTH_AND_TIME)
            today.get(Calendar.DAY_OF_MONTH) - past.get(Calendar.DAY_OF_MONTH) > 1 -> formatDate(past.time, Misc.DAY_MONTH_AND_TIME)
            today.get(Calendar.DAY_OF_MONTH) == past.get(Calendar.DAY_OF_MONTH) -> String.format("Today at %s", formatDate(past.time, Misc.TIME))
            else -> String.format("Yesterday at %s", formatDate(past.time, Misc.TIME))
        }
    } else {
        formatDate(past.time, Misc.DAY_MONTH_YEAR_AND_TIME)
    }
}

fun formatDate(date: Date, format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(date)

fun Float.convertToDistanceInKm(): String {
    val distanceInKm = this * 0.001
    val df = DecimalFormat("#.###")
    df.roundingMode = RoundingMode.CEILING
    return String.format("%skm", df.format(distanceInKm))
}

fun String.getCharAt(index: Int): String {
    val array = this.toCharArray()
    return if (array.size > index) array[index].toString() else ""
}
