package tree.binary

interface AvlTree<K : Comparable<K>, V> : BinarySearchTree<K, V> {

    companion object {
        fun <K : Comparable<K>, V> create(root: MutableBinaryTreeNode<K, V>): MutableAvlTree<K, V> {
            return MutableAvlTreeImpl(root)
        }
    }
}

interface MutableAvlTree<K : Comparable<K>, V> : AvlTree<K, V>, MutableBinarySearchTree<K, V> {

    companion object {
        fun <K : Comparable<K>, V> create(root: MutableBinaryTreeNode<K, V>): MutableAvlTree<K, V> {
            return MutableAvlTreeImpl(root)
        }
    }
}


class MutableAvlTreeImpl<K : Comparable<K>, V>(
    override var root: MutableBinaryTreeNode<K, V>
) : MutableAvlTree<K, V> {

    override fun put(node: MutableBinaryTreeNode<K, V>): Boolean {

        TODO("Not yet implemented")
    }

    override fun remove(key: K): MutableBinaryTreeNode<K, V>? {


        TODO("Not yet implemented")
    }

    private fun height(node: MutableBinaryTreeNode<K, V>?): Int {
       TODO()
    }

    private fun updateHeight(node: MutableBinaryTreeNode<K, V>) {
        TODO()
    }

    private fun balanceFactor(node: MutableBinaryTreeNode<K, V>?): Int {
        TODO()
    }

    private fun rotateLeft(node: MutableBinaryTreeNode<K, V>): MutableBinaryTreeNode<K, V> {
        TODO()
    }

    private fun rotateRight(node: MutableBinaryTreeNode<K, V>): MutableBinaryTreeNode<K, V> {
        TODO()
    }

    private fun rotation(node: MutableBinaryTreeNode<K, V>): MutableBinaryTreeNode<K, V> {
        TODO()
    }

    private fun getUnbalancedNode(node: MutableBinaryTreeNode<K, V>): MutableBinaryTreeNode<K, V>? {
        TODO()
    }

    private fun removeHelper(node: MutableBinaryTreeNode<K, V>?, key: K): MutableBinaryTreeNode<K, V>? {
        TODO()
    }



}
