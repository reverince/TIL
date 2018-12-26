# 연결 리스트

* 요소 확장이 _O_(1)의 시간복잡도를 가진다.
* 저장공간을 동적으로 할당한다.
* 순차 접근이 _O_(n)의 시간복잡도를 가진다.
* 참조 포인터로 인한 오버헤드가 발생한다.

## vs 배열

* 요소 접근이 _O_(1)의 시간복잡도를 가진다.
* 구조가 간단하고 사용이 쉽다.
* 크기가 고정되어 있다.
* 위치 지정 삽입이 복잡하다.

|  | 연결 리스트 | 배열 | 동적 배열 |
|-:|:-----------:|:----:|:---------:|
|         인덱싱 | _O_(n) | _O_(1) | _O_(1) |
|     front 삽입 | _O_(1) | _O_(n) | _O_(n) |
|      back 삽입 | _O_(n) | _O_(1) | _O_(1) * |
|      back 삭제 | _O_(n) | _O_(1) | _O_(n) |
| 중간 삽입/삭제 | _O_(n) | _O_(n) | _O_(n) |
|      공간 낭비 | _O_(n) | ≒0 | _O_(n) |

\* 배열이 가득 찬 경우 _O_(n)

## 구현

```cpp
#include <iostream>
using namespace std;

template <typename T>
struct Node {
  T datum;
  Node* next;

  public:

  Node(T t) : datum(t), next(NULL) { }
};

template <typename T>
class LinkedList {
  Node<T>* head;
  unsigned int size;

  void calcSize() {
    Node<T>* ptr = head;
    size = 0;
    while(ptr != NULL) {
      ++size;
      ptr = ptr->next;
    }
  }

  public:

  LinkedList() : head(NULL), size(0) { }

  unsigned int getSize() { return size; }

  void print() {
    Node<T>* ptr = head;
    while(ptr != NULL) {
      cout << ptr->datum << ' ';
      ptr = ptr->next;
    }
    cout << '\n';
  }

  void push_back(Node<T>* node) {
    if(head == NULL) {
      head = node;
      // 여러 노드를 추가할 수 있으므로 길이 계산 필요
      calcSize();
      return;
    }
    Node<T>* ptr = head;
    while(ptr->next != NULL) {
      ptr = ptr->next;
    }
    ptr->next = node;
    calcSize();
  }
};

int main() {
  Node<int> a(2), b(3);
  a.next = &b;
  LinkedList<int> myLL;

  cout << myLL.getSize() << '\n';
  myLL.push_back(&a);
  cout << myLL.getSize() << '\n';
  myLL.print();

  return 0;
}
```

### 뒤에서 n번째 노드 찾기

```cpp
Node<T>* nThNodeFromBack(int n) {
  Node<T>* ptr = head;
  int toTravel = size - n;

  if(n <= 0 || n > size) return NULL;
  while(toTravel > 0) {
    if(ptr == NULL) return NULL;
    ptr = ptr->next;
    --toTravel;
  }
  return ptr;
}
```

### 환형 연결 리스트 여부 검사

```cpp
#include <set>
...
bool isCircular() {
  set<Node<T>* > nodes;
  Node<T>* ptr = head;
  while(ptr != NULL) {
    if(nodes.find(ptr) != nodes.end()) return true;
    nodes.insert(ptr);
    ptr = ptr->next;
  }
  return false;
}
```
