package tree.binary

import tree.TreeNode

/**
 * @since 2024/06/12
 */
interface BinaryTreeNode : TreeNode<Int, Int> {
    override val value: Int
        get() = key

    override val children: Map<Int, BinaryTreeNode>
        get() = mutableMapOf<Int, BinaryTreeNode>().apply {
            left?.let { put(it.key, it) }
            right?.let { put(it.key, it) }
        }

    var left: BinaryTreeNode?

    var right: BinaryTreeNode?

    fun search(key: Int): Boolean

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
    fun put(key: Int)

    fun remove(key: Int)

}

class MutableBinaryTreeNodeImpl(
    override val key: Int,
    override var left: BinaryTreeNode? = null,
    override var right: BinaryTreeNode? = null
): MutableBinaryTreeNode {

    override fun search(key: Int): Boolean {
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

    override fun put(key: Int) {
        TODO("Not yet implemented")
    }

    override fun remove(key: Int) {
        TODO("Not yet implemented")
    }
}


