package random.weight

import kotlin.random.Random

/**
 * WeightedRandom is a class that randomly selects an item from a collection of items with weights.
 * The probability of selecting an item is proportional to its weight.
 *
 * WeightedRandom은 항목들의 컬렉션에서 가중치를 기반으로 항목을 무작위로 선택하는 클래스입니다.
 * 항목이 선택될 확률은 그 항목의 가중치에 비례합니다.
 *
 * @param items A collection of items with weights.
 * @param random A random number generator.
 */
interface WeightedRandom<T> {
    operator fun plus(item: WeightedItem<T>): Boolean

    fun add(item: WeightedItem<T>): Boolean

    fun add(value: T, weight: Double): Boolean

    operator fun minus(item: T): Boolean

    fun remove(item: T): Boolean

    fun getItems(): List<WeightedItem<T>>

    fun random(): T

    companion object {
        fun <T> default(
            items: Collection<WeightedItem<T>> = mutableListOf(),
            random: Random = Random.Default
        ): WeightedRandom<T> {
            return WeightedRandomDefault(items, random)
        }
    }
}

class WeightedRandomDefault<T>(
    items: Collection<WeightedItem<T>> = mutableListOf(),
    private val random: Random = Random.Default
) : WeightedRandom<T> {
    private val items: MutableList<WeightedItem<T>> = items.toMutableList()
    private var cumulativeWeights: List<Double> = emptyList()

    init {
        updateCumulativeWeights()
    }

    override operator fun plus(item: WeightedItem<T>): Boolean {
        minus(item.value)
        items.add(item)
        updateCumulativeWeights()
        return true
    }

    override fun add(item: WeightedItem<T>): Boolean {
        return plus(item)
    }

    override fun add(value: T, weight: Double): Boolean {
        return plus(WeightedItem(value, weight))
    }

    override operator fun minus(item: T): Boolean {
        val index = items.indexOfFirst { it.value == item }
        if (index == -1) {
            return false
        }
        items.removeAt(index)
        updateCumulativeWeights()
        return true
    }

    override fun remove(item: T): Boolean {
        return minus(item)
    }

    override fun getItems(): List<WeightedItem<T>> {
        return items.toList()
    }

    override fun random(): T {
        if (cumulativeWeights.isEmpty() || cumulativeWeights.last() == 0.0) {
            throw IllegalStateException("No items to choose from")
        }
        val randomValue = random.nextDouble(from = 0.0, until = cumulativeWeights.last())
        val index = cumulativeWeights.binarySearch { it.compareTo(randomValue) }
        val adjustedIndex = if (index >= 0) index else -index - 1
        return items[adjustedIndex].value
    }

    private fun updateCumulativeWeights() {
        cumulativeWeights = items.scan(0.0) { acc, item -> acc + item.weight }.drop(1)
    }
}

data class WeightedItem<T>(
    val value: T,
    val weight: Double
) {
    init {
        require(weight >= 0) { "Weight must be greater than or equal to 0" }
    }
}
