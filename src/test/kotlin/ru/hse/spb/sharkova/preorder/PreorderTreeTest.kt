package ru.hse.spb.sharkova.preorder

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import java.io.File

class PreorderTreeTest {
    // tree from the problem statement
    @Test
    fun testStatementTree() {
        checkForFile(
            "statement.txt",
            listOf("fooooo", "k", "ab", "o", "pam", "bar", "baz", "z", "i", "w", "m", "abcde")
        )
    }

    @Test
    fun testBambooLeft() {
        checkForFile("bambooLeft.txt", listOf("node1", "node2", "node3", "node4"))
    }

    @Test
    fun testBambooRight() {
        checkForFile("bambooRight.txt", listOf("node1", "node2", "node3", "node4"))
    }

    @Test
    fun testEmpty() {
        checkForFile("empty.txt", emptyList())
    }

    @Test
    fun testSingular() {
        checkForFile("singular.txt", listOf("node"))
    }

    @Test
    fun testFullBinaryTree() {
        checkForFile("fullBinaryTree.txt", listOf("1234", "123", "4556", "text1", "text2", "q", "p"))
    }

    @Test
    fun testCompleteBinaryTree() {
        checkForFile(
            "completeBinaryTree.txt",
            listOf("1_2_3_4", "_abc", "de", "r", "s", "fg", "1", "2", "cba_", "q", "4", "p")
        )
    }

    @Test
    fun testAllEdgesDirectlyUnderneath() {
        checkForFile(
            "allEdgesDirectlyUnderneath.txt",
            listOf("longnodetext", "moderate", "q", "p", "nodetext", "short", "text", "2", "3")
        )
    }

    @Test
    fun testAllEdgesShiftOne() {
        checkForFile(
            "allEdgesShiftOne.txt",
            listOf("longnodetext", "moderate", "q", "p", "nodetext", "short", "text", "2", "3")
        )
    }

    @Test
    fun testArbitraryNodeCharacters() {
        checkForFile(
            "arbitraryNodeCharacters.txt",
            listOf("fdshjafgdshf", "FHG", "_", "5843", "!!!!!", "@%\$^", "ТЕКст", "---", "a+b+1\"")
        )
    }

    @Test
    fun testLongNodes() {
        checkForFile(
            "longNodes.txt",
            listOf(
                "texttexttexttexttexttexttexttexttexttexttexttext",
                "123123123123123123",
                "p",
                "r",
                "txttxttxttx",
                "123",
                "33",
                "text123text123",
                "q",
                "text",
                "t",
                "a",
                "texttexttext",
                "456"
            )
        )
    }

    // even though this tree is incorrectly formed, all no-end-node edges are simply disregarded
    // an exception can be added if necessary
    @Test
    fun testNoNodeUnderLeftEdge() {
        checkForFile("noNodeUnderLeftEdge.txt", listOf("fooooo", "k", "bar", "baz", "z", "i", "w", "m", "abcde"))
    }

    // when the tree is malformed (position of slashes does not correspond to the statement; extra spacing)
    // the malformed branch is ignored
    @Test
    fun testMalformedTree() {
        checkForFile(
            "malformed.txt",
            listOf("fooooo", "bar", "baz", "z", "i", "w")
        )
    }

    private fun checkForFile(filename: String, expected: List<String>) {
        val classLoader = javaClass.classLoader
        val filepath = classLoader.getResource(filename)?.file
        if (filepath == null) {
            fail()
            return
        }
        val file = File(filepath)
        val traversal = preorderTraversalFromFile(file)
        assertEquals(expected, traversal)
    }
}