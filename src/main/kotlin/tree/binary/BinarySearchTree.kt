package tree.binary

interface BinarySearchTree<K : Comparable<K>, V> {
    val root: BinaryTreeNode<K, V>

    fun find(key: K): BinaryTreeNode<K, V>?
}

interface MutableBinarySearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V> {
    override var root: MutableBinaryTreeNode<K, V>

    fun put(node: MutableBinaryTreeNode<K, V>): Boolean

    fun remove(key: K): MutableBinaryTreeNode<K, V>?

    companion object {
        fun <K : Comparable<K>, V> create(root: MutableBinaryTreeNode<K, V>): MutableBinarySearchTree<K, V> {
            return MutableBinarySearchTreeImpl(root)
        }
    }
}

class MutableBinarySearchTreeImpl<K : Comparable<K>, V>(
    override var root: MutableBinaryTreeNode<K, V>
) : MutableBinarySearchTree<K, V> {

    override fun put(node: MutableBinaryTreeNode<K, V>): Boolean {
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

    override fun remove(key: K): MutableBinaryTreeNode<K, V>? {
        val fakeRootNode = MutableBinaryTreeNodeImpl(key = root.key, value = root.value)
        var parentNode: MutableBinaryTreeNode<K, V> = fakeRootNode
        var current: MutableBinaryTreeNode<K, V> = root
        val deleteNode: MutableBinaryTreeNode<K, V>
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
        if (deleteNode.isTerminalNode()) {
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
        var leftMax = deleteNode.left!!
        var leftMaxParentNode = deleteNode
        while (true) {
            if (leftMax.right == null) break
            leftMaxParentNode = leftMax
            leftMax = leftMax.right!!
        }
        // leftMax의 부모노드에 남은 leftMax 왼쪽 자식을 연결한다
        if (leftMaxParentNode.left == leftMax) {
            leftMaxParentNode.left = leftMax.left
        } else {
            leftMaxParentNode.right = leftMax.left
        }

        // leftMax를 삭제할 노드의 자리로 옮긴다
        leftMax.left = deleteNode.left
        leftMax.right = deleteNode.right

        // 삭제할 노드의 부모노드에 leftMax를 연결한다
        if (parentNode.left == deleteNode) {
            parentNode.left = leftMax
        } else {
            parentNode.right = leftMax
        }

        // root에 변경이 있다면
        if (fakeRootNode.right != root) {
            root = fakeRootNode.right!!
        }
        return deleteNode
    }

    override fun find(key: K): MutableBinaryTreeNode<K, V>? {
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




