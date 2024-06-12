package tree.binary

import org.junit.jupiter.api.BeforeEach


/**
 * @since 2024/06/12
 */
internal class BinaryTreeNodeSpec {
    private lateinit var sut : MutableBinaryTreeNode

    @BeforeEach
    fun setup() {
        sut = MutableBinaryTreeNodeImpl(10)
    }

}
