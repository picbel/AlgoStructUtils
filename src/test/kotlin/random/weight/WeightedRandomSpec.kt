package random.weight

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import kotlin.test.BeforeTest
import kotlin.test.Test

/**
 * @since 2024/05/21
 */
internal class WeightedRandomSpec {

    @DisplayName("A의 비중을 10, B와 C의 비중을 각각 0으로 설정하고 random 함수를 호출하면 결과는 A만 나옵니다.")
    @Test
    fun callRandomWithWeightsA10B0C0ReturnsAOnly() {
        // given:
        val sut = MutableWeightedRandom.default<String>()
        sut.add(WeightedItem("A", 10.0))
        sut.add(WeightedItem("B", 0.0))
        sut.add(WeightedItem("C", 0.0))
        // when: // then: When weights are set to A: 10, B: 0, and C: 0, calling the random function returns only A.
        repeat(100) {
           sut.random() shouldBe "A"
        }
    }

    @DisplayName("A와 B의 비중을 각각 10, C의 비중을 0으로 설정한 후 B를 제거하고 random 함수를 호출하면 결과는 A만 나옵니다.")
    @Test
    fun removeBThenCallRandomWithWeightsA10B10C0ReturnsAOnly() {
        // given:
        val sut = MutableWeightedRandom.default<String>(
            mutableListOf(
                WeightedItem("A", 10.0),
                WeightedItem("B", 10.0),
                WeightedItem("C", 0.0)
            )
        )
        sut.add(WeightedItem("A", 10.0))
        sut.add(WeightedItem("B", 0.0))
        sut.add(WeightedItem("C", 0.0))
        sut.remove("B")
        // when: // then: when weights are set to A: 10, B: 10, and C: 0, and B is removed, calling the random function returns only A
        repeat(100) {
            sut.random() shouldBe "A"
        }
    }

    @DisplayName("선택할 항목이 없다면 random 함수가 예외를 발생시킵니다.")
    @Test
    fun callRandomThrowsExceptionWhenAllWeightsAreZeroOrDeleted() {
        // given:
        val sut = MutableWeightedRandom.default<String>(
            mutableListOf(
                WeightedItem("A", 10.0),
                WeightedItem("B", 0.0),
            )
        )
        sut.add(WeightedItem("C", 10.0))
        sut.remove("A")
        sut.remove("C")
        // when: // then: When all items are removed or their weights are set to 0, the random function throws an exception.
        shouldThrow<IllegalStateException> {
            sut.random()
        }
    }

    @DisplayName("모든 항목을 삭제하거나 비중이 0이 되면 random 함수가 예외를 발생시킵니다.")
    @Test
    fun callRandomThrowsExceptionWhenAllWeightsAreDeleted() {
        // given:
        val sut = MutableWeightedRandom.default<String>()
        // when: // then: When items is empty, the random function throws an exception.
        shouldThrow<IllegalStateException> {
            sut.random()
        }
    }

    @DisplayName("가중치 항목이 비어있다면 random 함수가 예외를 발생시킵니다.")
    @Test
    fun setItemsEmptyThenCallRandomReturnsAOnly() {
        // given:
        val sut = MutableWeightedRandom.default<String>(
            mutableListOf(
                WeightedItem("A", 10.0),
                WeightedItem("B", 10.0),
            )
        )
        sut.add(WeightedItem("B", 0.0))
        // when: // then: When the weight of B is set to 0 and the random function is called, it returns only A.
        repeat(100) {
            sut.random() shouldBe "A"
        }
    }

}