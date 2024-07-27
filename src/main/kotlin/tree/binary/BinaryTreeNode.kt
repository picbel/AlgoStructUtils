package tree.binary

import tree.TreeNode

/**
 * @since 2024/06/12
 */
interface BinaryTreeNode<K : Comparable<K>, V> : TreeNode<K, V> {

    override val children: Map<K, BinaryTreeNode<K, V>>
        get() = mutableMapOf<K, BinaryTreeNode<K, V>>().apply {
            left?.let { put(it.key, it) }
            right?.let { put(it.key, it) }
        }

    val left: BinaryTreeNode<K, V>?

    val right: BinaryTreeNode<K, V>?

    /**
     * @return 전위 순회 결과
     */
    fun preorderTraversal(): List<K>

    /**
     * @return 중위 순회 결과
     */
    fun inorderTraversal(): List<K>

    /**
     * @return 후위 순회 결과
     */
    fun postorderTraversal(): List<K>

}

interface MutableBinaryTreeNode<K : Comparable<K>, V> : BinaryTreeNode<K, V> {
    override var value: V

    override var left: MutableBinaryTreeNode<K, V>?

    override var right: MutableBinaryTreeNode<K, V>?

}

class MutableBinaryTreeNodeImpl<K : Comparable<K>, V>(
    override var key: K,
    override var value: V,
    override var left: MutableBinaryTreeNode<K, V>? = null,
    override var right: MutableBinaryTreeNode<K, V>? = null
) : MutableBinaryTreeNode<K, V> {

    override fun preorderTraversal(): List<K> {
        return preorderTraversalInternal(this, mutableListOf())
    }

    private fun preorderTraversalInternal(node: BinaryTreeNode<K, V>?, acc: MutableList<K>): List<K> {
        node?.let {
            acc.add(it.key)
            preorderTraversalInternal(it.left, acc)
            preorderTraversalInternal(it.right, acc)
        }
        return acc
    }

    override fun inorderTraversal(): List<K> {
        return inorderTraversalInternal(this, mutableListOf())
    }

    private fun inorderTraversalInternal(node: BinaryTreeNode<K, V>?, acc: MutableList<K>): List<K> {
        node?.let {
            inorderTraversalInternal(it.left, acc)
            acc.add(it.key)
            inorderTraversalInternal(it.right, acc)
        }
        return acc
    }

    override fun postorderTraversal(): List<K> {
        return postorderTraversalInternal(this, mutableListOf())
    }

    private fun postorderTraversalInternal(node: BinaryTreeNode<K, V>?, acc: MutableList<K>): List<K> {
        node?.let {
            postorderTraversalInternal(it.left, acc)
            postorderTraversalInternal(it.right, acc)
            acc.add(it.key)
        }
        return acc
    }
}


