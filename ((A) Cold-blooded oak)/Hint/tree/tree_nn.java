import java.io.*;
import java.util.*;

public class tree_nn {

    static List<Integer>[] edges;
    static int[] entranceTime;
    static int[] exitTime;
    static long[] depth;
    static int currentTime;

    static void dfs(int v, int d) {
        entranceTime[v] = currentTime++;
        depth[v] = d;
        for (int i : edges[v]) {
            dfs(i, d + 1);
        }
        exitTime[v] = currentTime;
    }

    static void solve() throws IOException {
        int n = nextInt();
        edges = new List[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<>();
        }
        entranceTime = new int[n];
        exitTime = new int[n];
        depth = new long[n];
        for (int i = 1; i < n; i++) {
            int x = nextInt() - 1;
            edges[x].add(i);
        }
        currentTime = 0;
        dfs(0, 0);
        BinaryIndexedTree tree = new BinaryIndexedTree(n);
        BinaryIndexedTree tree2 = new BinaryIndexedTree(n);
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            String type = next();
            int v = nextInt() - 1;
            if (type.equals("ask")) {
                long s1 = tree.get(exitTime[v] - 1) - tree.get(entranceTime[v] - 1);
                long s2 = tree2.get(exitTime[v] - 1) - tree2.get(entranceTime[v] - 1);
                out.println(s2 - s1 * (depth[v] - 1));
            } else {
                int y = nextInt();
                if (type.equals("del")) {
                    y = -y;
                }
                tree.add(entranceTime[v], y);
                tree2.add(entranceTime[v], y * depth[v]);
            }
        }
    }

    static class BinaryIndexedTree {
        long[] a;

        BinaryIndexedTree(int n) {
            a = new long[n];
        }

        long get(int x) {
            long ret = 0;
            for (int i = x; i >= 0; i = (i & i + 1) - 1) {
                ret += a[i];
            }
            return ret;
        }

        void add(int x, long y) {
            for (int i = x; i < a.length; i |= i + 1) {
                a[i] += y;
            }
        }
    }

    static StringTokenizer tokenizer;
    static BufferedReader reader;
    static PrintWriter out;

    public static void main(String[] args) throws IOException {
        reader = new BufferedReader(new FileReader("tree.in"));
        out = new PrintWriter("tree.out");
        solve();
        out.close();
        reader.close();
    }

    static String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            String line = reader.readLine();
            if (line == null) {
                return null;
            }
            tokenizer = new StringTokenizer(line);
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt(next());
    }
}
