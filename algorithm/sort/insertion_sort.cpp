#include <iostream>
#include <vector>

using namespace std;

void printVector(vector<int> vc) {
  for (int i = 0; i < vc.size(); ++i) {
    cout << ' ' << vc[i];
  }
  cout << '\n';
}

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

int main()
{
  vector<int> vc = {9, 1, 6, 8, 4, 3, 2, 0};

  insertionSort(vc);
  printVector(vc);

  return 0;
}
