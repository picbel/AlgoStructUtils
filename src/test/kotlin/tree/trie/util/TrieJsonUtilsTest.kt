package tree.trie.util

import org.json.JSONObject
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tree.trie.TrieNode
import tree.trie.util.TrieJsonUtils.fromJson
import tree.trie.util.TrieJsonUtils.toJsonString

/**
 * @since 2024/02/04
 */
class TrieJsonUtilsTest {
    private val jsonString = """
    {
        "경": {
            "기": {
                "도": {
                    "value": "지입배송"
                }
            },
            "상": {
                "북": {
                    "도" : {
                        "value": "택배"
                    }
                },
                "남": {
                    "도" : {
                        "value": "택배"
                    }
                }
            }
        },
        "서": {
            "울": {
                "시": {"value": "직접배송"}
            }
        }
    }
    """.trimIndent()

    private val trie = TrieNode.fromJson(jsonString)

    @DisplayName("Trie를 JSON 문자열로 변환합니다.")
    @Test
    fun toJsonString() {
        // given // when
        val jsonString = trie.toJsonString()
        // then
        assert(JSONObject(jsonString).toString() == JSONObject(this.jsonString).toString())
    }
}