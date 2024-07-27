package tree.binary

interface BinarySearchTree {
    val root : BinaryTreeNode

    fun find(key: Int): BinaryTreeNode?
}

interface MutableBinarySearchTree : BinarySearchTree {
    override var root: MutableBinaryTreeNode

    fun put(node: MutableBinaryTreeNode): Boolean

    fun remove(key: Int): MutableBinaryTreeNode?

    companion object {
        fun create(root: MutableBinaryTreeNode): MutableBinarySearchTree {
            return MutableBinarySearchTreeImpl(root)
        }
    }
}

class MutableBinarySearchTreeImpl(
    override var root: MutableBinaryTreeNode
) : MutableBinarySearchTree {

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

    override fun remove(key: Int): MutableBinaryTreeNode? {
        val fakeRootNode = MutableBinaryTreeNodeImpl(-1)
        var parentNode : MutableBinaryTreeNode  = fakeRootNode
        var current: MutableBinaryTreeNode  = root
        val deleteNode : MutableBinaryTreeNode
        fakeRootNode.right = root
        while (true) {
            if (key < current.key) {
                if (current.left == null) {
                    return null
                }
                parentNode = current
                current = current.left!!
            } else if (key > current.key) {
                if (current.right == null) {
                    return null
                }
                parentNode = current
                current = current.right!!
            } else {
                deleteNode = current
                break
            }
        }

        // 터미널 노드인경우
        if (deleteNode.isTerminalNode()){
            if (parentNode.left == deleteNode) {
                parentNode.left = null
            } else {
                parentNode.right = null
            }

            return deleteNode
        }

        // 자식이 하나인 경우
        if (deleteNode.children.size == 1) {
            if (deleteNode.left != null) {
                if (parentNode.left == deleteNode) {
                    parentNode.left = deleteNode.left
                } else {
                    parentNode.right = deleteNode.left
                }
            } else {
                if (parentNode.left == deleteNode) {
                    parentNode.left = deleteNode.right
                } else {
                    parentNode.right = deleteNode.right
                }
            }
            return deleteNode
        }

        // 자식이 두개인 경우
        current = deleteNode.left!!
        parentNode = deleteNode
        while (true) {
            if (current.right == null) break
            parentNode = current
            current = current.right!!
        }

        val tempDeleteNode = MutableBinaryTreeNodeImpl(deleteNode.key)
        if (parentNode.left == current) {
            parentNode.left = current.left
        } else {
            parentNode.right = current.left
        }
        deleteNode.value = current.value

        // root에 변경이 있다면
        if (fakeRootNode.right != root) {
            root = fakeRootNode.right!!
        }

        return tempDeleteNode
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




