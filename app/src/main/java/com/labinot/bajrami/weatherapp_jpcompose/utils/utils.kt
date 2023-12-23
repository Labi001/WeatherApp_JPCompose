package com.labinot.bajrami.weatherapp_jpcompose.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(time: Long): String {

    val date = Date(time *1000)
    val format = SimpleDateFormat("EEE, MMM d",
       Locale.getDefault())

    return format.format(date)

}

fun formatDateTime(timestamp: Long):String {

    val sdf = SimpleDateFormat("hh:mm aa")
    val date = Date(timestamp * 1000)

    return sdf.format(date)

}

fun formatDistanceInKilometers(meters: Int): String {
    val kilometers = meters / 1000
    return String.format(kilometers.toString())
}

fun capitalizeFirstLetter(input: String): String {
    return input.substring(0, 1).uppercase(Locale.ROOT) + input.substring(1)
}

