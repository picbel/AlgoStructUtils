package tree.binary

interface BinarySearchTree {
    val root : BinaryTreeNode
}

interface MutableBinaryTree : BinarySearchTree {
    fun put(key: Int, value: Int): Boolean
    fun remove(key: Int): Boolean
    fun clearValue(key: Int): Boolean
}
