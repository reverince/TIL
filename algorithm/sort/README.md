# 정렬

[정렬 알고리즘](https://ko.wikipedia.org/wiki/정렬_알고리즘)


## O(n^2)

### 선택 정렬 (Selection Sort)

1. 주어진 리스트에서 최솟값을 찾는다.
1. 그 값을 맨 앞에 위치한 값과 교체한다.
1. 맨 앞을 제외한 나머지 리스트로 반복한다.

```cpp
void selectionSort(vector<int> &vc)
{
  int end = vc.size() - 1;
  for (int level = 0; level < end; ++level) {
    int indexMin = i;
    for (int j = level + 1; j < vc.size(); ++j) {
      if (vc[j] < vc[indexMin]) {
        indexMin = j;
      }
    }
    swap(vc[level], vc[indexMin]);
  }
}
```

### 거품 정렬 (Bubble Sort)

1. 리스트 맨 앞에서부터 인접한 요소를 두 개씩 비교해 정렬한다.
1. 맨 뒤를 제외한 나머지 리스트로 반복한다.

```cpp
void bubbleSort(vector<int> &vc)
{
  int end = vc.size() - 1;
  for (int level = 0; level < end; ++level) {
    for (int i = 0; i < end-level; ++i) {
      if (vc[i] > vc[i+1]) {
        swap(vc[i], vc[i+1]);
      }
    }
  }
}
```

### 삽입 정렬 (Insertion Sort)

1. 리스트 앞에서부터 각 요소를 이미 정렬된 배열과 비교한다.
1. 알맞은 위치에 요소를 삽입하고 다음 요소로 진행한다.

```cpp
void insertionSort(vector<int> &vc) {
  for (int level = 1; level < vc.size(); ++level) {
    int tmp = vc[level];
    int i = level - 1;
    for (; i >= 0; --i) {
      if (tmp > vc[i]) break;
      vc[i+1] = vc[i];
    }
    vc[i+1] = tmp;
  }
}
```

## 번외

### 취침 정렬 (Sleep Sort)

1. 각 요소에 대해 스레드를 만들어 값에 비례하는 시간만큼 잔다.
1. 다 잤으면 결과 행렬 맨 뒤에 요소를 삽입한다.

```ruby
def sleep_sort(vc, sleep_time=0.1)
  
  ret = []
  threads = []

  vc.each { |n|
    threads << Thread.new { sleep(n * sleep_time); ret << n}
  }

  threads.each { |thr| thr.join }

  ret
end

vc = [2, 5, 9, 3, 4, 8, 7, 1, 6]
1.upto(24) { |i|
  sleep_time = 1.0 / i.to_f
  puts "sleep_time: 1/" + i.to_s + " sec"
  puts "result: [" + sleep_sort(vc, sleep_time)*", " + "]"
}
```

```
sleep_time: 1/1 sec
result: [1, 2, 3, 4, 5, 6, 7, 8, 9]
sleep_time: 1/2 sec
result: [1, 2, 3, 4, 5, 6, 7, 8, 9]
...
sleep_time: 1/21 sec
result: [2, 1, 3, 4, 5, 6, 8, 7, 9]
sleep_time: 1/22 sec
result: [1, 2, 3, 4, 5, 6, 7, 8, 9]
sleep_time: 1/23 sec
result: [1, 2, 3, 4, 5, 6, 9, 7, 8]
sleep_time: 1/24 sec
result: [1, 2, 3, 4, 5, 6, 7, 8, 9]
```

틀리는 게 귀엽다.
