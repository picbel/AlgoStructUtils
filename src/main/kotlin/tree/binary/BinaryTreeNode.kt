package tree.binary

/**
 * @since 2024/06/12
 */
interface BinaryTreeNode {
    val value: Int

    var left: BinaryTreeNode?

    var right: BinaryTreeNode?

    fun search(value: Int): Boolean

    /**
     * @return 전위 순회 결과
     */
    fun preorderTraversal(): List<Int>
    /**
     * @return 중위 순회 결과
     */
    fun inorderTraversal(): List<Int>
    /**
     * @return 후위 순회 결과
     */
    fun postorderTraversal(): List<Int>

//    /**
//     * @return 최대값
//     */
//    fun max(): Int
//    /**
//     * @return 최소값
//     */
//    fun min(): Int

}

interface MutableBinaryTreeNode : BinaryTreeNode {
    fun insert(value: Int)

    fun delete(value: Int)

}

class MutableBinaryTreeNodeImpl(
    override val value: Int,
    override var left: BinaryTreeNode? = null,
    override var right: BinaryTreeNode? = null
): MutableBinaryTreeNode {

    override fun search(value: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun preorderTraversal(): List<Int> {
        TODO("Not yet implemented")
    }

    override fun inorderTraversal(): List<Int> {
        TODO("Not yet implemented")
    }

    override fun postorderTraversal(): List<Int> {
        TODO("Not yet implemented")
    }

    override fun insert(value: Int) {
        TODO("Not yet implemented")
    }

    override fun delete(value: Int) {
        TODO("Not yet implemented")
    }
}


