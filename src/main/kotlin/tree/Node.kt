package tree

interface Node<K, V> {

    val key: K

    val value: V?

}

interface TreeNode<K, V> : Node<K, V> {

    val children: Map<K, TreeNode<K, V>>

    fun hasChildren(): Boolean {
        return children.isNotEmpty()
    }

    fun isTerminalNode(): Boolean {
        return !hasChildren()
    }

}

