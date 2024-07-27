package tree.binary

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BinarySearchTreeImplTest {

    private lateinit var sut: MutableBinarySearchTree

    /**
     * Sets up the initial state of the Mutable Binary Search Tree.
     *
     * The structure of the Binary Search Tree after setup is as follows:
     *
     * ```
     *                    18
     *                   /  \
     *                 15    31
     *                /     /  \
     *              10     27   33
     *             / \     /      \
     *            6  12   24      35
     *           / \  /   /        \
     *          3  8 11  20        37
     *              \
     *              7
     * ```
     * The root of the tree is 18, and each `put` method call inserts a node in the appropriate position
     * to maintain the properties of the Binary Search Tree.
     */
    @BeforeEach
    fun setup() {
        sut = MutableBinarySearchTreeImpl(MutableBinaryTreeNodeImpl(18))
        sut.put(MutableBinaryTreeNodeImpl(15))
        sut.put(MutableBinaryTreeNodeImpl(10))
        sut.put(MutableBinaryTreeNodeImpl(6))
        sut.put(MutableBinaryTreeNodeImpl(3))
        sut.put(MutableBinaryTreeNodeImpl(8))
        sut.put(MutableBinaryTreeNodeImpl(7))
        sut.put(MutableBinaryTreeNodeImpl(12))
        sut.put(MutableBinaryTreeNodeImpl(11))
        sut.put(MutableBinaryTreeNodeImpl(31))
        sut.put(MutableBinaryTreeNodeImpl(27))
        sut.put(MutableBinaryTreeNodeImpl(24))
        sut.put(MutableBinaryTreeNodeImpl(20))
        sut.put(MutableBinaryTreeNodeImpl(33))
        sut.put(MutableBinaryTreeNodeImpl(35))
        sut.put(MutableBinaryTreeNodeImpl(37))
    }

    @Test
    fun putTest() {
        // Given : // When :
        sut.put(MutableBinaryTreeNodeImpl(38))
        // Then :
        sut.root.inorderTraversal() shouldBe listOf(3, 6, 7, 8, 10, 11, 12, 15, 18, 20, 24, 27, 31, 33, 35, 37, 38)
    }

    @Test
    fun findTest() {
        // Given :
        val key = 15
        val key2 = 40
        // When :
        val result = sut.find(key)
        val result2 = sut.find(key2)
        // Then :
        assertSoftly {
            result?.key shouldBe key
            result2 shouldBe null
        }
    }

    @Test
    fun removeTest() {
        // Given :
        val key = 10

        // When :
        sut.remove(key)

        // Then :
        sut.root.inorderTraversal() shouldBe listOf(3, 6, 7, 8, 11, 12, 15, 18, 20, 24, 27, 31, 33, 35, 37)
    }

    @Test
    fun removeTest2() {
        // Given :
        val key = 31

        // When :
        sut.remove(key)

        // Then :
        sut.root.inorderTraversal() shouldBe listOf(3, 6, 7, 8, 10, 11, 12, 15, 18, 20, 24, 27, 33, 35, 37)
    }



    @Test
    fun removeTerminalNodeTest() {
        // Given :
        val key = 37

        // When :
        sut.remove(key)

        // Then :
        sut.root.inorderTraversal() shouldBe listOf(3, 6, 7, 8, 10, 11, 12, 15, 18, 20, 24, 27, 31, 33, 35)
    }
}