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

int main()
{
  vector<int> vc = {9, 1, 6, 8, 4, 3, 2, 0};

  bubbleSort(vc);
  printVector(vc);

  return 0;
}
