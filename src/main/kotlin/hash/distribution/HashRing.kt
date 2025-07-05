package hash.distribution

/**
 * 해시 링은 분산 시스템에서 노드의 추가 및 제거 시 데이터 재매핑을 최소화하는
 * Consistent Hashing(일관성 해싱)을 구현하는 데 사용되는 데이터 구조입니다.
 *
 * @param N 링에 저장될 노드(서버 등)의 타입
 */
interface HashRing<N> {
    /**
     * 링에 새로운 노드를 추가합니다.
     *
     * @param node 추가할 노드
     */
    fun add(node: N)

    /**
     * 링에서 기존 노드를 제거합니다.
     *
     * @param node 제거할 노드
     */
    fun remove(node: N)

    /**
     * 주어진 키가 매핑되는 노드를 찾습니다.
     *
     * @param key 찾고자 하는 데이터의 키
     * @return 키에 해당하는 노드. 링이 비어있으면 null을 반환합니다.
     */
    fun get(key: Any): N?
}
