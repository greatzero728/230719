// Задача "Добыча нефти"
// Шестая Интернет-олимпиада сезона 2009-2010, усложненный уровень
// Автор задачи: Виталий Аксенов, aksenov@rain.ifmo.ru
// Автор решения: Антон Банных, argentony@gmail.com

#include <cstdio>
#include <algorithm>

const int MAX = 300;
const int MAX_S = 10000000;
const int MAX_V = 1000000;

int tab[MAX][MAX], a[MAX];

int main()
{
	freopen("oil.in", "rt", stdin);
	freopen("oil.out", "wt", stdout);
	int n, m, s;
	scanf("%d %d %d", &n, &m, &s);

	for (int i = 0; i < n; ++i) {
		for (int j = 0; j < m; ++j) {
			scanf("%d", &tab[i][j]);
		}
	}

	for (int i = 0; i < n; ++i) {
		for (int j = i + 1; j < n; ++j) {
			for (int k = 0; k < m; ++k) {
				a[k] = tab[i][k] + tab[j][k];
			}
			std::sort(a, a + m);
			int r = m - 1;
			for (int l = 0; l < m && l < r; ++l) {
				while (r >= 0 && a[l] + a[r] > s)
					--r;
				if (r <= l)
					break;
				if (a[l] + a[r] == s) {
					
					int ll = -1;
					for (int k = 0; k < m; ++k) {
						if (tab[i][k] + tab[j][k] == a[l]) {
							ll = k;
							break;
						}
					}
					int rr = -1;
					for (int k = 0; k < m; ++k) {
						if (k == ll) continue;
						if (tab[i][k] + tab[j][k] == a[r]) {
							rr = k;
							break;
						}
					}
					
					printf("%d %d %d %d", i + 1, ll + 1, j + 1, rr + 1);
					return 0;
				}
			}
		}
	}
	printf("-1\n");
	return 0;
}

	

