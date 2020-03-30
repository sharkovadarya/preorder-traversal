package ru.hse.spb.sharkova.preorder

import java.io.File
import java.util.*
import java.util.regex.Pattern

private fun isToTheLeft(s: Pair<Int, Int>, t: Pair<Int, Int>): Boolean = s.second <= t.second

private fun isToTheRight(s: Pair<Int, Int>, t: Pair<Int, Int>): Boolean = s.first >= t.first

private fun isInside(s: Pair<Int, Int>, t: Pair<Int, Int>): Boolean = isToTheLeft(
    s,
    t
) && isToTheRight(s, t)

private val charactersPattern = Pattern.compile("[^\\\\/\\s]+")
private val slashesPattern = Pattern.compile("[\\\\/]")

/**
 * A function to read a binary tree from a file and do a preorder traversal of that tree.
 * @param file file containing the binary tree (edges represented as / and \, arbitrary characters for nodes)
 * @return a list of nodes in the preorder traversal of the tree
 * */
fun preorderTraversalFromFile(file: File): List<String> {
    val traversal = mutableListOf<String>()
    // assuming the file is not that large since the problem statement says 'read from file or string'
    val lines = file.readLines()
    if (lines.isEmpty()) {
        return emptyList()
    }

    // triple: line number, segment start, segment end
    val stack = LinkedList<Triple<Int, Int, Int>>()

    val firstLineMatcher = charactersPattern.matcher(lines[0])
    if (firstLineMatcher.find()) {
        stack.push(Triple(0, firstLineMatcher.start(), firstLineMatcher.end()))
    } else {
        return emptyList()
    }

    while (stack.isNotEmpty()) {
        val (line, start, end) = stack.pop()
        val currentText = lines[line].substring(start, end)
        if (currentText == "/") {
            // it's assumed the tree is always correct and each edge is succeeded by either a left edge or a node
            // option 1: next line has another left edge
            if (lines[line + 1].substring(start - 1, end - 1) == "/") {
                stack.push(Triple(line + 1, start - 1, end - 1))
            } else { // option 2: next line has a node
                val matcher = charactersPattern.matcher(lines[line + 1])
                while (matcher.find()) {
                    if (matcher.start() <= start - 1 && matcher.end() == end - 1 ||
                        isInside(
                            start to end,
                            matcher.start() to matcher.end()
                        )
                    ) {
                        stack.push(Triple(line + 1, matcher.start(), matcher.end()))
                        break
                    }
                }
            }
        } else if (currentText == "\\") {
            // option 1: next line has another right edge
            if (lines[line + 1].substring(start + 1, end + 1) == "\\") {
                stack.push(Triple(line + 1, start + 1, end + 1))
            } else { // option 2: next line has a node
                val matcher = charactersPattern.matcher(lines[line + 1])
                while (matcher.find()) {
                    if (matcher.start() == start + 1 && matcher.end() >= end + 1 ||
                        isInside(
                            start to end,
                            matcher.start() to matcher.end()
                        )
                    ) {
                        stack.push(Triple(line + 1, matcher.start(), matcher.end()))
                        break
                    }
                }
            }

        } else {
            traversal.add(currentText)
            // if there is any text on the next line, we are currently interested in / and \ only
            if (line + 1 < lines.size) {
                val matcher = slashesPattern.matcher(lines[line + 1])
                // first save all occurrences so that it's possible to push right node first, left node second
                val occurrences = mutableListOf<Pair<Int, Int>>()
                while (matcher.find()) {
                    if ((matcher.group() == "\\" &&
                                (isInside(
                                    matcher.start() to matcher.end(),
                                    start to end
                                ) ||
                                        (matcher.start() == end && matcher.end() == end + 1))) ||
                        (matcher.group() == "/" &&
                                (isInside(
                                    matcher.start() to matcher.end(),
                                    start to end
                                ) ||
                                        (matcher.start() == start - 1 && matcher.end() == start)))
                    ) {
                        occurrences.add(matcher.start() to matcher.end())
                    }
                }
                // there should be 0, 1 or 2 occurrences, starting with the left one
                val rightOccurrence = occurrences.find { lines[line + 1].substring(it.first, it.second) == "\\" }
                val leftOccurrence = occurrences.find { lines[line + 1].substring(it.first, it.second) == "/" }
                if (rightOccurrence != null) {
                    stack.push(Triple(line + 1, rightOccurrence.first, rightOccurrence.second))
                }
                if (leftOccurrence != null) {
                    stack.push(Triple(line + 1, leftOccurrence.first, leftOccurrence.second))
                }
            }
        }
    }

    return traversal
}


fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        val file = File(args[0])
        if (!file.isFile) {
            println("Invalid file provided.")
            return
        }
        val traversal = preorderTraversalFromFile(file)
        traversal.forEach { print("$it ") }
        println()
    } else {
        println("Please provide the file name as command line argument.")
    }

}