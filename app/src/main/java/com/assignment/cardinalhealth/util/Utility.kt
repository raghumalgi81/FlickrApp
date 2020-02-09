package com.assignment.cardinalhealth.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Salman Zach on 5/8/19.
 * Email - zach.salmansaifi@gmail.com
 */

object Utility {

    fun getFormattedDate(date: String): String {
        return try {
            val dateOnly = date.substringBefore("T")
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = formatter.parse(dateOnly)
            val newFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
            newFormat.format(date)
        } catch (e: Exception) {
            ""
        }
    }
}
