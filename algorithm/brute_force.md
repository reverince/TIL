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
