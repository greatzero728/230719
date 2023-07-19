import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class TestGen implements Runnable {
	public static void main(String[] args) {
		new Thread(new TestGen()).start();
	}

	PrintWriter out;
	Random rand = new Random(5432256);

	static String getName(int i) {
		return i / 10 + "" + i % 10;
	}

	void open(int TestNum) {
		try {
			System.out.println("Generating test " + TestNum);
			out = new PrintWriter(getName(TestNum));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	void close() {
		out.close();
	}

	BufferedReader br;

	int randInt(int l, int r) {
		if (r <= l) {
			return r;
		}
		return l + rand.nextInt(r - l);
	}

	public void genRandom(int minN, int maxN, int minM, int maxM, int minValue,
			int maxValue) {
		int n = randInt(minN, maxN), m = randInt(minM, maxM), s = randInt(
				minValue, maxValue * 4) / 10;
		out.println(n + " " + m + " " + s);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				out.print(randInt(minValue, maxValue) + " ");
			}
			out.println();
		}
	}

	public void genRandomExist(int minN, int maxN, int minM, int maxM,
			int minValue, int maxValue) {
		int n = randInt(minN, maxN), m = randInt(minM, maxM), s = 0;
		out.print(n + " " + m + " ");
		int a[][] = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = randInt(minValue, maxValue);
			}
		}
		int c1 = randInt(0, m), r1 = randInt(0, n);
		int c2 = randInt(0, m), r2 = randInt(0, n);
		while (c1 == c2) {
			c2 = randInt(0, m);
		}
		while (r2 == r1) {
			r2 = randInt(0, n);
		}
		s = a[r1][c1] + a[r1][c2] + a[r2][c1] + a[r2][c2];
		out.println(s);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				out.print(a[i][j] + " ");
			}
			out.println();
		}
	}

	final int MAX = 300;
	final int MAXVALUE = (int) Math.pow(10, 6);

	public void run() {
		try {
			PrintWriter desc = new PrintWriter(("tests.desc"));
			desc.println("tests 1-2: Hand tests");
			desc.println("tests 3-5: min exist Tests");
			for (int i = 3; i < 6; i++) {// 
				open(i);
				genRandomExist(2, 5, 2, 5, 0, 10);
				close();
			}
			desc.println("tests 5 - 10: small random tests");
			for (int i = 5; i < 11; i++) {// 
				open(i);
				genRandom(2, 10, 2, 10, 0, 20);
				close();
			}
			desc.println("tests 11-21: max tests, solution exists");
			for (int i = 11; i < 21; i++) {// 
				open(i);
				genRandomExist(MAX - 10, MAX, MAX - 10, MAX, MAXVALUE / 10,
						MAXVALUE);
				close();
			}
			// 99% -1
			for (int i = 21; i < 31; i++) {
				open(i);
				genRandom(MAX - 10, MAX, MAX - 10, MAX, 0, MAXVALUE);
				close();
			}
			desc.println("tests 31-50: random tests");
			for (int i = 31; i < 41; i += 2) {
				open(i);
				genRandom(2, 200, 2, 100, 0, 1000);
				close();
				open(i + 1);
				genRandomExist(2, 200, 2, 100, 0, 1000);
				close();
			}
			for (int i = 41; i < 51; i += 2) {
				open(i);
				genRandom(2, MAX, 2, MAX, 0, MAXVALUE);
				close();
				open(i + 1);
				genRandomExist(2, MAX, 2, MAX, 0, MAXVALUE);
				close();
			}
			desc.flush();
		} catch (Throwable e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
