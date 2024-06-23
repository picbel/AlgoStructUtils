package tree.trie

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class TrieNodeSpec {

    @DisplayName("하위 노드를 추가합니다")
    @Test
    fun putNode() {
        // given
        val root = MutableTrieNode.rootNode()
        val key = 'a'
        val node = MutableTrieNodeImpl(key = key, value = "test")
        // when
        root.put(node)
        // then
        val expect = root.children[key]
        expect shouldBe node
    }

    @DisplayName("하위 노드를 제거합니다")
    @Test
    fun removeNode() {
        // given
        val root = MutableTrieNode.rootNode()
        val key = 'a'
        val node = MutableTrieNodeImpl(key = key, value = "test")
        root.put(node)
        // when
       root.remove(key)
        // then
        val expect = root.children[key]
        expect shouldBe  null
    }

    @DisplayName("가장 많이 매칭된 문자열의 value를 반환합니다 : ")
    @Nested
    inner class FindSimilarNodeSpec {
        private lateinit var root: MutableTrieNode

        @BeforeEach
        fun setup() {
            root = MutableTrieNode.rootNode()
            setNode("서울","에러")
            setNode("서울특별시", "직접배송")
            setNode("경기도", "지입배송")
            setNode("경상북도", "택배")
            setNode("경상남도", "택배")
            setNode("세종시", "지입배송")
        }

        @DisplayName("'경기도'로 조회시 value는 '지입배송'입니다.")
        @Test
        fun allMatching() {
            val node = root.findSimilarNode("경기도")
            assertSoftly {
                node?.key shouldBe '도'
                node?.value shouldBe "지입배송"
            }
        }

        @DisplayName("'서울특별시강남'으로 조회시 value는 '직접배송'여야 합니다.")
        @Test
        fun similarMatching() {
            // 가장 비슷한 '서울특별시'의 값을 가져옵니다
            val node = root.findSimilarNode("서울특별시강남")
            assertSoftly {
                node?.key shouldBe '시'
                node?.value shouldBe "직접배송"
            }
        }

        @DisplayName("'뉴욕'으로 조회시 value가 없습니다.")
        @Test
        fun missing() {
            val node = root.findSimilarNode("뉴욕")
            assertSoftly {
                node shouldBe null
            }
        }

        private fun setNode(str: String, value: String): Boolean {
            var currentNode: MutableTrieNode = root
            for (char in str) {
                val childNode = currentNode.children[char] ?: MutableTrieNodeImpl(char).also {
                    currentNode.children[char] = it
                }
                currentNode = childNode
            }
            currentNode.value = value
            return true
        }
    }

}