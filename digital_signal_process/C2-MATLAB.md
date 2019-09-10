# MATLAB

- 형 선언 불필요 (컴퓨터가 사용 가능한 크기까지 자동으로 저장 공간 할당)

### 행렬 생성

```
>> x = [-2.5 12 0];
>> x(4) = abs(x(1));
>> x
x =
    -2.5000    12.0000    0    2.5000
```

### 벡터 생성

`a`와 `b` 사이에 `dx` 간격으로 분할된 `n`개의 벡터 값 생성

```
>> x = a : dx : b;
>> x = linspace(a, b, n);
```

### 행렬 연산

- ` A` ` : 행렬의 행과 열 전환 (transpose)
- `C = [A, B]`, `C = [A; B]` : 행렬 연결 (각각 가로, 세로)
- `+`, `-` : 덧셈, 뺄셈. 두 행렬의 차원이 같아야 함.
- `*` : 행렬 곱셈
- `.*` : 같은 위치의 원소 곱셈
- `\` : `A * X = B`의 해 `X = inv(A) * B`
- `/` : `X * B = A`의 해 `X = A * inv(B)`
- `<=`, `==`, `~=`(not equal) 등 관계 연산
- `&`, `|`, `~` 등 논리 연산

### 그래프 명령어

- `plot(X, Y, s)` : 벡터 X에 대응하는 벡터 Y 값을 선형 축을 사용해 그래프 출력 (`s`는 그래프 표현법)
- `stem(X, Y)` : 이산 축을 사용해 이산 신호에 대한 그래프 출력
- `subplot(m, n, p)` : 그래프 창을 `m` * `n` 행렬로 나누고 `p`번째 위치 선택 (`plot`으로 출력 필요`
- `clf` : 그래프 창 초기화
- `hold on` : 현재 그래프 창 유지
- `axis`(축 크기 조절), `xlabel`·`ylabel`(축 이름), `grid`(격자선), `title`(제목) 등

### 흐름 제어 명령어

- `if`, `elseif`, `else`
- `for` (`:`으로 구분)
- `while`
- `switch`, `case`, `otherwise`
- 각 흐름 제어 명령어 끝에 `end` 필요

### 기타 명령어

- `help` : 도움말
- `clc` : 명령어 창 초기화

### M-파일 프로그래밍

- M-파일 : MATLAB 명령어들을 모아 놓은 파일
- 명령어 창에서 M-파일을 호출하거나 M-파일 내에서 다른 M-파일 호출 가능


- Script-mode : 명령어 창에 파일명을 입력해 실행
- Function-mode : 입력 매개 변수와 출력 매개 변수를 다루는 함수 양식으로 프로그래밍
  - M-파일 이름과 파일 첫 줄 함수 이름이 같아야 함.
  - 첫 줄은 '함수 선언 줄'이라고 하며, 마지막 줄이 실행되거나 `return` 시 종료
  - `function y = factorial(n);`


## MATLAB 실습

`MATLAB/`