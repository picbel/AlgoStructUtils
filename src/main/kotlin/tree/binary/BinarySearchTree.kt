package tree.binary

interface BinarySearchTree {
    val root : BinaryTreeNode

    fun find(key: Int): Int?
}

interface MutableBinaryTree : BinarySearchTree {
    override val root: MutableBinaryTreeNode

    fun put(node: MutableBinaryTreeNode): Boolean

//    fun remove(key: Int): Boolean

    companion object {
        fun create(root: MutableBinaryTreeNode): MutableBinaryTree {
            return MutableBinaryTreeImpl(root)
        }

    }
}

class MutableBinaryTreeImpl(
    override val root: MutableBinaryTreeNode
) : MutableBinaryTree {

    override fun put(node: MutableBinaryTreeNode): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(key: Int): Int? {
        TODO("Not yet implemented")
    }
}




