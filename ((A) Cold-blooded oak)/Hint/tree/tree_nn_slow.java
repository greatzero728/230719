import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class tree_nn_slow {

    static int[][] edges;
    static long[] sum;
    static long[] sum2;

    static long dfs0(int v) {
        long ret = sum[v];
        for (int i : edges[v]) {
            ret += dfs0(i);
        }
        return sum2[v] = ret;
    }

    static long dfs(int v) {
        long ret = sum2[v];
        for (int i : edges[v]) {
            ret += dfs(i);
        }
        return ret;
    }

    static void solve() throws IOException {
        int n = nextInt();
        edges = new int[n][];
        int[] deg = new int[n];
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            p[i] = nextInt() - 1;
            deg[p[i]]++;
        }
        for (int i = 0; i < n; i++)
            edges[i] = new int[deg[i]];
        for (int i = 1; i < n; i++) {
            edges[p[i]][--deg[p[i]]] = i;
        }
        int m = nextInt();
        sum = new long[n];
        sum2 = new long[n];
        for (int i = 0; i < m; i++) {
            String type = next();
            int v = nextInt() - 1;
            if (type.equals("ask")) {
                dfs0(v);
                out.println(dfs(v));
            } else {
                int y = nextInt();
                if (type.equals("del")) {
                    y = -y;
                }
                sum[v] += y;
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
