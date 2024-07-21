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

    val left: BinaryTreeNode?

    val right: BinaryTreeNode?

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

}

interface MutableBinaryTreeNode : BinaryTreeNode {

    override var left: MutableBinaryTreeNode?

    override var right: MutableBinaryTreeNode?

}

class MutableBinaryTreeNodeImpl(
    override val key: Int,
    override var left: MutableBinaryTreeNode? = null,
    override var right: MutableBinaryTreeNode? = null
) : MutableBinaryTreeNode {

    override fun preorderTraversal(): List<Int> {
        return preorderTraversalInternal(this, mutableListOf())
    }

    private fun preorderTraversalInternal(node: BinaryTreeNode?, acc: MutableList<Int>): List<Int> {
        node?.let {
            acc.add(it.key)
            preorderTraversalInternal(it.left, acc)
            preorderTraversalInternal(it.right, acc)
        }
        return acc
    }

    override fun inorderTraversal(): List<Int> {
        return inorderTraversalInternal(this, mutableListOf())
    }

    private fun inorderTraversalInternal(node: BinaryTreeNode?, acc: MutableList<Int>): List<Int> {
        node?.let {
            inorderTraversalInternal(it.left, acc)
            acc.add(it.key)
            inorderTraversalInternal(it.right, acc)
        }
        return acc
    }

    override fun postorderTraversal(): List<Int> {
        return postorderTraversalInternal(this, mutableListOf())
    }

    private fun postorderTraversalInternal(node: BinaryTreeNode?, acc: MutableList<Int>): List<Int> {
        node?.let {
            postorderTraversalInternal(it.left, acc)
            postorderTraversalInternal(it.right, acc)
            acc.add(it.key)
        }
        return acc
    }
}


