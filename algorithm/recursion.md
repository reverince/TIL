# 재귀

- 자기 자신을 반복 호출하는 모든 함수
- 정렬, 검색, 탐색 문제 해결

```cpp
int fact(int n) {
  // 기저 조건
  if (n==0 || n==1) return 1;
  // 재귀 조건
  return n * fact(n-1);
}
```

### 재귀 vs 반복

- 일반적으로 함수 호출 오버헤드가 있는 재귀보다 반복이 성능 우세
- 재귀로 해결할 수 있는 문제는 반복으로도 해결 가능

##### 재귀
- 기저 조건에 도달하면 종료
- 각 재귀 호출은 스택 프레임상 추가 공간 요구
- 무한 재귀 시 메모리 부족으로 스택 오버플로우 발생

##### 반복
- 조건이 거짓일 때 종료
- 추가 공간 불필요
- 무한 루프 가능성 존재

### 재귀 예시

- 피보나치, 팩토리얼, 하노이 탑 문제
- 병합 정렬, 퀵 정렬
- 이진 검색
- 트리 탐색(전위·중위·후위), 그래프 탐색(DFS·BFS)
- 동적 프로그래밍
- 분할 정복
- 역추적 알고리즘

### 하노이 탑 문제

```cpp
#include <iostream>
using namespace std;

void move(int n, char from, char to) {
  cout << n << "번 원반을 " << from << "에서 " <<
    to << "로\n";
}

void hanoi(int n, char from, char aux, char to) {
  // 기저: 원반이 하나면 바로 이동
  if (n==1) {
    move(1, from, to);
    return;
  }
  // n-1개 원반을 A에서 C를 거쳐 B로 이동
  hanoi(n-1, from, to, aux);
  // n번 원반 이동
  move(n, from, to);
  // n-1개 원반을 B에서 A를 거쳐 C로 이동
  hanoi(n-1, aux, from, to);
}

int main() {
  hanoi(3, 'A', 'B', 'C');
  cout << "이동하면 끝이에요.\n";
}
```

```
1번 원반을 A에서 C로
2번 원반을 A에서 B로
1번 원반을 C에서 B로
3번 원반을 A에서 C로
1번 원반을 B에서 A로
2번 원반을 B에서 C로
1번 원반을 A에서 C로
이동하면 끝이에요.
```
