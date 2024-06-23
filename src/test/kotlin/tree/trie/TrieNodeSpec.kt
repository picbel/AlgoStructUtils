package tree.trie

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class TrieNodeSpec {
    private lateinit var root: MutableTrieNode

    @BeforeEach
    fun setup() {
        root = MutableTrieNode.rootNode()
        setNode("서울특별시", "직접배송")
        setNode("경기도", "지입배송")
        setNode("경상북도", "택배")
        setNode("경상남도", "택배")
        setNode("세종시", "지입배송")
    }

    private fun setNode(str: String, value: String): Boolean {
        var currentNode : MutableTrieNode= root
        for (char in str) {
            val childNode = currentNode.children[char] ?: MutableTrieNodeImpl(char).also {
                currentNode.children[char] = it
            }
            currentNode = childNode
        }
        currentNode.value = value
        return true
    }


    @DisplayName("가장 많이 매칭된 문자열의 value를 반환합니다 : ")
    @Nested
    inner class FindSimilarValueSpec {
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
    }

    @DisplayName("하위 노드를 추가 및 수정합니다 : ")
    @Nested
    inner class PutSpec {
        @DisplayName("'제주도'를 추가합니다")
        @Test
        fun new() {
            // given

            // when

            // then
        }

        @DisplayName("'서울특별시송파구'를 추가합니다.")
        @Test
        fun put() {
            // given

            // when

            // then
        }

        @DisplayName("'경상북도'의 value를 '택배'에서 '배송불가'로 변경합니다.'")
        @Test
        fun modify() {
            // given

            // when

            // then
        }
    }

    @Nested
    inner class RemoveAndClearSpec {
        @DisplayName("문자열을 삭제합니다")
        @Test
        fun remove() {
            // given

            // when

            // then
        }

        @DisplayName("없는 문자열을 삭제 할 수 없습니다")
        @Test
        fun removeFail() {
            // given

            // when

            // then
        }

        @DisplayName("자식이 있는 문자열은 삭제 할 수 없습니다.")
        @Test
        fun removeFail2() {
            // given

            // when

            // then
        }

        @DisplayName("문자열의 value를 삭제합니다.")
        @Test
        fun clearValue() {
            // given

            // when

            // then
        }
    }

}