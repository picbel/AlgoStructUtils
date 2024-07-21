package tree.binary

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


/**
 * @since 2024/06/12
 */
internal class BinaryTreeNodeSpec {
    private lateinit var sut : MutableBinaryTreeNode

    /**
     * 이진 트리 구조를 초기화합니다.
     *
     * ```
     *        1
     *       / \
     *      2   3
     *     / \ / \
     *    4  5 6  7
     * ```
     */

    @BeforeEach
    fun setup() {
        val seven = MutableBinaryTreeNodeImpl(7)
        val six = MutableBinaryTreeNodeImpl(6)
        val five = MutableBinaryTreeNodeImpl(5)
        val four = MutableBinaryTreeNodeImpl(4)
        val three = MutableBinaryTreeNodeImpl(3)
        val two = MutableBinaryTreeNodeImpl(2)
        sut =  MutableBinaryTreeNodeImpl(1)
        sut.left = two
        sut.right = three
        two.left = four
        two.right = five
        three.left = six
        three.right = seven
    }

    @Test
    fun preorderTraversalTest() {
        sut.preorderTraversal() shouldBe listOf(1, 2, 4, 5, 3, 6, 7)
    }

    @Test
    fun inorderTraversalTest() {
        sut.inorderTraversal() shouldBe listOf(4, 2, 5, 1, 6, 3, 7)
    }

    @Test
    fun postorderTraversalTest() {
        sut.postorderTraversal() shouldBe listOf(4, 5, 2, 6, 7, 3, 1)
    }

}
