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
	
	int n = 100000;
	int m = n - 1;
	cout<<n<<' '<<m<<endl;
	for(int i = 1; i < n; i++) cout<<i<<' '<<i + 1<<endl;
	return 0;
}
