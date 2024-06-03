package tree.binary

interface BinaryTree {
    val root: BinaryTreeNode

    fun search(value: Int): Boolean

    /**
     * @return 전위 순회 결과
     */
    fun preorderTraversal(): List<Int>
    /**
     * @return 중위 순회 결과
     */
    fun inorderTraversal(): List<Int>
    /**
     * @return 후위 순회 결과
     */
    fun postorderTraversal(): List<Int>

    /**
     * @return 최대값
     */
    fun max(): Int
    /**
     * @return 최소값
     */
    fun min(): Int

}

interface MutableBinaryTree : BinaryTree {
    fun insert(value: Int)
    fun delete(value: Int)

}

class BinaryTreeNode(val value: Int) {
    var left: BinaryTreeNode? = null
    var right: BinaryTreeNode? = null
}
