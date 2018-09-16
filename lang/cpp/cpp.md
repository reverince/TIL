# C++


## STL

### 컨테이너 (Containers)

#### 순차 컨테이너 (Sequence Containers)

##### 벡터 (`vector`)

요소가 추가되거나 제거될 때 자동으로 크기가 변하는 동적 배열이다. 배열처럼 연속된 주소에 저장되기 때문에 반복자로 접근할 수 있다.

* `assign(count, value)` : `count`개의 `value`로 벡터를 초기화한다.
  * `assign({...})` : 블록 안의 요소로 벡터를 초기화한다.
* `push_back(value)`, `pop_back()`
* `insert(pos, value)`, `erase(pos)`
* `front()`, `back()`, `size()`, `bool empty()`
* `begin()`, `end()`

##### 리스트 (`list`)

리스트는 배열과 달리 메모리에 분산 저장된다. 따라서 횡단하는 데 오래 걸리지만 주소를 아는 경우 삽입과 삭제가 빠르다. 일반적으로 리스트라 하면 이중 연결 리스트(Doubly Linked List)를 말한다. 단일 연결 리스트(Singly Linked List)에는 `forward_list`를 사용한다.

* `push_front(value)`, `pop_front()`
* `reverse()`, `sort()`

리스트 반복자(`list<T>::iterator`)를 사용해 요소에 접근해야 한다. 마지막 요소는 `--li.end()`임에 유의.

```cpp
void printList(list<int> li) {
  list<int>::iterator it;
  for(it = li.begin(); it != li.end(); ++it) {
    cout << ' ' << *it;
  }
  cout << '\n';
}
```

##### 덱 (`deque`)

양 끝으로 확장 및 축소가 가능한 순차 컨테이너. 벡터에 front 접근을 추가한 형태이나 연속 메모리 할당(Contiguous Memory Allocation)은 보장되지 않는다.

덱 반복자(`deque<T>::iterator`)를 사용해야 한다. 역시 마지막 요소는 `--dq.end()`임에 유의.

##### 포워드 리스트 (`forward_list`)

단일 연결 리스트를 구현한다. 즉 리스트와 달리 다음 요소의 주소만을 추적하기 때문에 이전 요소로 직접 접근할 수 없다.

#### 컨테이너 어댑터 (Container Adaptors)

* 스택 (`stack`)

* 큐 (`queue`)

* 우선순위 큐(`priority_queue`) : 값이 자동으로 정렬되는 큐. 기본적으로 높은 값부터 pop된다.
  * `priority_queue<T, vector<T>, greater<T>>`로 선언하면 작은 값을 우선한다. `vector<T>`는 큐의 구현체.
