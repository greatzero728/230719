// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: Антон Банных, argentony@gmail.com

import java.io.*;
import java.util.*;
import java.math.*;

public class oil_ab_bug implements Runnable {
	public static void main(String[] args) {
		new Thread(new oil_ab_bug()).start();
	}

	@Override
	public void run() {
		try {
			br = new BufferedReader(new FileReader("oil.in"));
			out = new PrintWriter("oil.out");
			solve();
			out.close();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	BufferedReader br;
	StringTokenizer st;
	PrintWriter out;
	boolean eof = true;

	String cnv(String a) {
		return a.equals("") ? "0" : a;
	}

	String nextToken() {
		while (st == null || !st.hasMoreElements()) {
			try {
				st = new StringTokenizer(br.readLine());
			} catch (Exception e) {
				eof = true;
				return "";
			}
		}
		return st.nextToken();
	}

	int nextInt() {
		return Integer.parseInt(cnv(nextToken()));
	}

	double nextDouble() {
		return Double.parseDouble(cnv(nextToken()));
	}

	final int MAX = 300;
	final int MAX_S = 10000000;
	final int MAX_V = 1000000;

	private void solve() throws IOException {
		int n = nextInt();
		assert 2 <= n && n <= MAX;
		int m = nextInt();
		assert 2 <= m && m <= MAX;
		int s = nextInt();
		assert 0 <= s && s <= MAX_S;

		int[][] tab = new int[n][m];
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < m; ++j) {
				tab[i][j] = nextInt();
				assert 0 <= tab[i][j] && tab[i][j] <= MAX_V;
			}
		}

		int[] a = new int[m];
		for (int i = 0; i < n; ++i) {
			for (int j = i + 1; j < n; ++j) {
				for (int k = 0; k < m; ++k) {
					a[k] = tab[i][k] + tab[j][k];
				}
				Arrays.sort(a);
				int r = m - 1;
				for (int l = 0; l < m && l <= r; ++l) {
					while (r >= 0 && a[l] + a[r] > s)
						--r;
					if (r < 0)
						break;
					if (a[l] + a[r] == s) {
						out.println((i + 1) + " " + (l + 1) + " " + (j + 1)
								+ " " + (r + 1));
						return;
					}
				}
			}
		}
		out.println(-1);
	}

}
