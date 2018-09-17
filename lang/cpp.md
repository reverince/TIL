# C++


## STL

### 컨테이너 (Containers)

[Microsoft 설명서](https://msdn.microsoft.com/ko-kr/library/1fe2x6kt.aspx)

#### 순차 컨테이너 (Sequence Containers)

요소가 삽입된 순서를 유지하는 컨테이너.

##### 벡터 (`vector`)

요소가 추가되거나 제거될 때 자동으로 크기가 변하는 동적 배열이다. 배열처럼 연속된 주소에 저장되기 때문에 반복자로 접근할 수 있다.

* `assign(count, value)` : `count`개의 `value`로 벡터를 초기화한다.
* `push_back(value)`, `pop_back()`
* `insert(pos, value)`, `erase(pos)`
* `clear()`
* `front()`, `back()`, `size()`, `empty()`
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

양 끝으로 확장 및 축소할 수 있다. 벡터에 front 접근을 추가한 형태지만 연속 메모리 할당(Contiguous Memory Allocation)은 보장되지 않는다.

덱 반복자(`deque<T>::iterator`)를 사용해야 한다. 역시 마지막 요소는 `--dq.end()`임에 유의.

##### 포워드 리스트 (`forward_list`)

단일 연결 리스트를 구현한다. 즉 리스트와 달리 다음 요소의 주소만을 추적하기 때문에 이전 요소로 직접 접근할 수 없다.

#### 연관 컨테이너 (Associative Containers)

요소의 순서가 미리 정해져 있거나 순서 개념이 없다. [키-값](https://ko.wikipedia.org/wiki/%ED%82%A4-%EA%B0%92_%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4) 참조.

#### 세트 (Set)

**값**이 곧 **키**이다. **키**는 유일(unique)하며 불변(immutable)하기 때문에 세트의 요소는 중복되지 않으며 수정할 수 없다. 물론 어떤 요소를 삭제하고 다른 값의 요소를 삽입하는 것은 가능하다.

* `erase(s.begin(), 30)`처럼 `erase()`에 범위를 지정할 수 있다.

#### 맵 (Map)

**키**-**값** 쌍(`pair`)으로 구성된다.

```cpp
map<int, int> mp;

mp.insert(pair<int, int>(1,2));
mp.insert(pair<int, int>(2,3));
mp.erase(2);  // 키 값을 기준으로 삭제
```

```cpp
void printMap(map<int, int> mp) {
  map<int, int>::iterator it;
  for (it = mp.begin(); it != mp.end(); ++it) {
    cout << it->first << ": " << it->second << '\n';
  }
}
```

#### 컨테이너 어댑터 (Container Adaptors)

기능이 제한되거나 변형된 컨테이너. 반복자를 지원하지 않는다.

* 스택 (`stack`)

* 큐 (`queue`)

* 우선순위 큐(`priority_queue`) : 값이 자동으로 정렬되는 큐. 기본적으로 높은 값부터 pop된다. `#include <queue>`가 필요하다.
  * `priority_queue<T, vector<T>, greater<T>>`로 선언하면 작은 값을 우선한다. `vector<T>`는 큐의 구현체.