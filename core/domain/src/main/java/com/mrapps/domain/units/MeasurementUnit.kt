package com.mrapps.domain.units


sealed class MeasurementUnit(val id: Int, val unit: String) {

    sealed class Weight(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object Kilogram : Weight(1, "kg")
        data object Pound : Weight(2, "lb")
    }

    sealed class Distance(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object Centimeter : Distance(12, "cm")
        data object Meter : Distance(10, "m")
        data object Kilometer : Distance(11, "km")
        data object Inch : Distance(14, "in")
        data object Foot : Distance(13, "ft")
        data object Mile : Distance(15, "mi")
    }

    sealed class Time(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object Millisecond : Time(20, "ms")
        data object Second : Time(21, "s")
        data object Minute : Time(22, "min")
        data object Hour : Time(23, "h")

        companion object {
            val entries = listOf(Millisecond, Second, Minute, Hour)

            fun fromId(id: Int?): Time? {
                return entries.find { it.id == id }
            }
        }
    }

    sealed class Speed(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object KmPerHour : Speed(30, "km/h")
        data object MiPerHour : Speed(31, "mi/h")
    }

    sealed class Countable(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object Repetition : Countable(40, "reps")
        data object Set : Countable(41, "sets")
        data object Step : Countable(42, "steps")
    }

    sealed class Percentage(id: Int, unit: String) : MeasurementUnit(id, unit) {
        data object Percent : Percentage(100, "%")
    }
}