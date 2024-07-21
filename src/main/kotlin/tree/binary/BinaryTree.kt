package tree.binary

interface BinaryTree {
    val root : BinaryTreeNode
}

interface MutableBinaryTree : BinaryTree {
    fun put(key: Int, value: Int): Boolean
    fun remove(key: Int): Boolean
    fun clearValue(key: Int): Boolean
}
