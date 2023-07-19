#include<bits/stdc++.h>
#define y second
#define x first

using namespace std;
using INT = long long;
using pii = pair<int, int>;

#define pc _putchar_nolock
static char fjf[1010], so[44];
void pt(INT x) {
	int n = 0;
	while(so[n++] = x % 10, x /= 10);
	while(n--) pc(so[n] + 48);
}
char gc() {
	static char *L = fjf, *R = L;
	if(L == R) {
		int n = fread(fjf, 1, 1010, stdin);
		if(!n) return -1; L = fjf, R = L + n;
	}
	return *L++;
}
bool gn(int &x) {
	char c; while(c = gc(), (c < 48 || 57 < c) && ~c);
	for(x = c - 48; c = gc(), 48 <= c && c <= 57; x = 10 * x + c - 48);
	return 1;
}

const int NN = 101010, MM = NN << 1;

int Ls[MM], xmn[MM], ymn[MM], x[NN], y[NN];
int Rs[MM], xmx[MM], ymx[MM], id[NN], nc;

#define mid (L + R >> 1)

int build(int L, int R, bool f = 0) {
	int u = nc++;
	if(R - L < 2) {
		xmn[u] = xmx[u] = x[id[L]], ymn[u] = ymx[u] = y[id[L]];
		return u;
	}
	if(f) nth_element(id + L, id + mid, id + R, [&](int u, int v) {return x[u] < x[v];});
	else nth_element(id + L, id + mid, id + R, [&](int u, int v) {return y[u] < y[v];});
	
	int ls = Ls[u] = build(L, mid, f ^ 1), rs = Rs[u] = build(mid, R, f ^ 1);
	xmn[u] = min(xmn[ls], xmn[rs]), xmx[u] = max(xmx[ls], xmx[rs]);
	ymn[u] = min(ymn[ls], ymn[rs]), ymx[u] = max(ymx[ls], ymx[rs]);
	return u;
}

int z[NN][5];

int main() {
#ifndef ONLINE_JUDGE
	freopen("in.txt", "r", stdin);
//	freopen("out.txt", "w", stdout);
#else
	freopen(".in", "r", stdin);
	freopen(".out", "w", stdout);
#endif
	
	int n; gn(n);
	for(int i = 1; i <= n; i++) {
		gn(x[i]), gn(y[i]), id[i] = i;
		for(int j = 1; j <= 4; j++) gn(z[i][j]);
	}
	build(1, n + 1);
	return 0;
}