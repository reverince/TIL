# 상호 배제 (Mutex; Mutual Exclusion)

## 상호 배제의 3조건
- Mutual Exclusion (상호 배제)
- Progress (진행)
- Bounded waiting (한정 대기)

## SW Mutex

### 2개 프로세스 ME

- Dekker 알고리즘 (`flag[]` + `turn`)
- Peterson 알고리즘 (양보)

### n개 프로세스 ME

- Dijkstra 알고리즘

  ```cpp
  int j;
  do {
      flag[i] = 1;
      while (turn != i)
          if (flag[turn] = 0) turn = i;
      flag[i] = 2;
      j = 0;
      for (j = 0 ; j < n && (j == i || flag[j] != 2); ++j);
  } while ( j >= n);
  ```

## HW Mutex

- TAS(Test And Set)

  ```cpp
  // EnterCS
  waiting[i] = true;
  key = true;
  while (waiting[i] && key) key = TestAndSet(&lock);
  waiting[i] = false;
  // CS ...
  // ExitCS
  j = (i + 1) % n;
  while (j != i && !waiting[j]) j = (j + 1) % n;
  if (j == i) lock = false;
  else waiting[j] = false;
  ```
  - 그냥 쓰면 Bounded waiting 위배 가능 (새치기)
  - Busy waiting

## OS Mutex

### Spinlock

```cpp
P(S) { while (S <= 0) { }; --S; }
V(S) { ++S; }
```
- 멀티 프로세서에서만 가능

### Semaphore (세마포어)

```cpp
P(S) { if (S <= 0) { /* wait on the Q */ } else --S; }
V(S) { if (!Q.empty) { /* wake-up one on Q */ } else ++S; }
```
- No busy waiting (block)
- Binary Semaphore / Counting Semaphore
- ME, Process synchronizaiton, Producer-consumer, Readers-writers, Dining philosophers
- Starvation

#### Produer-consumer problem

###### Producer
```cpp
MESSAGE msg = produceMessage();
P(mutexP);
P(nrEmpty);
buffer[in] = msg;
in = (in + 1) % N;
V(nrFull);
V(mutexP);
```

###### Comsumer
```cpp
P(mutexC);
P(nrFull);
MESSAGE msg = buffer[out];
out = (out + 1) % N;
V(nrEmpty);
V(mutexC);
consumeMessage(msg);
```

#### Readers-writers problem

- Reader first code

###### Reader
```cpp
P(mutexR);
if (nReader == 0) P(mutexW);
++nReader;
V(mutexR);

read();

P(mutexR);
--nReader;
if (nReader == 0) V(mutexW);
V(mutexR);
```

###### Writer
```cpp
P(mutexW)
write();
V(mutexW);
```

### Eventcount / Sequencer

- **S**equencer: 0으로 초기화된 정수. 감소하지 않음. 발생 사건 순서 유지. `ticket(S)`으로만 접근 가능.
  - `ticket(S)`: `ticket(S)` 연산이 호출된 횟수 반환.
- **E**ventcount: `read(E)`, `advance(E)`, `await(E, v)`으로만 접근 가능.
  - `read(E)`: 현재 `E` 값 반환.
  - `advance(E)`: `++E;`. `E`를 기다리는 프로세스 wake-up.
  - `await(E, v)`: `E<v`면 `E`에 연결된 큐에 프로세스 push 및 CPU 스케줄러 호출.

```cpp
v = ticket(S);
await(E, v);
// CS ...
advance(E);
```

- No busy waiting
- No starvation
- Low-level control than Semaphore

#### Producer-consumer Problem

###### Producer
```cpp
MESSAGE msg = produceMessage();
t = ticket(ticketP);
await(in, t);
await(out, t - N + 1);
buffer[t % N] = msg;
advance(in);
```

###### Consumer
```cpp
u = ticket(ticketC);
await(out, u);
await(in, u + 1);
MESSAGE msg = buffer[u % N];
advance(out);
consumeMessage();
```

## Language-Level Mutex

### Monitor

- 공유 데이터와 임계 영역의 집합
- 유사 객체지향
- Entry queue: 모니터 내 프로시저 수만큼 큐 존재.
- Condition queue: 특정 이벤트 대기.
- Signaler queue: 하나의 신호 제공자 큐 존재. `signal()`을 실행한 프로세스가 대기.
- Information hiding: 공유 데이터는 모니터 내 프로세스만 접근 가능.

#### Circular buffer

```cpp
MESSAGE buffer[N] = { };
int validBuffer = 0, in = 0, out = 0;
bool bufferHasMsg = false, bufferHasSpace = true;
...
void fillBuffer(const MESSAGE msg) {
  if (validBuffer == N) bufferHasSpace.wait();
  buffer[in] = msg;
  ++validBuffer;
  in = ++in % N;
  bufferHasMsg.signal();
}
void emptyBuffer(MESSAGE& msg) {
  if (validBuffer == 0) bufferHasMessage.wait();
  msg = buffer[out];
  --validBuffer;
  out = ++out % N;
  bufferHasSpace.signal();
}
```

#### Producer-consumer problem

```cpp
int nReader = 0;
bool isWriting = false;
CONDITION canRead, canWrite;
...
void beginReading() {
  if (isWriting || queue(canWrite)) canRead.wait();
  ++nReader;
  if (queue(canRead)) canRead.signal();
}
void finishReading() {
  --nReader;
  if (nReader == 0) canWrite.signal();
}
void beginWriting() {
  if (isWriting || nReader > 0) canWrite.wait();
  isWriting = true;
}
void finishWriting() {
  isWriting = false;
  if (queue(canRead)) canRead.signal();
  else canWrite.signal();
}
```

#### Dining philosophers problem

- 5 philosophers

```cpp
PHILOSOPHER nForks[5] = { 2, };
CONDITION ready[5];
...
void pickUp(PHILOSOPHER me) {
  if (nForks[me] != 2) ready[me].wait();
  --nFork[right(me)];
  --nFork[left(me)];
}
void putDown(PHILOSOPHER me) {
  ++nFork[right(me)];
  ++nFork[left(me)];
  if (nFork[right(me)] == 2) ready[right(me)].signal();
  if (nFork[left(me)] == 2) ready[left(me)].signal();
}
```
