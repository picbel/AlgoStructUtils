package tree.trie.util

import org.json.JSONObject
import tree.trie.*

@Deprecated("다음작업에서 제거 예정")
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
        val node = MutableTrieNode.rootNode() // 수정 필요
        buildTrieFromJson(jsonObject, node)
        return TrieImpl(node)
    }

    // JSON 객체로부터 Trie를 구축하는 수정된 메소드
    private fun buildTrieFromJson(jsonObject: JSONObject, node: MutableTrieNode) {
        jsonObject.keys().forEach { key ->
            when (key) {
                VALUE -> node.value = jsonObject.getString(key) ?: null
                else -> {
                    val childNode = MutableTrieNodeImpl(key[0]) // 수정필요
                    node.children[key[0]] = childNode
                    buildTrieFromJson(jsonObject.getJSONObject(key), childNode)
                }
            }
        }
    }

}
