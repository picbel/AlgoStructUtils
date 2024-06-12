package tree.trie.util

import org.json.JSONObject
import tree.trie.MutableTrieNode
import tree.trie.MutableTrieNodeImpl
import tree.trie.TrieNode

/**
 * @since 2024/02/04
 * Trie를 JSON 문자열로 변환하거나 JSON 문자열로부터 Trie를 구축하는 유틸리티 클래스입니다.
 */
object TrieJsonUtils {
    private const val VALUE = "value"


    /**
     * @return Trie를 JSON 문자열로 변환합니다.
     */
    fun TrieNode.toJsonString(): String {
        return trieToJson(this).toString()
    }

    private fun trieToJson(node: TrieNode): JSONObject {
        val jsonObject = JSONObject()
        node.value?.let { jsonObject.put("value", it) }
        node.children.forEach { (char, childNode) ->
            jsonObject.put(char.toString(), trieToJson(childNode))
        }
        return jsonObject
    }

    /**
     * @return JSON 문자열로부터 Trie를 구축합니다.
     */
    fun TrieNode.Companion.fromJson(jsonStr: String): TrieNode {
        return MutableTrieNode.fromJson(jsonStr)
    }

    /**
     * @return JSON 문자열로부터 Trie를 구축합니다.
     */
    fun MutableTrieNode.Companion.fromJson(jsonStr: String): MutableTrieNode {
        val jsonObject = JSONObject(jsonStr)
        val node = rootNode()
        buildTrieNodeFromJson(jsonObject, node)
        return node
    }

    // JSON 객체로부터 Trie를 구축하는 수정된 메소드
    private fun buildTrieNodeFromJson(jsonObject: JSONObject, node: MutableTrieNode) {
        jsonObject.keys().forEach { key ->
            when (key) {
                VALUE -> node.value = jsonObject.getString(key) ?: null
                else -> {
                    val childNode = MutableTrieNodeImpl(key[0])
                    node.children[key[0]] = childNode
                    buildTrieNodeFromJson(jsonObject.getJSONObject(key), childNode)
                }
            }
        }
    }

}
