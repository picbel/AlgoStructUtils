package tree.binary

interface BinarySearchTree {
    val root : BinaryTreeNode

    fun find(key: Int): BinaryTreeNode?
}

interface MutableBinaryTree : BinarySearchTree {
    override val root: MutableBinaryTreeNode

    fun put(node: MutableBinaryTreeNode): Boolean

    fun remove(key: Int): Boolean

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
        var current = root

        while (true) {
            if (node.key < current.key) {
                if (current.left == null) {
                    current.left = node
                    return true
                }
                current = current.left!!
            } else if (node.key > current.key) {
                if (current.right == null) {
                    current.right = node
                    return true
                }
                current = current.right!!
            } else {
                return false
            }
        }
    }

    override fun remove(key: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun find(key: Int): MutableBinaryTreeNode? {
        var current = root
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    return null
                }
                current = current.left!!
            } else if (key > current.key) {
                if (current.right == null) {
                    return null
                }
                current = current.right!!
            } else {
                return current
            }
        }
    }
}




