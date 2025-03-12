package com.mrapps.domain.units

sealed interface UnitType

sealed class FixedUnitType(val value: Measurement) : UnitType {

    data class Time(val duration: Double, val unit: MeasurementUnit) : FixedUnitType(
        value = Measurement(duration, unit)
    )

    data class Repetition(val count: Int) : FixedUnitType(
        value = Measurement(count.toDouble(), MeasurementUnit.Countable.Repetition)
    )

    data class Set(val count: Int) : FixedUnitType(
        value = Measurement(count.toDouble(), MeasurementUnit.Countable.Set)
    )
}

sealed class SystemUnitType(
    val metric: Measurement,
    val imperial: Measurement
) : UnitType {

    data class Weight(val kg: Double, val lb: Double) : SystemUnitType(
        metric = Measurement(kg, MeasurementUnit.Weight.Kilogram),
        imperial = Measurement(lb, MeasurementUnit.Weight.Pound),
    )

    data class Distance(val meter: Double, val foot: Double) : SystemUnitType(
        metric = Measurement(meter, MeasurementUnit.Distance.Meter),
        imperial = Measurement(foot, MeasurementUnit.Distance.Foot),
    )

    data class Speed(val speedKMH: Double, val speedMPH: Double) : SystemUnitType(
        metric = Measurement(speedKMH, MeasurementUnit.Speed.KmPerHour),
        imperial = Measurement(speedMPH, MeasurementUnit.Speed.MiPerHour),
    )

    fun getMeasurement(useMetric: Boolean): Measurement {
        return if (useMetric) metric else imperial
    }
}