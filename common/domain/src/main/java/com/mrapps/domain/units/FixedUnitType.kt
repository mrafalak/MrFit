package com.mrapps.domain.units

sealed interface UnitType

sealed class FixedUnitType(val value: Measurement) : UnitType {

    data class Time(val duration: Double, val unit: MeasurementUnit) : FixedUnitType(
        value = Measurement(duration, unit)
    )

    data class Repetition(val count: Int) : FixedUnitType(
        value = Measurement(count.toDouble(), MeasurementUnit.REPETITION)
    )

    data class Set(val count: Int) : FixedUnitType(
        value = Measurement(count.toDouble(), MeasurementUnit.SET)
    )
}

sealed class SystemUnitType(
    val metric: Measurement,
    val imperial: Measurement
) : UnitType {

    data class Weight(val kg: Double, val lb: Double) : SystemUnitType(
        metric = Measurement(kg, MeasurementUnit.KILOGRAM),
        imperial = Measurement(lb, MeasurementUnit.POUND),
    )

    data class Distance(val meter: Double, val foot: Double) : SystemUnitType(
        metric = Measurement(meter, MeasurementUnit.METER),
        imperial = Measurement(foot, MeasurementUnit.FOOT),
    )

    data class Speed(val speedKMH: Double, val speedMPH: Double) : SystemUnitType(
        metric = Measurement(speedKMH, MeasurementUnit.SPEED_KMH),
        imperial = Measurement(speedMPH, MeasurementUnit.SPEED_MPH),
    )

    fun getMeasurement(useMetric: Boolean): Measurement {
        return if (useMetric) metric else imperial
    }
}