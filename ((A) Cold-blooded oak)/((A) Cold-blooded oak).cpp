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
bool gn(char &c) {while(c = gc(), ~c && isspace(c)); return ~c;}
bool gn(string &s) {
	char c; while(c = gc(), ~c && isspace(c)); if(!~c) return 0;
	for(s = c; c = gc(), ~c && !isspace(c); s += c); return 1;
}

const int NN = 202020;

vector<int> edges[NN];
int currentTime, n;
int entranceTime[NN], exitTime[NN];
INT depth[NN];

INT a[2][NN];

INT get(int id, int x) {
	INT ans = 0;
	for(int i = x; i >= 0; i = (i & i + 1) - 1) ans += a[id][i];
	return ans;
}
void add(int id, int x, INT y) {
	for(int i = x; i < n; i |= i + 1) {
		a[id][i] += y;
	}
}

void dfs(int u, int d) {
	entranceTime[u] = currentTime++;
	depth[u] = d;
	for(int i : edges[u]) dfs(i, d + 1);
	exitTime[u] = currentTime;
}

int main() {
#ifndef ONLINE_JUDGE
	freopen("in.txt", "r", stdin);
	freopen("out.txt", "w", stdout);
#else
	freopen("tree.in", "r", stdin);
	freopen("tree.out", "w", stdout);
#endif
	
	gn(n);
	for(int i = 1; i < n; i++) {
		int x; gn(x);
		edges[x - 1].push_back(i);
	}
	
	currentTime = 0;
	dfs(0, 0);
	
	int m; gn(m);
	while(m--) {
		string s; gn(s);
		int v; gn(v), v--;
		if(s == "ask") {
			INT s1 = get(0, exitTime[v] - 1) - get(0, entranceTime[v] - 1);
			INT s2 = get(1, exitTime[v] - 1) - get(1, entranceTime[v] - 1);
			pt(s2 - s1 * (depth[v] - 1)), pc(10);
		}
		else {
			int y; gn(y);
			if(s == "del") y = -y;
			add(0, entranceTime[v], y);
			add(1, entranceTime[v], y * depth[v]);
		}
	}
	return 0;
}