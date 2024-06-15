package tree.trie

// 240612 tree 인터페이스를 확장하는 식으로 구현하여보자
/**
 * Trie 자료구조를 구현합니다.
 * @since 2024/05/21
 */
interface Trie { // 애가 굳이 필요있을까? TrieNode만으로도 해결 가능하여 보인다.
    val root: TrieNode

    /**
     * @param str 비교할 문자열
     * @return 해당 문자열중 가장 많이 매칭된 문자열의 value를 가져옵니다.
     */
    fun findSimilarValue(str: String): String?

    /**
     * @return Trie를 MutableTrie로 변환합니다.
     */
    fun toMutableTrie(): MutableTrie

    companion object {
        fun empty(): Trie = TrieImpl()
    }
}

interface MutableTrie : Trie {
    /**
     * @param str 추가할 단어
     * @param value 단어에 대응하는 값
     */
    fun put(str: String, value: String): Boolean

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
        fun empty(): MutableTrie = TrieImpl()
    }
}

internal class TrieImpl(
    override val root: MutableTrieNodeImpl = MutableTrieNodeImpl()
) : MutableTrie {


    override fun findSimilarValue(str: String): String? {
        var currentNode : MutableTrieNode= root
        var value: String? = null
        for (char in str) {
            val childNode = currentNode.children[char] ?: break
            if (childNode.value != null) value = childNode.value
            currentNode = childNode
        }
        return value
    }

    override fun put(str: String, value: String): Boolean {
        var currentNode : MutableTrieNode= root
        for (char in str) {
            val childNode = currentNode.children[char] ?: MutableTrieNodeImpl().also { currentNode.children[char] = it }
            currentNode = childNode
        }
        currentNode.value = value
        return true
    }

    override fun remove(str: String): Boolean {
        // 삭제할 문자열의 노드를 저장합니다. first: 문자, second: 부모 노드
        val nodes = mutableListOf<Pair<Char, MutableTrieNodeImpl>>()
        var currentNode : MutableTrieNode = root
        for (char in str) {
            val childNode  = currentNode.children[char] ?: return false
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
        var currentNode : MutableTrieNode = root
        for (char in str) {
            val childNode = currentNode.children[char] ?: return false
            currentNode = childNode
        }
        currentNode.value = null
        if (!currentNode.hasChildren()) remove(str) // value도 없고 자식node도 없다면 그냥 삭제
        return true
    }

    override fun toMutableTrie(): MutableTrie = this
}

// value를 T로 제네릭으로 하자
interface TrieNode {
    val children: Map<Char, TrieNode>
    val value: String?
    fun hasChildren(): Boolean = children.isNotEmpty()
    // Trie인터페이스를 삭제하고 이걸로 하면 되지 않을까?
    /**
     * @param str 비교할 문자열
     * @return 해당 문자열중 가장 많이 매칭된 문자열의 value를 가져옵니다.
     */
//    fun findSimilarValue(str: String): String?

//
//    /**
//     * 해당 문자열 구조를 trie에서 삭제합니다
//     * 부모 노드가 자식 노드를 가지고 있지 않고 value가 null일 경우 삭제합니다.
//     *
//     * @param str 삭제할 문자열
//     */
//    fun remove(str: String): Boolean
}

interface MutableTrieNode : TrieNode {
    override var value: String?
    override val children: MutableMap<Char, MutableTrieNode>

    /**
     * @param str 추가할 단어
     * @param value 단어에 대응하는 값
     */
//    fun put(str: String, value: String): Boolean
}

internal class MutableTrieNodeImpl(
    override var value: String? = null
) : MutableTrieNode {
    override val children: MutableMap<Char, MutableTrieNode> = mutableMapOf()
}
