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
    /**
     * Returns a list of items with weights.
     *
     * 가중치가 있는 항목들의 리스트를 반환합니다.
     *
     * @return A list of items with weights.
     */

    fun getItems(): List<WeightedItem<T>>

    /**
     * Returns a randomly selected item.
     *
     * 가중치를 기반으로 무작위로 선택된 항목을 반환합니다.
     *
     * @return A randomly selected item.
     * @throws IllegalStateException When there are no items to choose from.
     */
    @Throws(IllegalStateException::class)
    fun random(): T

    companion object {
        fun <T> default(
            items: Collection<WeightedItem<T>> = mutableListOf(),
            random: Random = Random.Default
        ): WeightedRandom<T> {
            return MutableWeightedRandomImpl(items, random)
        }
    }

}

interface MutableWeightedRandom<T> : WeightedRandom<T> {
    /**
     * Adds an item to the collection.
     *
     * 항목을 컬렉션에 추가합니다.
     *
     * @param item An item with a weight.
     * @return true if the item is added; otherwise, false.
     */
    operator fun plus(item: WeightedItem<T>): Boolean

    /**
     * Adds an item to the collection.
     *
     * 항목을 컬렉션에 추가합니다.
     *
     * @param item An item with a weight.
     * @return true if the item is added; otherwise, false.
     */
    fun add(item: WeightedItem<T>): Boolean

    /**
     * Adds an item to the collection.
     *
     * 항목을 컬렉션에 추가합니다.
     *
     * @param value An item.
     * @param weight A weight.
     * @return true if the item is added; otherwise, false.
     */
    fun add(value: T, weight: Double): Boolean

    /**
     * Removes an item from the collection.
     *
     * 항목을 컬렉션에서 제거합니다.
     *
     * @param item An item.
     * @return true if the item is removed; otherwise, false.
     */
    fun minus(item: T): Boolean

    /**
     * Removes an item from the collection.
     *
     * 항목을 컬렉션에서 제거합니다.
     *
     * @param item An item.
     * @return true if the item is removed; otherwise, false.
     */
    fun remove(item: T): Boolean

    companion object {
        fun <T> default(
            items: Collection<WeightedItem<T>> = mutableListOf(),
            random: Random = Random.Default
        ): MutableWeightedRandom<T> {
            return MutableWeightedRandomImpl(items, random)
        }
    }
}

class MutableWeightedRandomImpl<T>(
    items: Collection<WeightedItem<T>> = mutableListOf(),
    private val random: Random = Random.Default
) : MutableWeightedRandom<T> {
    private val items: MutableList<WeightedItem<T>> = mutableListOf()
    private var cumulativeWeights: List<Double> = emptyList()

    init {
        items.forEach { addInternal(it) }
        updateCumulativeWeights()
    }

    override operator fun plus(item: WeightedItem<T>): Boolean {
        return addInternal(item).also {
            updateCumulativeWeights()
        }
    }

    override fun add(item: WeightedItem<T>): Boolean {
        return plus(item)
    }

    override fun add(value: T, weight: Double): Boolean {
        return plus(WeightedItem(value, weight))
    }

    override operator fun minus(item: T): Boolean {
        return removeInternal(item).also {
            updateCumulativeWeights()
        }
    }

    override fun remove(item: T): Boolean {
        return minus(item)
    }

    override fun getItems(): List<WeightedItem<T>> {
        return items.toList()
    }

    override fun random(): T {
        require(items.isNotEmpty()) {
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

    private fun addInternal(item: WeightedItem<T>): Boolean {
        removeInternal(item.value)
        return items.add(item)
    }

    private fun removeInternal(item: T): Boolean {
        val index = items.indexOfFirst { it.value == item }
        if (index == -1) {
            return false
        }
        items.removeAt(index)
        return true
    }
}

/**
 * A weighted item.
 *
 * 가중치가 있는 항목입니다.
 *
 * @param value An item.
 * @param weight A weight.
 */
data class WeightedItem<T>(
    val value: T,
    val weight: Double
) {
    init {
        require(weight >= 0) { "Weight must be greater than or equal to 0" }
    }
}
