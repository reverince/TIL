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

## [PICNIC](https://algospot.com/judge/problem/read/PICNIC)

* `(0, 1)`과 `(1, 0)`은 같은 쌍이므로 중복해서 세지 않아야 한다.
* `[(0, 1), (2, 3)]`과 `[(2, 3), (0, 1)]`은 같은 경우이므로 중복해서 세지 않아야 한다.
* 가장 번호가 빠른 학생부터 검사하는 것으로 해결할 수 있다.

```cpp
#include <iostream>
#include <cstring>
using namespace std;

const int MAX_STUDENTS = 10;
int n;
bool areFriends[MAX_STUDENTS][MAX_STUDENTS];

// 모든 학생을 친구끼리 짝짓는 방법의 수를 구하는 함수
int countPair(bool taken[MAX_STUDENTS]) {
  // 짝을 찾지 못한 첫 번째 학생 탐색
  int free = -1;
  for(int i = 0; i < n; ++i) {
    if(!taken[i]) {
      free = i;
      break;
    }
  }
  // 기저: 모두 짝을 찾은 경우
  if(free == -1) return 1;
  // 이하 재귀
  int ret = 0;
  for(int with = free + 1; with < n; ++with) {
    // 아직 짝이 없으며 친구인 상대 탐색
    if(!taken[with] && areFriends[free][with]) {
      // 짝을 짓는 경우 조합의 수
      taken[free] = taken[with] = true;
      ret += countPair(taken);
      // 짝을 취소하고 다음 상대로 진행
      taken[free] = taken[with] = false;
    }
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    int m;
    bool taken[MAX_STUDENTS] = { false, };
    // 배열 초기화
    memset(areFriends, false, sizeof(areFriends));

    cin >> n >> m;
    for(int i = 0; i < m; ++i) {
      int s1, s2;
      cin >> s1 >> s2;
      areFriends[s1][s2] = areFriends[s2][s1] = true;
    }

    cout << countPair(taken) << '\n';
  }

  return 0;
}
```

## [BOARDCOVER](https://algospot.com/judge/problem/read/BOARDCOVER)

```cpp
#include <iostream>
#include <vector>
using namespace std;

const int TYPE_CNT = 4;
const int BLOCK_SIZE = 3;
const int COVER_TYPE[TYPE_CNT][BLOCK_SIZE][2] = {
  { {0, 0}, {0, 1}, {1, 0} },  // ┏
  { {0, 0}, {0, 1}, {1, 1} },  // ┓
  { {0, 0}, {1, 0}, {1, 1} },  // ┗
  { {0, 0}, {1, 0}, {1, -1} }  // ┛
};
int h, w;

bool inRange(int y, int x) {
  if(y >= 0 && y < h && x >= 0 && x < w) return true;
  else return false;
}

// 게임판에 블록을 덮거나(delta=1) 치우는(delta=-1) 함수
// 성공하면 true, 실패하면 false 반환
bool set(vector<vector<int> >& board, int y, int x, int type, int delta) {
  bool ret = true;
  for(int i = 0; i < BLOCK_SIZE; ++i) {
    const int ny = y + COVER_TYPE[type][i][0];
    const int nx = x + COVER_TYPE[type][i][1];
    if(!inRange(ny, nx)) ret = false;
    else {
      board[ny][nx] += delta;
      if(board[ny][nx] > 1) ret = false;
    }
  }
  return ret;
}

// 게임판의 모든 흰 칸을 덮는 방법의 수를 구하는 함수
int cover(vector<vector<int> >& board) {
  // 아직 덮지 않은 가장 왼쪽 위 흰 칸 탐색
  int y = -1, x = -1;
  for(int j = 0; j < board.size(); ++j) {
    for(int i = 0; i < board[j].size(); ++i) {
      if(board[j][i] == 0) {
        y = j;
        x = i;
        break;
      }
    }
    if(y != -1) break;
  }
  // 기저: 게임판을 다 덮은 경우 1 반환
  if(y == -1) return 1;
  // 이하 재귀
  int ret = 0;
  for(int type = 0; type < TYPE_CNT; ++type) {
    // 해당 블록 타입으로 덮는 경우 방법의 수 계산
    if(set(board, y, x, type, 1)) ret += cover(board);
    // 덮은 블록을 제거하고 다음 타입으로 진행
    set(board, y, x, type, -1);
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    cin >> h >> w;
    // 입력 받은 크기의 게임판 생성
    vector<vector<int> > board(h, vector<int>(w));
    // 게임판 초기화
    for(int j = 0; j < h; ++j) {
      for(int i = 0; i < w; ++i) {
        char ch;
        cin >> ch;
        board[j][i] = (ch == '.') ? 0 : 1;
      }
    }

    cout << cover(board) << '\n';
  }

  return 0;
}
```

## [CLOCKSYNC](https://algospot.com/judge/problem/read/CLOCKSYNC)

```cpp
#include <iostream>
#include <vector>
using namespace std;

const int PUSH_LIMIT = 31;  // SWITCH_CNT * 3 + 1
const int SWITCH_CNT = 10, CLOCK_CNT = 16;
const bool LINKED[SWITCH_CNT][CLOCK_CNT] = {
  {1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0},
  {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1},
  {1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0},
  {1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
  {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
  {0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1},
  {0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
  {0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0}
};

// 모든 시계가 12시를 가리키는지 확인하는 함수
bool areAligned(vector<int> clocks) {
  vector<int>::iterator it;
  for(it = clocks.begin(); it != clocks.end(); ++it) {
    if(*it != 12) return false;
  }
  return true;
}

// 스위치를 누르는 함수
void push(vector<int>& clocks, int swtch) {
  for(int clock = 0; clock < CLOCK_CNT; ++clock) {
    if(LINKED[swtch][clock]) {
      clocks[clock] += 3;
      if(clocks[clock] == 15) clocks[clock] = 3;
    }
  }
}

// 모든 시계를 12시로 돌려놓기 위해 눌러야 할 스위치의 최소 수를 구하는 함수
int fewestPush(vector<int>& clocks, int swtch) {
  // 모든 스위치를 누른 후 정렬 여부 검사
  if(swtch == SWITCH_CNT) return areAligned(clocks) ? 0 : PUSH_LIMIT;
  int ret = PUSH_LIMIT;
  // 스위치를 0~3번 누르는 경우 각각 시도
  for(int cnt = 0; cnt < 4; ++cnt) {
    ret = min(ret, cnt + fewestPush(clocks, swtch + 1));
    push(clocks, swtch);
    // 스위치를 4번 눌렀으므로 연결된 시계는 원래 상태
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    vector<int> clocks;
    int res;

    for(int i = 0; i < CLOCK_CNT; ++i) {
      int time;
      cin >> time;
      clocks.push_back(time);
    }

    res = fewestPush(clocks, 0);
    cout << ((res < PUSH_LIMIT) ? res : -1) << '\n';
  }

  return 0;
}
```
