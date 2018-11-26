# 완전 탐색

참조 : [프로그래밍 대회에서 배우는 알고리즘 문제 해결 전략](http://book.algospot.com/) - _구종만_

## nCk (조합)

```cpp
#include <iostream>
#include <vector>
using namespace std;

// 벡터를 출력하는 함수
void printVector(vector<int> v) {
  vector<int>::iterator it;
  for(it = v.begin(); it != v.end(); ++it) {
    cout << ' ' << *it;
  }
  cout << '\n';
}

void pick(int n, vector<int>& vPicked, int toPick) {
  // 기저: 더 고를 원소가 없을 경우
  if(toPick == 0) { printVector(vPicked); return;}
  // 고를 수 있는 가장 작은 수 선택
  int smallest = vPicked.empty() ? 0 : vPicked.back() + 1;
  // 나머지 수로 조합 생성
  for(int next = smallest; next < n; ++next) {
    vPicked.push_back(next);
    pick(n, vPicked, toPick-1);
    vPicked.pop_back();
  }
}

int main() {
  int n, k;
  vector<int> vPicked;

  cin >> n >> k;
  pick(n, vPicked, k);
  return 0;
}
```

## [BOGGLE](https://algospot.com/judge/problem/read/BOGGLE)

```cpp
#include <iostream>
using namespace std;

const int MAX_HEIGHT = 5;
const int MAX_WIDTH = 5;
const int dy[8] = { -1, -1, -1,  0, 0,  1, 1, 1 };
const int dx[8] = { -1,  0,  1, -1, 1, -1, 0, 1 };
char board[MAX_HEIGHT][MAX_WIDTH];

bool inRange(int y, int x) {
  if(y >= 0 && y < MAX_HEIGHT && x >= 0 && x < MAX_WIDTH)
    return true;
  return false;
}

bool hasWord(int y, int x, string word) {
  // 기저 1: 범위를 벗어난 경우 거짓
  if(!inRange(y, x)) return false;
  // 기저 2: 첫 글자가 일치하지 않는 경우 거짓
  if(board[y][x] != word[0]) return false;
  // 기저 3: 찾는 단어가 한 글자인 경우 참
  if(word.size() == 1) return true;
  // 재귀: 인접한 8칸 검사
  for(int dir = 0; dir < 8; ++dir) {
    int nextY = y + dy[dir], nextX = x + dx[dir];
    if(hasWord(nextY, nextX, word.substr(1))) return true;
  }
  return false;
}

int main() {
  int c, n;
  bool yes;
  string words[10];

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    for(int h = 0; h < MAX_HEIGHT; ++h) {
      for(int w = 0; w < MAX_WIDTH; ++w) {
        cin >> board[h][w];
      }
    }
    cin >> n;
    for(int i = 0; i < n; ++i) {
      cin >> words[i];
    }

    for(int i = 0; i < n; ++i) {
      cout << words[i] << ' ';
      yes = false;
      for(int h = 0; h < MAX_HEIGHT; ++h) {
        for(int w = 0; w < MAX_WIDTH; ++w) {
          if(hasWord(h, w, words[i])) yes = true;
        }
      }
      cout << ((yes) ? "YES" : "NO");
      cout << '\n';
    }
  }

  return 0;
}
```

* 기저 조건에서 유효성을 검사하기 때문에 for문에서 별도로 확인할 필요가 없다.
* 시간복잡도 O(8^n). 문제를 풀 수는 있지만 속도가 느려서 개선이 필요하다.
