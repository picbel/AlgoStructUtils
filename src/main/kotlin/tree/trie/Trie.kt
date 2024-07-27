package tree.trie

import tree.TreeNode

/**
 * Trie 자료구조를 구현합니다.
 * @since 2024/02/04
 */
interface Trie<V> {
    val root: TrieNode<V>

    /**
     * @param str 비교할 문자열
     * @return 해당 문자열중 가장 많이 매칭된 문자열의 value를 가져옵니다.
     */
    fun findSimilarValue(str: String): V?

    /**
     * @return Trie를 MutableTrie로 변환합니다.
     */
    fun toMutableTrie(): MutableTrie<V>

    companion object {
        fun <V> create(
            root: TrieNode<V> = MutableTrieNode.rootNode()
        ): Trie<V> = TrieImpl(root.toMutableTrieNode())
    }
}

interface MutableTrie<V> : Trie<V> {
    /**
     * @param str 추가할 단어
     * @param value 단어에 대응하는 값
     */
    fun put(str: String, value: V): Boolean

    /**
     * 해당 문자열 구조를 trie에서 삭제합니다
     * 부모 노드가 자식 노드를 가지고 있지 않고 value가 null일 경우 삭제합니다.
     *
     * @param str 삭제할 문자열
     */
    fun remove(str: String): Boolean

    /**
     * 해당 문자열의 value를 삭제합니다
     * @param str
     */
    fun clearValue(str: String): Boolean

    companion object {

        fun <V> create(
            root: MutableTrieNode<V> = MutableTrieNode.rootNode()
        ): MutableTrie<V> = TrieImpl(root)

    }
}

internal class TrieImpl<V>(
    override val root: MutableTrieNode<V> = MutableTrieNode.rootNode()
) : MutableTrie<V> {


    override fun findSimilarValue(str: String): V? {
        return root.findSimilarNode(str)?.value
    }

    override fun put(str: String, value: V): Boolean {
        var currentNode: MutableTrieNode<V> = root
        for (char in str) {
            val childNode =
                currentNode.children[char] ?: MutableTrieNodeImpl<V>(char).also { currentNode.put(it) }
            currentNode = childNode
        }
        currentNode.value = value
        currentNode.isEnd = true
        return true
    }

    override fun remove(str: String): Boolean {
        // 삭제할 문자열의 노드를 저장합니다. first: 문자, second: 부모 노드
        val nodes = mutableListOf<Pair<Char, MutableTrieNode<V>>>()
        var currentNode: MutableTrieNode<V> = root
        for (char in str) {
            val childNode = currentNode.children[char] ?: return false
            nodes.add(char to currentNode)
            currentNode = childNode
        }
        if (currentNode.hasChildren()) return false
        for (i in nodes.size - 1 downTo 0) {
            val (char, parentNode) = nodes[i]
            if (currentNode.hasChildren() && currentNode.value != null) break
            parentNode.children.remove(char)
            currentNode = parentNode
        }
        return true
    }

    override fun clearValue(str: String): Boolean {
        var currentNode: MutableTrieNode<V> = root
        for (char in str) {
            val childNode = currentNode.children[char] ?: return false
            currentNode = childNode
        }
        currentNode.value = null
        currentNode.isEnd = false
        if (!currentNode.hasChildren()) remove(str) // value도 없고 자식node도 없다면 그냥 삭제
        return true
    }

    override fun toMutableTrie(): MutableTrie<V> = this
}

interface TrieNode<V> : TreeNode<Char, V> {
    override val children: Map<Char, TrieNode<V>>
    val isEnd: Boolean

    /**
     * @param str 비교할 문자열
     * @return 해당 문자열중 가장 많이 매칭된 문자열의 node를 가져옵니다.
     */
    fun findSimilarNode(str: String): TrieNode<V>? {
        if (str.isEmpty() || str.isBlank()) return null
        return children[str.first()]?.let { child ->
            if (str.length == 1) {
                child
            } else {
                child.findSimilarNode(str.substring(1)) ?: child
            }
        }
    }

    companion object {}

}

interface MutableTrieNode<V> : TrieNode<V> {
    override var value: V?
    override val children: MutableMap<Char, MutableTrieNode<V>>
    override var isEnd: Boolean

    /**
     * @param node 추가할 노드
     */
    fun put(node: MutableTrieNode<V>): Boolean {
        children[node.key] = node
        return true
    }

    /**
     * 해당 문자열 key를 가진 자식 목록에서 노드를 삭제합니다
     *
     * @param key 삭제할 문자열
     * @return 삭제 성공 여부 (해당 node ket가 없을 경우 false)
     */
    fun remove(key: Char): Boolean {
        return children.remove(key) != null
    }

    companion object {
        fun <V> rootNode(): MutableTrieNode<V> = MutableTrieNodeImpl(Char.MIN_VALUE)
    }
}

fun <V> TrieNode<V>.toMutableTrieNode(): MutableTrieNode<V> = MutableTrieNodeImpl(
    key = key,
    value = value,
    children = children.mapValues { it.value.toMutableTrieNode() }.toMutableMap(),
    isEnd = isEnd
)

internal data class MutableTrieNodeImpl<V>(
    override val key: Char,
    override var value: V? = null,
    override val children: MutableMap<Char, MutableTrieNode<V>> = mutableMapOf(),
    override var isEnd: Boolean = false
) : MutableTrieNode<V>
