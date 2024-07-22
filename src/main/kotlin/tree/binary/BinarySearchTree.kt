package tree.binary

interface BinarySearchTree {
    val root : BinaryTreeNode

    fun find(key: Int): Int?
}

interface MutableBinaryTree : BinarySearchTree {
    fun put(key: Int, value: Int): Boolean

    fun remove(key: Int): Boolean
}
