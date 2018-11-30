# 동적 계획법

참조 : [프로그래밍 대회에서 배우는 알고리즘 문제 해결 전략](http://book.algospot.com/) - _구종만_

* 메모이제이션은 함수가 동일한 인자에 대해 동일한 값을 반환하는 참조 투명성을 가져야 한다. 즉 순수 함수여야 한다.

## 이항계수

```cpp
#include <iostream>
#include <cstring>
using namespace std;

const int LIMIT = 100;
int cache[LIMIT][LIMIT];

// 이항 계수
int bino(int n, int r) {
  // 기저
  if(r == 0 || n == r) return 1;
  if(cache[n][r] != -1) return cache[n][r];
  // 재귀
  return cache[n][r] = bino(n-1, r-1) + bino(n-1, r);
}

int main() {
  int n, r;
  memset(cache, -1, sizeof(cache));

  cin >> n >> r;
  cout << bino(n, r) << '\n';

  return 0;
}
```
