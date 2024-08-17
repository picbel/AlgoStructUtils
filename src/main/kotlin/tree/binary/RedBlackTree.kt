package tree.binary

interface RedBlackTree<K : Comparable<K>, V> : BinarySearchTree<K, V> {

    companion object {
        fun <K : Comparable<K>, V> create(root: MutableBinaryTreeNode<K, V>): RedBlackTree<K, V> {
            return MutableRedBlackTreeImpl(root)
        }
    }
}

interface MutableRedBlackTree<K : Comparable<K>, V> : RedBlackTree<K, V>, MutableBinarySearchTree<K, V> {

    companion object {
        fun <K : Comparable<K>, V> create(root: MutableBinaryTreeNode<K, V>): MutableRedBlackTree<K, V> {
            return MutableRedBlackTreeImpl(root)
        }
    }
}

class MutableRedBlackTreeImpl<K : Comparable<K>, V>(
    override var root: MutableBinaryTreeNode<K, V>
) : MutableRedBlackTree<K, V> {

    override fun put(node: MutableBinaryTreeNode<K, V>): Boolean {

        TODO()
    }

    override fun remove(key: K): MutableBinaryTreeNode<K, V>? {
        TODO()
    }
}
