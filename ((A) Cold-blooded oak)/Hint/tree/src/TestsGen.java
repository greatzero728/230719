import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class TestsGen {

    static final Random RNG = new Random(1029384756);
    static String TEST_FOLDER_PATH = "./";
    static final int MAX_ADD = 10000;
    static final int MAX_VERTICES = 200000;
    static final int MAX_QUERIES = 200000;


    static class Test {
        int[] p;
        Query[] queries;

        Test(int[] p, Query[] queries) {
            this.p = p;
            this.queries = queries;
        }
    }

    static int testsCount;


    static void printTest(Test test) {
        System.err.println(++testsCount);
        try {
            PrintWriter out = new PrintWriter(TEST_FOLDER_PATH + String.format("%02d", testsCount));
            int n = test.p.length;
            out.println(n);
            for (int i = 1; i < n; i++) {
                if (i > 1) out.print(' ');
                out.print(test.p[i] + 1);
            }
            out.println();
            int m = test.queries.length;
            out.println(m);
            for (Query e : test.queries) {
                out.println(e.getQueryLine());
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static abstract class Query {
        abstract String getQueryLine();
    }

    static class AddQuery extends Query {
        int vertex;
        int value;

        AddQuery(int vertex, int value) {
            this.vertex = vertex;
            this.value = value;
        }

        @Override
        String getQueryLine() {
            return "add " + (vertex + 1) + " " + value;
        }
    }

    static class DelQuery extends Query {
        int vertex;
        int value;

        DelQuery(int vertex, int value) {
            this.vertex = vertex;
            this.value = value;
        }

        @Override
        String getQueryLine() {
            return "del " + (vertex + 1) + " " + value;
        }
    }

    static class AskQuery extends Query {
        int vertex;

        AskQuery(int v) {
            this.vertex = v;
        }

        @Override
        String getQueryLine() {
            return "ask " + (vertex + 1);
        }
    }

    static int[] shuffleVertices(int[] a) {
        int n = a.length;
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            int t = RNG.nextInt(i) + 1;
            p[i] = p[t];
            p[t] = i;
        }
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[p[i]] = p[a[i]];
        }
        return ret;
    }

    static int[] genBamboo(int n) {
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            p[i] = i - 1;
        }
        return shuffleVertices(p);
    }

    static int[] genBinaryTree(int n) {
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            p[i] = (i - 1) / 2;
        }
        return shuffleVertices(p);
    }

    static int[] genWideTree(int n) {
        final int CO = RNG.nextInt(10) + 1;
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            if (i <= CO) {
                p[i] = RNG.nextInt(i);
            } else {
                p[i] = RNG.nextInt(CO);
            }
        }
        return shuffleVertices(p);
    }

    static int[] genHighTree(int n) {
        final int CO = RNG.nextInt(10) + 1;
        int[] p = new int[n];
        for (int i = 1; i < n; i++) {
            if (i <= CO) {
                p[i] = RNG.nextInt(i);
            } else {
                p[i] = i - RNG.nextInt(CO) - 1;
            }
        }
        return shuffleVertices(p);
    }

    static int[] genRandomTree(int n) {
        int[] ret = new int[n];
        for (int i = 1; i < n; i++) {
            ret[i] = RNG.nextInt(i);
        }
        return shuffleVertices(ret);
    }

    static Query[] genRandomQueries(int n, int m, int p1, int p2, int p3) {
        Query[] ret = new Query[m];
        int[] current = new int[n];
        for (int i = 0; i < m; i++) {
            int type = RNG.nextInt(p1 + p2 + p3);
            int v = RNG.nextInt(n - 1) + 1;
            if (type < p1) {
                int y = RNG.nextInt(MAX_ADD) + 1;
                current[v] += y;
                ret[i] = new AddQuery(v, y);
            } else if (type < p1 + p2) {
                if (current[v] == 0) {
                    --i;
                    continue;
                }
                int y = RNG.nextInt(current[v]) + 1;
                ret[i] = new DelQuery(v, y);
                current[v] -= y;
            } else {
                ret[i] = new AskQuery(v);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("usage: java TestsGen <path>");
            return;
        }
        TEST_FOLDER_PATH = args[0];
        // sample tests
        printTest(new Test(new int[]{0, 0}, new Query[]{new AddQuery(1, 1), new AskQuery(1), new DelQuery(1, 1), new AskQuery(1)}));
        printTest(new Test(new int[]{0, 0, 1, 1, 0}, new Query[]{new AddQuery(2, 4), new AskQuery(1), new AddQuery(3, 5),
                new AskQuery(1), new DelQuery(2, 1), new AskQuery(1), new DelQuery(3, 3), new AddQuery(4, 1), new AskQuery(4), new DelQuery(4, 1), new AskQuery(4), new AskQuery(1)}));
        //
        for (int v : new int[]{5, 10, 20, 40, 50}) {
            for (int[] p : new int[][]{{1, 1, 1}, {100, 100, 1}, {1, 1, 200}}) {
                int n = v;
                printTest(new Test(genRandomTree(n), genRandomQueries(n, MAX_QUERIES, p[0], p[1], p[2])));
            }
        }
        // max tests
        for (int[] p : new int[][]{{1, 1, 1}, {100, 100, 1}, {1, 1, 200}, {1000 , 0, 1}}) {
            printTest(new Test(genBamboo(MAX_VERTICES), genRandomQueries(MAX_VERTICES, MAX_QUERIES, p[0], p[1], p[2])));
            printTest(new Test(genRandomTree(MAX_VERTICES), genRandomQueries(MAX_VERTICES, MAX_QUERIES, p[0], p[1], p[2])));
            printTest(new Test(genBinaryTree(MAX_VERTICES), genRandomQueries(MAX_VERTICES, MAX_QUERIES, p[0], p[1], p[2])));
            printTest(new Test(genWideTree(MAX_VERTICES), genRandomQueries(MAX_VERTICES, MAX_QUERIES, p[0], p[1], p[2])));
            printTest(new Test(genHighTree(MAX_VERTICES), genRandomQueries(MAX_VERTICES, MAX_QUERIES, p[0], p[1], p[2])));
        }
        for (int i = 0; i < 5; i++) {
            for (int[] p : new int[][]{{1, 1, 1}, {100, 100, 1}, {1, 1, 200}}) {
                int n = RNG.nextInt(MAX_VERTICES - 4) + 5;
                printTest(new Test(genRandomTree(n), genRandomQueries(n, MAX_QUERIES, p[0], p[1], p[2])));
            }
        }
    }
}
