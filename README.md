# preorder-traversal
A program to read a graphically represented tree and output its preorder traversal.

Input: a text file containing a binary tree.

The tree is expected to satisty the following conditions:

 - Left edges are represented as `/`
 - Right edges are represented as `\`
 - All other non-whitespace character sequences may be considered nodes if they satisty the positional requirements
 - Each node consists of one or more characters
 - First left edge is either directly underneath its parent node or one character to the left of its parent node
 - First right edge is either directly underneath its parent node or one character to the right of its parent node
 - Each left edge is one character to the left of its predecessor
 - Each right edge is one character to the right of its predecessor
 - Last left edge is either directly above its child node or one character to the right of its child node
 - Last right edge is either directly above its child node or one character to the left of its child node
 - The tree is binary

Output: the preorder traversal of the given tree. 