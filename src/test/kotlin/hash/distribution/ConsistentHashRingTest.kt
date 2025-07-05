package hash.distribution

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 * `ConsistentHashRing` 클래스의 동작을 검증하는 테스트 클래스입니다.
 */
class ConsistentHashRingTest {

    @Test
    fun `get returns null when ring is empty`() {
        // 링이 비어있을 때 키를 조회하면 null을 반환해야 합니다.
        val ring = ConsistentHashRing<String>(emptyList())
        assertNull(ring.get("anyKey"))
    }

    @Test
    fun `get returns the only node in the ring`() {
        // 링에 노드가 하나만 있을 때, 어떤 키를 조회하든 항상 그 노드를 반환해야 합니다.
        val ring = ConsistentHashRing(listOf("node1"))
        assertEquals("node1", ring.get("anyKey"))
    }

    @Test
    fun `key is remapped after node addition`() {
        // 테스트의 예측 가능성을 위해 제어된 해시 함수를 사용합니다.
        val controlledHash: (Any) -> Int = { key ->
            when (val s = key.toString()) {
                "nodeA0" -> 10 // 노드 A의 가상 노드 해시
                "nodeC0" -> 30 // 노드 C의 가상 노드 해시
                "nodeB0" -> 20 // 새로 추가될 노드 B의 가상 노드 해시
                "mykey"  -> 15 // 테스트에 사용할 키의 해시
                else     -> s.hashCode()
            }
        }

        // 초기 상태: 노드 A, C만 존재
        val ring = ConsistentHashRing(listOf("nodeA", "nodeC"), replicas = 1, hashFunction = controlledHash)

        // 링의 상태: {10 -> "nodeA", 30 -> "nodeC"}
        // 해시 15를 가진 키는 시계 방향으로 다음 노드인 "nodeC"에 매핑되어야 합니다.
        assertEquals("nodeC", ring.get("mykey"))

        // 새로운 노드 B를 추가합니다.
        ring.add("nodeB")

        // 노드 추가 후 링 상태: {10 -> "nodeA", 20 -> "nodeB", 30 -> "nodeC"}
        // 이제 해시 15를 가진 키는 새로 추가된 "nodeB"에 매핑되어야 합니다.
        assertEquals("nodeB", ring.get("mykey"))
    }

    @Test
    fun `key is remapped after node removal`() {
        // 테스트의 예측 가능성을 위해 제어된 해시 함수를 사용합니다.
        val controlledHash: (Any) -> Int = { key ->
            when (val s = key.toString()) {
                "nodeA0" -> 10
                "nodeB0" -> 20
                "nodeC0" -> 30
                "mykey"  -> 15
                else     -> s.hashCode()
            }
        }

        // 초기 상태: 노드 A, B, C가 모두 존재
        val ring = ConsistentHashRing(listOf("nodeA", "nodeB", "nodeC"), replicas = 1, hashFunction = controlledHash)

        // 링의 상태: {10 -> A, 20 -> B, 30 -> C}. 해시 15를 가진 키는 "nodeB"에 매핑됩니다.
        assertEquals("nodeB", ring.get("mykey"))

        // 노드 B를 제거합니다.
        ring.remove("nodeB")

        // 노드 제거 후 링 상태: {10 -> A, 30 -> C}. 해시 15를 가진 키는 이제 "nodeC"에 매핑되어야 합니다.
        assertEquals("nodeC", ring.get("mykey"))
    }
}
