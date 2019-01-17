# 탐욕 알고리즘 (Greedy Algorithm)

참조 : [프로그래밍 대회에서 배우는 알고리즘 문제 해결 전략](http://book.algospot.com/) - _구종만_

- 전체 선택지를 고려하는 완전 탐색이나 동적 계획법과 달리 각 단계마다 당장의 최적해를 선택한다.

## [회의실배정](https://www.acmicpc.net/problem/1931)

- 종료시간이 가장 빠른 회의부터 선택한다.
  - 종료시간이 가장 빠른 회의 M₁ 대신 임의의 다른 회의 M₂를 선택하면, 나머지 회의를 최적으로 배정해도 M₂를 제거하고 M₁을 선택할 수 있다. 그러면 두 회의의 종료시간 차이만큼의 이익이 발생한다.

```cpp
#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

const int N_MAX = 100000;
int n;
// 각 회의는 [from, to) 동안 회의실 사용
int from[N_MAX], to[N_MAX];

// 회의실에 배정할 수 있는 최대 회의의 수를 구하는 함수
int schedule() {
  // 종료 시간 순 정렬
  vector<pair<int, int> > order;
  for(int i = 0; i < n; ++i) {
    order.push_back(make_pair(to[i], from[i]));
  }
  sort(order.begin(), order.end());
  // 다음 회의가 시작할 수 있는 가장 이른 시간
  int earliest = 0;
  // 지금까지 선택한 회의 수
  int ret = 0;
  // 회의 선택
  for(int i = 0; i < order.size(); ++i) {
    int meetingBegin = order[i].second;
    int meetingEnd = order[i].first;
    if(earliest <= meetingBegin) {
      earliest = meetingEnd;
      ++ret;
    }
  }
  return ret;
}

int main() {
  cin >> n;
  for(int i = 0; i < n; ++i) {
    cin >> from[i] >> to[i];
  }

  cout << schedule() << '\n';

  return 0;
}
```

## [MATCHORDER](https://algospot.com/judge/problem/read/MATCHORDER)

- 이길 수 있다면 이길 수 있는 선수 중 가장 점수가 낮은 선수를 선택한다.
- 이길 수 없다면 남은 선수 중 가장 점수가 낮은 선수를 선택한다.

```cpp
#include <iostream>
#include <set>
#include <vector>
using namespace std;

int order(const vector<int>& russian, const vector<int>& korean) {
  int n = russian.size(), ret = 0;
  // 남은 선수 레이팅 순 정렬
  multiset<int> ratings(korean.begin(), korean.end());
  for(int rus = 0; rus < n; ++rus) {
    // 이길 수 있는 선수 없음
    if(*ratings.rbegin() < russian[rus]) {
      // 최소 레이팅 선수 선택
      ratings.erase(ratings.begin());
    }
    // 이길 수 있는 최소 레이팅 선수 선택
    else {
      ratings.erase(ratings.lower_bound(russian[rus]));
      ++ret;
    }
  }
  return ret;
}

int main() {
  int c;

  cin >> c;
  for(int testCase = 0; testCase < c; ++testCase) {
    int n, rating;
    vector<int> russian, korean;

    cin >> n;
    for(int i = 0; i < n; ++i) {
      cin >> rating;
      russian.push_back(rating);
    }
    for(int i = 0; i < n; ++i) {
      cin >> rating;
      korean.push_back(rating);
    }

    cout << order(russian, korean) << '\n';
  }

  return 0;
}
```

## [LUNCHBOX](https://algospot.com/judge/problem/read/LUNCHBOX)

- 한 도시락을 먹는 데 걸리는 시간은 (지금까지 도시락을 데운 시간의 합) + (이 도시락을 먹는 시간)
- 도시락을 먹는 시간이 모두 E로 같다면, 도시락을 어떤 순서로 데우든 점심 시간의 길이는 (모든 도시락을 데우는 시간) + (마지막 도시락을 먹는 시간)
- 데우는 시간과 상관없이 먹는 데 오래 걸리는 도시락부터 데운다.

```cpp
#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

const int N_MAX = 10000;
int n, m[N_MAX], e[N_MAX];

// 최소 점심 시간을 계산하는 함수
int heat() {
  // 도시락 데우는 순서 결정
  vector<pair<int, int> > order;
  for(int i = 0; i < n; ++i) {
    // 음수로 넣어 먹는 시간 내림차순 정렬
    order.push_back(make_pair(-e[i], i));
  }
  sort(order.begin(), order.end());
  int ret = 0, beginEat = 0;
  for(int i = 0; i < n; ++i) {
    // 먹는 시간이 긴 도시락부터 선택
    int box = order[i].second;
    // 먹기 시작하는 시간 계산
    beginEat += m[box];
    // 가장 먹는 데 오래 걸리는 도시락 시간 저장
    ret = max(ret, beginEat + e[box]);
  }
  return ret;
}

int main() {
  int t;

  cin >> t;
  for(int testCase = 0; testCase < t; ++testCase) {
    cin >> n;
    for(int i = 0; i < n; ++i)
      cin >> m[i];
    for(int i = 0; i < n; ++i)
      cin >> e[i];

    cout << heat() << '\n';
  }

  return 0;
}
```
