// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: Сергей Поромов, poromov@rain.ifmo.ru

import java.io.*;
import java.util.*;

public class oil_sp implements Runnable {

	public static void main(String[] args) {
		new Thread(new oil_sp()).run();
	}

	public void run() {
		try {
			Locale.setDefault(Locale.US);
			br = new BufferedReader(new FileReader(FILENAME + ".in"));
			out = new PrintWriter(FILENAME + ".out");
			solve();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	BufferedReader br;

	PrintWriter out;

	StringTokenizer st;

	String nextToken() throws IOException {
		while (st == null || !st.hasMoreTokens()) {
			st = new StringTokenizer(br.readLine());
		}
		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	void myAssert(boolean e, String s) {
		if (!e) {
			throw new Error("Assertion failure " + s);
		}
	}

	private static final String FILENAME = "oil";
	
	int MAXN = 300;
	int MAXS = 10000000;
	int MAX = 1000000;

	public void solve() throws IOException {
		int n = nextInt();
		myAssert((2 <= n) && (n <= MAXN), "Wrong n");
		int m = nextInt();
		myAssert((2 <= m) && (m <= MAXN), "Wrong m");
		int s = nextInt();
		myAssert((0 <= s) && (s <= MAXS), "Wrong s");
		int[][] a = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = nextInt();
				myAssert((0 <= a[i][j]) && (a[i][j] <= MAX), "Wrong a[" + i + "][" + j + "]");
			}
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
				for (int k = 0; k < m; k++) {
					if (hm.containsKey(s - a[i][k] - a[j][k])) {
						int l = hm.get(s - a[i][k] - a[j][k]);
						out.println((i + 1) + " " + (l + 1) + " " + (j + 1) + " " + (k + 1));
						return;
					}
					hm.put(a[i][k] + a[j][k], k);
				}
			}
		}
		out.println(-1);
	}
}
