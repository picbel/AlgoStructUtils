package tree.trie.util

import org.json.JSONObject
import tree.trie.Trie
import tree.trie.TrieImpl
import tree.trie.MutableTrieNodeImpl
import tree.trie.TrieNode

/**
 * Trie를 JSON 문자열로 변환하거나 JSON 문자열로부터 Trie를 구축하는 유틸리티 클래스입니다.
 */
object TrieJsonUtils {
    private const val VALUE = "value"


    /**
     * @return Trie를 JSON 문자열로 변환합니다.
     */
    fun Trie.toJsonString(): String {
        return trieToJson(root).toString()
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
    fun Trie.Companion.fromJson(jsonStr: String): Trie {
        val jsonObject = JSONObject(jsonStr)
        val node = MutableTrieNodeImpl()
        buildTrieFromJson(jsonObject, node)
        return TrieImpl(node)
    }

    // JSON 객체로부터 Trie를 구축하는 수정된 메소드
    private fun buildTrieFromJson(jsonObject: JSONObject, node: MutableTrieNodeImpl) {
        jsonObject.keys().forEach { key ->
            when (key) {
                VALUE -> node.value = jsonObject.getString(key) ?: null
                else -> {
                    val childNode = MutableTrieNodeImpl()
                    node.children[key[0]] = childNode
                    buildTrieFromJson(jsonObject.getJSONObject(key), childNode)
                }
            }
        }
    }

}
