import java.util.*

class TreeNode<T>(val content: T) {
    var left: TreeNode<T>? = null
    var right: TreeNode<T>? = null
}

fun preorderTraversal(root: TreeNode<String>): List<String> {
    // iterative traversal
    val stack = LinkedList<TreeNode<String>>()
    stack.push(root)
    val traversal = mutableListOf<String>()
    while (stack.isNotEmpty()) {
        val node = stack.pop()
        if (node.content != "/" && node.content != "\\") {
            traversal.add(node.content)
        }
        if (node.right != null) {
            stack.push(node.right)
        }
        if (node.left != null) {
            stack.push(node.left)
        }
    }
    return traversal
}
