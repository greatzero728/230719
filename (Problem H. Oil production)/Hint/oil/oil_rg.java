// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: Рустам Ганеев, ganeev@rain.ifmo.ru

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class oil_rg {
	public static void main(String[] args) throws IOException {
		new oil_rg().run();
	}

	PrintWriter out;
	StringTokenizer in;
	BufferedReader br;
	boolean eof = false;

	String file = "oil";

	void run() throws IOException {
		br = new BufferedReader(new FileReader(file + ".in"));
		out = new PrintWriter(file + ".out");
		solve();
		out.flush();
	}

	String nextToken() throws IOException {
		try {
			while (in == null || !in.hasMoreTokens()) {
				in = new StringTokenizer(br.readLine());
			}
			return in.nextToken();
		} catch (Exception e) {
			eof = true;
			return "";
		}
	}

	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}

	void printNum(int x1, int y1, int x2, int y2) {
		out.print((x1 + 1) + " " + (y1 + 1) + " " + (x2 + 1) + " " + (y2 + 1));
	}

	final int MAX = 300;
	final int MAXSUM = (int) Math.pow(10, 7);
	final int MAXNUM = (int) Math.pow(10, 6);

	void myAssert(boolean expr, String mess) {
		if (!expr) {
			throw new Error(mess);
		}
	}

	void solve() throws IOException {
		int n = nextInt(), m = nextInt(), s = nextInt();
		myAssert(1 < n && n <= MAX, "Wrong n value");
		myAssert(1 < m && m <= MAX, "Wrong m value");
		myAssert(0 <= s && m <= MAXSUM, "Wrong sum value");
		int a[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = nextInt();
				myAssert(0 <= a[i][j] && a[i][j] <= MAXNUM, "Wrong value at "
						+ Integer.toString(i + 1) + " "
						+ Integer.toString(j + 1) + " position");
			}
		}
		int sums[] = new int[MAXNUM * 4];
		int used[] = new int[MAX];
		int cnt = 0;
		Arrays.fill(sums, -1);
		for (int j = 0; j < m - 1; j++) {
			for (int k = j + 1; k < m; k++) {
				for (int i = 0; i < cnt; i++) {
					sums[used[i]] = -1;
				}
				cnt = 0;
				for (int i = 0; i < n; i++) {
					int sum = a[i][j] + a[i][k];
					if (sum > s)
						continue;
					if (sums[s - sum] != -1) {
						out.print((sums[s - sum] + 1) + " " + (j + 1) + " " + (i + 1) +  " " + (k + 1));
						return;
					} else {
						sums[sum] = i;
						used[cnt++] = sum;
					}
				}
			}
		}
		out.print(-1);
	}
}
