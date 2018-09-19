#include <iostream>
#include <vector>

using namespace std;

void printVector(vector<int> vc)
{
  for (int i = 0; i < vc.size(); ++i) {
    cout << ' ' << vc[i];
  }
  cout << '\n';
}

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

int main()
{
  vector<int> vc = {9, 1, 6, 8, 4, 3, 2, 0};

  selectionSort(vc);
  printVector(vc);

  return 0;
}
