package pl.karol202.axon.util

import java.util.*

internal typealias FloatRange = ClosedFloatingPointRange<Float>

fun FloatRange.randomNonZero(): Float = (start + Random().nextFloat() * (endInclusive - start)).let { value ->
	if(value != 0f) value else randomNonZero()
}

operator fun FloatArray.minus(other: FloatArray): FloatArray
{
	if(size != other.size) throw IllegalArgumentException("Invalid array size: $size and ${other.size}")
	return mapIndexed { i, value -> value - other[i] }.toFloatArray()
}