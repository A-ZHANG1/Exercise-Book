import java.util.Arrays;

/**
 * Created by Wayne.A.Z on 2020-08-20.
 */
class TrieNode{
    boolean isEnd;
    int occ;
    TrieNode[] next;
    public TrieNode(){
        isEnd = false;
        occ = 0;
        next = new TrieNode[26];
    }
}
class Trie {
    private TrieNode root;
    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }

    public void insert(String key, int val) {
        TrieNode node = root;
        for(char c: key.toCharArray()){
            int ch = c - 'a';
            if(node.next[ch] == null){
                node.next[ch] = new TrieNode();
            }
            node = node.next[ch];
        }
        if(node.isEnd == true) node.occ = val;
        else{
            node.isEnd = true;
            node.occ += val;
        }
        return;
    }

    public int sum(String prefix) {
        res = 0;
        TrieNode node = root;
        for(char c: prefix.toCharArray()){
            int ch = c - 'a';
//            System.out.println(Arrays.toString(node.next));
            node = node.next[ch];
            if(node == null) return res;

        }
        dfs(node);

        return res;
    }
    int res;
    public void dfs(TrieNode node){
        if(node == null) return;
        if(node.isEnd == true) {
            res += node.occ;
        }
        for(int i = 0;i < 26;i ++){
            dfs(node.next[i]);
        }
        return;
    }

    public static void main(String args[]){
        Trie tr = new Trie();
//        tr.insert("apple", 3);
//        System.out.println(tr.sum("ap"));
//        tr.insert("app", 2);
//        System.out.println(tr.sum("ap"));
        tr.insert("aa", 3);
        System.out.println(tr.sum("a"));
        tr.insert("aa", 2);
        System.out.println(tr.sum("a"));

        System.out.println(tr.sum("aa"));
        tr.insert("aaa", 3);
        System.out.println(tr.sum("aaa"));
        System.out.println(tr.sum("bbb"));

    }
}