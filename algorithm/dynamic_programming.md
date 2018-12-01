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

## [JUMPGAME](https://algospot.com/judge/problem/read/JUMPGAME)

```cpp
#include <iostream>
#include <cstring>
using namespace std;

const int N_MAX = 100;
int n, board[N_MAX][N_MAX];
// 메모이제이션을 위한 배열, -1로 초기화
int cache[N_MAX][N_MAX];

int jump(int y, int x) {
  // 기저 1: 범위를 벗어난 경우 0
  if(y >= n || x >= n) return 0;
  // 기저 2: 도착한 경우 1
  if(y == n - 1 && x == n - 1) return 1;
  // 메모이제이션 확인
  int& ret = cache[y][x];
  if(ret != -1) return ret;
  // 재귀: 오른쪽과 아래로 각각 점프
  int jumpSize = board[y][x];
  return ret = ( jump(y + jumpSize, x) || jump(y, x + jumpSize) );
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    // cache 초기화
    memset(cache, -1, sizeof(cache));

    cin >> n;
    for(int j = 0; j < n; ++j) {
      for(int i = 0; i < n; ++i) {
        cin >> board[j][i];
      }
    }

    cout << ( jump(0, 0) ? "YES" : "NO" ) << '\n';
  }

  return 0;
}
```
