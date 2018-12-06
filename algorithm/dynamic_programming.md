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

## [WILDCARD](https://algospot.com/judge/problem/read/WILDCARD)

* 고려해야 하는 경우
  * 패턴(`w`) 또는 문자열(`s`)의 끝에 도달한 경우
  * 같은 위치에 글자가 다른 경우 : `w[pos] != s[pos]`
  * `*`를 만난 경우
* 모든 문자열의 길이가 100 이하이므로 최대 가짓수는 `101 * 101 = 10201`이다. 비둘기집의 원리에 따라 `101 * 101` 배열에 모든 경우를 저장할 수 있다.

```cpp
#include <iostream>
#include <algorithm>
#include <vector>
#include <cstring>
using namespace std;

const int STR_MAX = 101;
int cache[STR_MAX][STR_MAX];  // 초기값 -1
string W, S;

bool match(int w, int s) {
  int& ret = cache[w][s];
  // 메모이제이션
  if(ret != -1) return ret;
  // 재귀
  if(w < W.size() && s < S.size() && (W[w] == '?' || W[w] == S[s])) {
    return ret = match(w + 1, s + 1);
  }
  // w의 끝에 도달한 경우: s 끝이면 true
  if(w == W.size()) return ret = (s == S.size());
  // *를 만난 경우
  if(W[w] == '*') {
    if(match(w + 1, s) || (s < S.size() && match(w, s + 1))) {
      return ret = true;
    }
  }
  // 그 외 모두 false
  return ret = false;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    int s_cnt;
    vector<string> answers;
    vector<string>::iterator it;

    cin >> W;
    cin >> s_cnt;
    for(int s = 0; s < s_cnt; ++s) {
      memset(cache, -1, sizeof(cache));
      cin >> S;
      if(match(0, 0)) answers.push_back(S);
    }

    sort(answers.begin(), answers.end());
    for(it = answers.begin(); it != answers.end(); ++it) {
      cout << *it << '\n';
    }
  }

  return 0;
}
```

## [TRIANGLEPATH](https://algospot.com/judge/problem/read/TRIANGLEPATH)

```cpp
#include <iostream>
#include <cstring>
using namespace std;

const int N_MAX = 100;
int n, triangle[N_MAX][N_MAX];
int cache[N_MAX][N_MAX];  // 초기값 -1

// (x, y)부터 맨 아래 줄까지 내려가는 최대 경로의 합
int path(int y, int x) {
  // 기저: 맨 아래 줄
  if(y == n - 1) return triangle[y][x];
  // 메모이제이션
  int& ret = cache[y][x];
  if(ret != -1) return ret;
  // 재귀
  return ret = triangle[y][x] + max( path(y+1, x), path(y+1, x+1) );
}

int main() {
  int c;

  cin >> c;
  for(int testCase; testCase < c; ++testCase) {
    memset(cache, -1, sizeof(cache));

    cin >> n;
    for(int j = 0; j < n; ++j) {
      for(int i = 0; i <= j; ++i) {
        cin >> triangle[j][i];
      }
    }
    cout << path(0, 0) << '\n';
  }

  return 0;
}
```

## [LIS](https://algospot.com/judge/problem/read/LIS) (최대 증가 부분 수열)

```cpp
#include <iostream>
#include <cstring>
using namespace std;

const int N_MAX = 500;
int n, seq[N_MAX];
int cache[N_MAX];  // 초기값 -1

// 주어진 인덱스에서 시작하는 수열의
// 최대 증가 부분 수열의 길이를 구하는 함수
int lis(int start) {
  // 메모이제이션
  int& ret = cache[start];
  if(ret != -1) return ret;
  // 무조건 부분 수열 존재
  ret = 1;
  // 재귀
  for(int next = start + 1; next < n; ++next) {
    if(seq[start] < seq[next]) {
      ret = max(ret, lis(next) + 1);
    }
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    memset(cache, -1, sizeof(cache));

    cin >> n;
    for(int i = 0; i < n; ++i) {
      cin >> seq[i];
    }

    int res = lis(0);
    for(int i = 1; i < n; ++i) {
      res = max(res, lis(i));
    }
    cout << res << '\n';
  }

  return 0;
}
```

## [PI](https://algospot.com/judge/problem/read/PI)

```cpp
#include <iostream>
#include <cstdlib>  // abs
#include <cstring>  // memset
using namespace std;

const int INF = 2100000000;
string nums;
int cache[10002];

// numStr[a..b] 구간의 난이도를 계산하는 함수
int classify(int a, int b) {
  string subNums = nums.substr(a, b-a+1);
  // 첫 숫자와 나머지 숫자가 같으면 난이도 1
  // 새 string 객체를 생성해 비교
  if(subNums == string(subNums.size(), subNums[0])) return 1;
  // 등차 수열 여부 검사
  bool progressive = true;
  int diff = subNums[1] - subNums[0];  // 공차
  for(int i = 0; i < subNums.size() - 1; ++i) {
    if(subNums[i+1] - subNums[i] != diff) progressive = false;
  }
  // 등차수열이고 공차가 1 또는 -1이면 난이도 2
  if(progressive && abs(diff) == 1) return 2;
  // 번갈아 나오는 수열 여부 검사
  bool alternating = true;
  for(int i = 0; i < subNums.size(); ++i) {
    if(subNums[i] != subNums[i%2]) alternating = false;
  }
  // 번갈아 나오는 수열이면 난이도 4
  if(alternating) return 4;
  // 등차수열이고 공차가 1 또는 -1이 아니면 난이도 5
  if(progressive) return 5;
  // 그 외의 경우 난이도 10
  return 10;
}

// 수열을 외우는 최소의 난이도를 계산하는 함수
int memorize(int begin) {
  // 기저: 수열의 끝
  if(begin == nums.size()) return 0;
  // 메모이제이션
  int& ret = cache[begin];
  if(ret != -1) return ret;
  // 재귀
  ret = INF;
  for(int len = 3; len <= 5; ++len) {
    int end = begin + len;
    if(end <= nums.size()) {
      ret = min(ret,
                classify(begin, end-1) + memorize(end));
    }
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    memset(cache, -1, sizeof(cache));

    cin >> nums;
    cout << memorize(0) << '\n';
  }

  return 0;
}
```
