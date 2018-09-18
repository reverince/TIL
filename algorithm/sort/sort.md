# Sort

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
  for (int i = 0; i < end; ++i) {
    int indexMin = i;
    for (int j = i + 1; j < vc.size(); ++j) {
      if (vc[j] < vc[indexMin]) {
        indexMin = j;
      }
    }
    swap(vc[i], vc[indexMin]);
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
