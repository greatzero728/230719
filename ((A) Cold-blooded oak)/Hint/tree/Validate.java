import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Validate {

    static final int MAXN = 200_000;
    static final int MAXQ = 200_000;
    static final int MAXY = 10_000;

    public void run() {
        StrictScanner inf = new StrictScanner(System.in);
        int n = inf.nextInt();
        ensureLimits(n, 1, MAXN, "incorrect n");
        inf.nextLine();
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            p[i] = inf.nextInt();
            ensureLimits(p[i], 1, n, "p[" + (i + 1) + "]");
            --p[i];
            ensure(p[i] != i, "p[i] != i, but p[" + (i + 1) + "] = " + (p[i] + 1));
        }
        int[] was = new int[n];
        Arrays.fill(was, 0);
        was[0] = 2;
        for (int i = 1; i < n; i++) {
            if (was[i] == 2) {
                continue;
            }
            int v = i;
            while (was[v] == 0) {
                was[v] = 1;
                v = p[v];
            }
            ensure(was[v] == 2, "not a tree in input");
            v = i;
            while (was[v] == 1) {
                was[v] = 2;
                v = p[v];
            }
        }
        inf.nextLine();
        int q = inf.nextInt();
        ensureLimits(q, 1, MAXQ, "q");
        inf.nextLine();
        int[] has = new int[n];
        for (int i = 0; i < q; i++) {
            String type = inf.next();
            ensure(type.equals("ask") || type.equals("add") || type.equals("del"), "incorrect query type: " + type);
            int v = inf.nextInt();
            ensureLimits(v, 2, n, "query #" + (1 + i) + ", v = " + v + ", ");
            if (type.equals("ask")) {

            } else {
                int y = inf.nextInt();
                if (type.equals("add")) {
                    ensureLimits(y, 1, MAXY, "query #" + (i + 1) + ", y = " + y + ", ");
                    has[v - 1] += y;
                } else {
                    ensureLimits(y, 1, has[v - 1], "query #" + (i + 1) + ", y = " + y + ", ");
                    has[v - 1] -= y;
                }
            }
            inf.nextLine();
        }
        inf.close();
    }

    public static void main(String[] args) {
        new Validate().run();
    }

    public class StrictScanner {
        private final BufferedReader in;
        private String line = "";
        private int pos;
        private int lineNo;

        public StrictScanner(InputStream source) {
            in = new BufferedReader(new InputStreamReader(source));
            nextLine();
        }

        public void close() {
            ensure(line == null, "Extra data at the end of file");
            try {
                in.close();
            } catch (IOException e) {
                throw new AssertionError("Failed to close with " + e);
            }
        }

        public void nextLine() {
            ensure(line != null, "EOF");
            ensure(pos == line.length(), "Extra characters on line " + lineNo);
            try {
                line = in.readLine();
            } catch (IOException e) {
                throw new AssertionError("Failed to read line with " + e);
            }
            pos = 0;
            lineNo++;
        }

        public String getNextLine() {
            String st = "";
            while (!isEoln()) {
                st = st + next();
            }
            return st;
        }


        public boolean isEoln() {
            return (pos == line.length());
        }

        public String next() {
            ensure(line != null, "EOF");
            ensure(line.length() > 0, "Empty line " + lineNo);
            if (pos == 0)
                ensure(line.charAt(0) > ' ', "Line " + lineNo
                        + " starts with whitespace");
            else {
                ensure(pos < line.length(), "Line " + lineNo + " is over");
                ensure(line.charAt(pos) == ' ', "Wrong whitespace on line "
                        + lineNo);
                pos++;
                ensure(pos < line.length(), "Line " + lineNo + " is over");
                ensure(line.charAt(0) > ' ', "Line " + lineNo
                        + " has double whitespace");
            }
            StringBuilder sb = new StringBuilder();
            while (pos < line.length() && line.charAt(pos) > ' ')
                sb.append(line.charAt(pos++));
            return sb.toString();
        }

        public int nextInt() {
            String s = next();
            ensure(s.length() == 1 || s.charAt(0) != '0',
                    "Extra leading zero in number " + s + " on line " + lineNo);
            ensure(s.charAt(0) != '+', "Extra leading '+' in number " + s
                    + " on line " + lineNo);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line "
                        + lineNo);
            }
        }

        public long nextLong() {
            String s = next();
            ensure(s.length() == 1 || s.charAt(0) != '0',
                    "Extra leading zero in number " + s + " on line " + lineNo);
            ensure(s.charAt(0) != '+', "Extra leading '+' in number " + s
                    + " on line " + lineNo);
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line "
                        + lineNo);
            }
        }

        public double nextDouble() {
            String s = next();
            ensure(s.length() == 1 || s.startsWith("0.") || s.charAt(0) != '0',
                    "Extra leading zero in number " + s + " on line " + lineNo);
            ensure(s.charAt(0) != '+', "Extra leading '+' in number " + s
                    + " on line " + lineNo);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                throw new AssertionError("Malformed number " + s + " on line "
                        + lineNo);
            }
        }
    }

    public static class Geometry {
        /**
         * Distance between two points (x1; y1) and (x2; y2)
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         * @return
         */
        public static double dist(int x1, int y1, int x2, int y2) {
            long x3 = x1;
            long y3 = y1;
            long x4 = x2;
            long y4 = y2;
            return Math.sqrt((x3 - x4) * (x3 - x4) + (y3 - y4) * (y3 - y4));
        }
    }

    public static class Graph {
        /**
         * Adjacency matrix
         * 0 or -1 if edge is empty
         * other positive int if not
         * @param e
         * @return
         */
        private static boolean dfsCheckForTree(int k, int p, boolean[] f, int[][] e) {
            f[k] = true;
            for (int i = 0; i < f.length; i++) {
                if (e[k][i] > 0 && !f[i]) {
                    dfsCheckForTree(i, k, f, e);
                } else {
                    if (e[k][i] > 0 && f[i] && i != p) {
                        return false;
                    }
                }
            }
            return true;
        }

        public static boolean isTree(int[][] e) {
            for (int i = 0; i < e.length; i++) {
                ensure(e[i].length == e.length, "Bad adjacency matrix given to class Graph");
            }
            ensure(e.length != 0, "Empty adjacency matrix given to class Graph");
            boolean[] visited = new boolean[e.length];
            if (!dfsCheckForTree(0, -1, visited, e)) ;
            boolean ans = true;
            for (int i = 0; i < e.length; i++) {
                ans &= visited[i];
            }
            return ans;
        }
    }

    static void ensure(boolean b, String message) {
        if (!b) {
            throw new AssertionError(message);
        }
    }

    void ensureString(String s, String alph, String text) {
        for (int i = 0; i < s.length(); i++) {
            ensure(alph.indexOf(s.charAt(i)) != -1, text);
        }
    }

    void ensureLimits(long n, long from, long to, String name) {
        ensure(from <= n && n <= to, name + " must be from " + from + " to "
                + to);
    }

    void ensureLimits(int n, int from, int to, String name) {
        ensure(from <= n && n <= to, name + " must be from " + from + " to "
                + to);
    }

    String line(int x) {
        return "Problem in line #" + Integer.toString(x);
    }

}

