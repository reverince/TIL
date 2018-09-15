# C++

## STL

### 컨테이너 (Containers)

#### 벡터 (Vector)

요소가 추가되거나 제거될 때 자동으로 크기가 변하는 동적 배열이다. 배열처럼 연속된 주소에 저장되기 때문에 반복자로 접근할 수 있다.

* `assign(count, value)` : `count`개의 `value`로 벡터를 초기화한다.
* `push_back(value)`, `pop_back()`
* `insert(pos, value)`, `erase(pos)`
* `front()`, `back()`, `size()`, `bool empty()`
* `begin()`, `end()`

#### 리스트 (List)

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
