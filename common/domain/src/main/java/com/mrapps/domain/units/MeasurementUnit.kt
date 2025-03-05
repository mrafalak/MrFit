package com.mrapps.domain.units


enum class MeasurementUnit(val id: Int, val unit: String) {

    // Weight
    KILOGRAM(1, "kg"),
    POUND(2, "lb"),

    // Distance
    CENTIMETER(12, "cm"),
    METER(10, "m"),
    KILOMETER(11, "km"),
    INCH(14, "in"),
    FOOT(13, "ft"),
    MILE(15, "mi"),

    // Time
    MILLISECOND(20, "ms"),
    SECOND(21, "s"),
    MINUTE(22, "min"),
    HOUR(23, "h"),

    // Speed
    SPEED_KMH(30, "km/h"),
    SPEED_MPH(31, "mi/h"),

    // Countable
    REPETITION(40, "reps"),
    SET(41, "sets"),
    STEP(42, "steps"),

    // Percentage
    PERCENT(100, "%")
}
