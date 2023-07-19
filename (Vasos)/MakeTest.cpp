#define rnd (((((INT)rand() << 15) | rand()) << 15) | rand())
#include<bits/stdc++.h>
#define y second
#define x first

using namespace std;
using INT = long long;
using pii = pair<int, int>;

int main() {
	freopen("in.txt", "w", stdout);
	
	srand(time(0));
	int n = 1000;
	cout<<n<<endl;
	for(int i = 1; i < n; i++) cout<<rnd % 100 + 1<<' '; puts("");
	int q = 1000, f = 1;
	cout<<q<<' '<<f<<endl;
	while(q--) {
		cout<<rnd % 10000 + 1<<' '<<rnd % min(n, 10) + 1<<endl;
	}
	return 0;
}
