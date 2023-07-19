import java.io.*;
import java.util.*;

public class tree_av_sqrt {

	List<Integer>[] graph;
	int[] timeIn, timeOut;
	int[] depth;
	int time = 0;

	void dfs(int u, int p, int d) {
		timeIn[u] = time++;
		depth[u] = d;
		for (int v : graph[u]) {
			if (v != p) {
				dfs(v, u, d + 1);
			}
		}
		timeOut[u] = time;
	}

	public void solve() {
		int n = in.nextInt();
		graph = new List[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		int[] parent = new int[n];
		parent[0] = -1;
		for (int i = 1; i < n; i++) {
			parent[i] = in.nextInt() - 1;
			graph[parent[i]].add(i);
		}
		timeIn = new int[n];
		timeOut = new int[n];
		depth = new int[n];
		dfs(0, -1, 0);

		int q = in.nextInt();
		int[] type = new int[q], vertex = new int[q], howMuch = new int[q];
		for (int i = 0; i < q; i++) {
			String name = in.nextToken();
			vertex[i] = in.nextInt() - 1;
			if (name.equals("add") || name.equals("del")) {
				type[i] = 0;
				howMuch[i] = in.nextInt();
				if (name.equals("del")) {
					howMuch[i] = -howMuch[i];
				}
			} else {
				type[i] = 1;
			}
		}

		int blockSize = (int) (Math.sqrt(q) + 1);
		int blocks = (q + blockSize - 1) / blockSize;

		int[] lampsInVertex = new int[n];
		int[] lampsInSubtree = new int[n];
		long[] totalWiresInSubtree = new long[n];
		for (int i = 0; i < blocks; i++) {
			int l = i * blockSize, r = Math.min(l + blockSize, q);

			for (int j = l; j < r; j++) {
				if (type[j] == 1) {
					int queryVertex = vertex[j];
					long result = totalWiresInSubtree[queryVertex];

					for (int t = l; t < j; t++) {
						if (type[t] == 0) {
							int changedVertex = vertex[t];
							if (isInSubtree(queryVertex, changedVertex)) {
								result += howMuch[t] * (depth[changedVertex] - depth[queryVertex] + 1);
							}
						}
					}
					
					out.println(result);
				}
			}

			for (int j = l; j < r; j++) {
				if (type[j] == 0) {
					lampsInVertex[vertex[j]] += howMuch[j];
				}
			}
			
			Arrays.fill(lampsInSubtree, 0);
			Arrays.fill(totalWiresInSubtree, 0);
			for (int j = n - 1; j > 0; j--) {
				lampsInSubtree[j] += lampsInVertex[j];
				lampsInSubtree[parent[j]] += lampsInSubtree[j];
			}
			
			for (int j = n - 1; j > 0; j--) {
				totalWiresInSubtree[j] += lampsInSubtree[j];
				totalWiresInSubtree[parent[j]] += totalWiresInSubtree[j];
			}
		}
	}

	private boolean isInSubtree(int queryVertex, int changedVertex) {
		return timeIn[queryVertex] <= timeIn[changedVertex] && timeOut[changedVertex] <= timeOut[queryVertex];
	}

	FastScanner in;
	PrintWriter out;

	public void run() {
		try {
			in = new FastScanner("tree.in");
			out = new PrintWriter("tree.out");
			solve();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastScanner(String name) {
			try {
				br = new BufferedReader(new FileReader(name));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		public String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextToken());
		}

		public long nextLong() {
			return Long.parseLong(nextToken());
		}

		public double nextDouble() {
			return Double.parseDouble(nextToken());
		}
	}

	public static void main(String[] args) {
		new tree_av_sqrt().run();
	}
}
