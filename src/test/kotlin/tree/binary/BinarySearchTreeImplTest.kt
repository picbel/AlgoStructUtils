package tree.binary

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class BinarySearchTreeImplTest {

    @Test
    fun putTest() {
        // Given : // When :
        val sut = MutableBinaryTreeImpl(MutableBinaryTreeNodeImpl(18))
        sut.put(MutableBinaryTreeNodeImpl(15))
        sut.put(MutableBinaryTreeNodeImpl(10))
        sut.put(MutableBinaryTreeNodeImpl(6))
        sut.put(MutableBinaryTreeNodeImpl(3))
        sut.put(MutableBinaryTreeNodeImpl(8))
        sut.put(MutableBinaryTreeNodeImpl(12))
        sut.put(MutableBinaryTreeNodeImpl(11))
        sut.put(MutableBinaryTreeNodeImpl(31))
        sut.put(MutableBinaryTreeNodeImpl(27))
        sut.put(MutableBinaryTreeNodeImpl(24))
        sut.put(MutableBinaryTreeNodeImpl(20))
        sut.put(MutableBinaryTreeNodeImpl(33))
        sut.put(MutableBinaryTreeNodeImpl(35))
        sut.put(MutableBinaryTreeNodeImpl(37))
        // Then :
        sut.root.inorderTraversal() shouldBe listOf(3, 6, 8, 10, 11, 12, 15, 18, 20, 24, 27, 31, 33, 35, 37)
    }

    @Test
    fun findTest() {
        // Given :
        val sut = MutableBinaryTreeImpl(MutableBinaryTreeNodeImpl(18))
        sut.put(MutableBinaryTreeNodeImpl(15))
        sut.put(MutableBinaryTreeNodeImpl(10))
        sut.put(MutableBinaryTreeNodeImpl(6))
        sut.put(MutableBinaryTreeNodeImpl(3))
        sut.put(MutableBinaryTreeNodeImpl(8))
        sut.put(MutableBinaryTreeNodeImpl(12))
        sut.put(MutableBinaryTreeNodeImpl(11))
        // When :
        val result = sut.find(12)
        val result2 = sut.find(33)
        // Then :
        assertSoftly {
            result?.key shouldBe 12
            result2 shouldBe null
        }
    }
}