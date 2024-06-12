package tree.binary

interface BinaryTreeNode<T> {
    val value: T

    var left: BinaryTreeNode<T>?

    var right: BinaryTreeNode<T>?

    fun search(value: Int): Boolean

    /**
     * @return 전위 순회 결과
     */
    fun preorderTraversal(): List<T>
    /**
     * @return 중위 순회 결과
     */
    fun inorderTraversal(): List<T>
    /**
     * @return 후위 순회 결과
     */
    fun postorderTraversal(): List<T>

//    /**
//     * @return 최대값
//     */
//    fun max(): Int
//    /**
//     * @return 최소값
//     */
//    fun min(): Int

}

interface MutableBinaryTreeNode<T> : BinaryTreeNode<T> {
    fun insert(value: Int)

    fun delete(value: Int)

}


