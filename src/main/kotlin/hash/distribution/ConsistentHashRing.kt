package hash.distribution

import java.util.SortedMap
import java.util.TreeMap

/**
 * Consistent Hashing(일관성 해싱)을 구현한 해시 링입니다.
 *
 * 이 구현체는 데이터(키)를 여러 노드(서버)에 분산할 때, 노드가 추가되거나 제거되어도
 * 전체 키의 재매핑을 최소화하여 시스템의 안정성을 높입니다.
 *
 * @param V 링에 저장될 노드의 타입
 * @param nodes 초기 노드들의 컬렉션
 * @param replicas 하나의 물리적 노드를 링에 몇 개의 가상 노드로 복제할 것인지 결정하는 값. 높을수록 키가 더 균등하게 분산됩니다.
 * @param hashFunction 키를 해시값(정수)으로 변환하는 함수. 기본값은 `Any.hashCode()` 입니다.
 */
class ConsistentHashRing<V>(
    nodes: Collection<V>,
    private val replicas: Int = 100,
    private val hashFunction: (Any) -> Int = { it.hashCode() }
) : HashRing<V> {

    // 해시 링을 표현하는 SortedMap. 해시값을 키로, 노드를 값으로 저장하여 정렬된 상태를 유지합니다.
    private val ring: SortedMap<Int, V> = TreeMap()

    init {
        // 초기 노드들을 링에 추가합니다.
        nodes.forEach { add(it) }
    }

    /**
     * 링에 새로운 노드를 추가합니다.
     * 각 노드는 `replicas` 수만큼의 가상 노드로 복제되어 링에 분산 저장됩니다.
     */
    override fun add(node: V) {
        for (i in 0 until replicas) {
            // 노드 이름과 인덱스를 조합하여 고유한 가상 노드 키를 생성하고 해시합니다.
            ring[hash(node.toString() + i)] = node
        }
    }

    /**
     * 링에서 노드와 그에 해당하는 모든 가상 노드를 제거합니다.
     */
    override fun remove(node: V) {
        for (i in 0 until replicas) {
            ring.remove(hash(node.toString() + i))
        }
    }

    /**
     * 주어진 키가 매핑될 노드를 찾습니다.
     * 키의 해시값보다 크거나 같은 해시값을 가진 노드 중 가장 가까운 노드를 시계 방향으로 찾습니다.
     */
    override fun get(key: Any): V? {
        if (ring.isEmpty()) {
            return null
        }

        val hash = hash(key)

        // --- Consistent Hashing의 핵심 로직 ---
        // 목표: 링 위에서 키의 해시 위치로부터 시계 방향으로 가장 가까운 노드를 찾는다.

        // 1. `tailMap(hash)` 호출
        //    - 정렬된 `ring` 맵에서 `hash` 값보다 크거나 같은 모든 노드(키-값 쌍)를 찾는다.
        //    - 이 결과는 "키의 위치에서부터 시계 방향으로 있는 모든 노드들의 목록"과 같다.
        val tailMap = ring.tailMap(hash)

        val nodeHash = if (tailMap.isEmpty()) {
            // 2. `tailMap`이 비어있는 경우 (엣지 케이스)
            //    - 키의 해시가 링의 모든 노드 해시보다 클 때 발생한다.
            //    - 링은 원형이므로, 이 경우 링의 가장 첫 번째 노드가 담당 노드가 된다.
            ring.firstKey()
        } else {
            // 3. `tailMap`에 노드가 있는 경우
            //    - `tailMap`은 이미 해시값으로 정렬되어 있으므로,
            //    - `firstKey()`를 호출하면 그 중 가장 작은 해시값, 즉 가장 가까운 노드의 해시를 얻을 수 있다.
            tailMap.firstKey()
        }
        return ring[nodeHash]
    }

    // 내부적으로 사용하는 해시 함수
    private fun hash(key: Any): Int {
        return hashFunction(key)
    }
}
