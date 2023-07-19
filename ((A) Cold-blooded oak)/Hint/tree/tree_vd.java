import java.io.*;
import java.util.*;

public class tree_vd {

	ArrayList<Integer>[] edges;
	int[] l;
	int[] r;
	long[] h;
	int time = 0;

	void dfs(int v, int curH) {
		l[v] = r[v] = time;
		h[v] = curH;
		time++;
		for (int i = 0; i < edges[v].size(); i++) {
			dfs(edges[v].get(i), curH + 1);
			r[v] = r[edges[v].get(i)];
		}
	}

	public void solve() throws IOException {
		int n = nextInt();
		edges = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<>();
		}
		for (int i = 0; i < n - 1; i++) {
			int par = nextInt() - 1;
			edges[par].add(i + 1);
		}
		l = new int[n];
		r = new int[n];
		h = new long[n];
		dfs(0, 0);

	
		SumTree tree1 = new SumTree(n);
		SumTree tree2 = new SumTree(n);
		int q = nextInt();
		for (int i = 0; i < q; i++) {
			String s = next();
			if (s.equals("add")) {
				int v = nextInt() - 1;
				long val = nextLong();
				tree1.update(l[v], val);
				tree2.update(l[v], val * h[v]);
			} else if (s.equals("del")) {
				int v = nextInt() - 1;
				long val = nextLong();
				tree1.update(l[v], -val);
				tree2.update(l[v], -val * h[v]);
			} else {
				int v = nextInt() - 1;
				out.println(tree2.sum(l[v], r[v]) - tree1.sum(l[v], r[v])
						* (h[v] - 1));
			}
		}
	}

	public class SumTree {
		int n;
		long[] sum;

		public SumTree(int n) {
			this.n = n;
			sum = new long[4 * n + 1];
		}

		void update(int index, long value) {
			update(0, n - 1, 1, index, value);
		}

		void update(int l, int r, int i, int pos, long val) {
			if (l == r) {
				sum[i] += val;
				return;
			}
			if (pos <= (l + r) / 2) {
				update(l, (l + r) / 2, i * 2, pos, val);
			} else {
				update((l + r) / 2 + 1, r, i * 2 + 1, pos, val);
			}
			sum[i] = sum[i * 2] + sum[i * 2 + 1];
		}

		long sum(int l, int r) {
			return sum(0, n - 1, l, r, 1);
		}

		long sum(int l, int r, int l1, int r1, int i) {
			if (l == l1 && r == r1) {
				return sum[i];
			}
			int mid = (l + r) / 2;
			if (r1 <= mid) {
				return sum(l, mid, l1, r1, i * 2);
			}
			if (l1 > mid) {
				return sum(mid + 1, r, l1, r1, i * 2 + 1);
			}
			return sum(l, mid, l1, mid, i * 2)
					+ sum(mid + 1, r, mid + 1, r1, i * 2 + 1);

		}

	}

	final String fileName = "tree_vd_".split("_")[0];

	public void run() throws IOException {
		br = new BufferedReader(new FileReader(fileName + ".in"));
		out = new PrintWriter(fileName + ".out");
		solve();
		out.close();
		br.close();
	}

	public static void main(String[] args) throws IOException {
		new tree_vd().run();
	}

	BufferedReader br;
	StringTokenizer str;
	PrintWriter out;

	String next() throws IOException {
		while (str == null || !str.hasMoreTokens()) {
			str = new StringTokenizer(br.readLine());
		}
		return str.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

}
