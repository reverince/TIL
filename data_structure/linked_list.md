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
    while(ptr) {
      ++size;
      ptr = ptr->next;
    }
  }

  public:

  LinkedList() : head(NULL), size(0) { }

  unsigned int getSize() { return size; }

  void print() {
    set<Node<T>*> nodeSet;
    Node<T>* ptr = head;

    while(ptr) {
      cout << ptr->datum << ' ';
      // 순환 무한 출력 방지
      if(nodeSet.find(ptr) != nodeSet.end()) {
        cout << "...";
        break;
      }
      nodeSet.insert(ptr);
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
    while(ptr->next) {
      ptr = ptr->next;
    }
    ptr->next = node;
    calcSize();
  }
};
```

* 순환 연결 리스트는 `2 3 5 2 ...`처럼 출력한다.

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
  set<Node<T>*> nodeSet;
  Node<T>* ptr = head;

  while(ptr) {
    if(nodeSet.find(ptr) != nodeSet.end()) return true;
    nodeSet.insert(ptr);
    ptr = ptr->next;
  }
  return false;
}
```

### 순환 시작 노드

```cpp
Node<T>* beginOfLoop() {
  Node<T> *fast = head, *slow = head;
  bool isLoop = false;

  while(fast && slow) {
    slow = slow->next;
    fast = fast->next;
    if(fast == NULL) break;
    fast = fast->next;
    if(fast == NULL) break;

    if(fast == slow) {
      isLoop = true;
      break;
    }
  }
  if(isLoop) {
    slow = head;
    while(fast != slow) {
      slow = slow->next;
      fast = fast->next;
    }
    return slow;
  }
  return NULL;
}
```

### 순환 길이

```cpp
int loopLength() {
  Node<T> *fast = head, *slow = head;
  bool isLoop = false;
  int length = 0;

  while(fast && slow) {
    slow = slow->next;
    fast = fast->next;
    if(fast == NULL) break;
    fast = fast->next;
    if(fast == NULL) break;

    if(fast == slow) {
      isLoop = true;
      break;
    }
  }
  if(isLoop) {
    do {
      fast = fast->next;
      ++length;
    } while(fast != slow);
    return length;
  }
  return 0;
}
```

### 연결 리스트 뒤집기

```cpp
void reverse() {
  if(head == NULL) return;

  set<Node<T>*> nodeSet;
  stack<Node<T>*> nodeStk;
  Node<T>* ptr = head;

  while(ptr) {
    if(nodeSet.find(ptr) != nodeSet.end()) break;
    nodeSet.insert(ptr);
    nodeStk.push(ptr);
    ptr = ptr->next;
  }
  head = nodeStk.top();
  nodeStk.pop();
  ptr = head;
  while(!nodeStk.empty()) {
    ptr->next = nodeStk.top();
    nodeStk.pop();
    ptr = ptr->next;
  }
  if(isCircular()) ptr->next = head;
}
```

* TODO: 순환 리스트 뒤집기 fix

### 테스트

```cpp
int main() {
  vector<Node<int> > vNodes;
  LinkedList<int> myLL;

  for(int i = 0; i < 7; ++i) {
    vNodes.push_back(Node<int>(i));
  }
  for(int i = 0; i < 6; ++i) {
    vNodes[i].next = &vNodes[i+1];
  }
  vNodes[6].next = &vNodes[3];
  myLL.push_back(&vNodes[0]);

  cout << "print() : ";
  myLL.print();
  cout << "getSize() : " << myLL.getSize() << '\n';
  cout << "nThNodeFromBack(2) : " << myLL.nThNodeFromBack(2)->datum << '\n';
  cout << "isCircular() : " << myLL.isCircular() << '\n';
  cout << "print() : ";
  myLL.print();
  cout << "beginOfCycling() : " << myLL.beginOfLoop()->datum << '\n';
  cout << "loopLength() : " << myLL.loopLength() << '\n';
  myLL.reverse();
  cout << "print() after reverse() : ";
  myLL.print();

  return 0;
}
```
```
getSize() : 0
* pushed node a into myLL.
getSize() : 4
nThNodeFromBack(2) : 5
isCircular() : 1
print() : 2 3 5 7 3 ...
beginOfCycling() : 3
print() after reverse() : 7 5 3 2 7 ...
```
