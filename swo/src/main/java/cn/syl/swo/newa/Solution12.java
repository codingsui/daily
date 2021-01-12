package cn.syl.swo.newa;

/**
 * 剑指 Offer 12. 矩阵中的路径
 *
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，
 * 每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。
 * 例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
 *
 *
 * [["a","b","c","e"],
 * ["s","f","c","s"],
 * ["a","d","e","e"]]
 *
 * 但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
 * 示例 1：
 *
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * 输出：true
 *
 */
public class Solution12 {

    public boolean exist(char[][] board, String word) {
        if (board == null || word == null || word.length() == 0){
            return false;
        }
        int row = board.length;
        int column = board[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (dfs(board,word.toCharArray(),i,j,0)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, char[] words, int row, int column, int k) {
        if (row >= board.length || column >= board[0].length || k >= words.length || row < 0 || column < 0 || words[k] != board[row][column]){
            return false;
        }
        if (k == words.length - 1){
            return true;
        }
        char tmp = board[row][column];
        board[row][column] = '/';
        int current = k + 1;
        boolean res = dfs(board,words,row+1,column,current) || dfs(board,words,row,column + 1,current) ||
                dfs(board,words,row-1,column,current) || dfs(board,words,row,column - 1,current);
        board[row][column] = tmp;
        return res;
    }
}
