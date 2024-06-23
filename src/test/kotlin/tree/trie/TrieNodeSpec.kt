package tree.trie

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested


internal class TrieNodeSpec {
    private val root = MutableTrieNodeImpl()

    @DisplayName("가장 많이 매칭된 문자열의 value를 반환합니다 : ")
    @Nested
    inner class FindSimilarValueSpec {
        @DisplayName("'경기도'로 조회시 value는 '택배'여야 합니다.")
        @Test
        fun allMatching() {
            // given

            // when

            // then
        }

        @DisplayName("'서울특별시강남'으로 조회시 value는 '직접배송'여야 합니다.")
        @Test
        fun similarMatching() {
            // given

            // when

            // then

        }

        @DisplayName("'뉴욕'으로 조회시 value가 없습니다.")
        @Test
        fun missing() {
            // given

            // when

            // then
        }
    }

    @DisplayName("문자열을 추가 및 수정합니다 : ")
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