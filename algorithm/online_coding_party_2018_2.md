# [온라인 코딩 파티 2018 시즌 2 \~나도 프로그래머다\~](https://codingparty.goorm.io/#university)


## 예선 - 1번 문제

#### 입력조건

1. 표준입력으로 메모 한 장의 내용이 한 줄로 주어진다.

1. 메모 내용 중 날짜는 `2018/12/13/`, `2018-12-18`, `2018년12월13일`의 형태로 주어진다.

1. 연도에 2자리 숫자만 온다면 20XX년으로 가정한다. 월과 일이 1~9 범위의 숫자인 경우 0이 있을 수도 있고 없을 수도 있다.

1. 모든 메모에는 날짜가 반드시 1번만 포함된다.

1. 입력의 마지막으로 `END`가 주어진다.

#### 출력조건

날짜 오름차순으로 메모 내용을 한 줄씩 출력한다.


### 풀이

정규표현식을 쓴다.

#### 루비

```ruby
REGEX1 = /(?<year>\d+)\/(?<month>\d+)\/(?<day>\d+)/
REGEX2 = /(?<year>\d+)-(?<month>\d+)-(?<day>\d+)/
REGEX3 = /(?<year>\d+)년(?<month>\d+)월(?<day>\d+)일/

arr = []

loop {
  s = gets.chomp
  break if s == 'END'
  ma = s.match(REGEX1) || s.match(REGEX2) || s.match(REGEX3)
  year, month, day = ma['year'], ma['month'], ma['day']
  year = '20' + year if year.length == 2
  month = '0' + month if month.length == 1
  day = '0' + day if day.length == 1
  arr << [year, month, day, s]
}

res = []
arr.sort.each { |a| res << a[3] }
print(res.join("\n"))
```

#### 파이썬

```python
import re

REGEX1 = '(?P<year>\d+)\/(?P<month>\d+)\/(?P<day>\d+)'
REGEX2 = '(?P<year>\d+)-(?P<month>\d+)-(?P<day>\d+)'
REGEX3 = '(?P<year>\d+)년(?P<month>\d+)월(?P<day>\d+)일'

arr = []

while True:
  s = input()
  if s == 'END':
    break
  srch = re.search(REGEX1, s) or re.search(REGEX2, s) or re.search(REGEX3, s)
  year, month, day = srch.group('year'), srch.group('month'), srch.group('day')
  if len(year) == 2:
    year = '20' + year
  if len(month) == 1:
    month = '0' + month
  if len(day) == 1:
    day = '0' + day
  arr.append([year, month, day, s])

arr.sort()
res = []
for a in arr:
  res.append(a[3])
print('\n'.join(res), end='')
```


## 예선 - 2번 문제

#### 문제

각 자리마다 최댓값이 다른 계수기를 구현한다.

#### 입력

1. 숫자판의 개수 m
1. 공백으로 구분된, 각 숫자판의 최댓값 m개
1. 공백으로 구분된, 각 숫자판의 초깃값 m개
1. 버튼을 누르는 횟수

#### 출력

계수기 숫자판 상태를 공백 없이 한 줄로 출력한다.

### 풀이(X)

계수기가 저장하고 있는 값을 십진수로 변환해 누른 횟수와 더하고 다시 계수기의 표현 방식으로 변환한다.

```ruby
m = gets.to_i
max_nums = gets.split.map(&:to_i)
init_nums = gets.split.map(&:to_i)
push_cnt = gets.to_i

valid = true
m.times { |i| valid = false if max_nums[i] < init_nums[i] }
m.times { |i| valid = false if max_nums[i] < 0 || max_nums[i] > 9 || init_nums[i] < 0 || init_nums[i] > 9 }
if valid
	muls = []  # 해당 자릿수의 멀티플라이어
	muls << ((max_nums[-1] > 0) ? 1 : 0)
	mul = 1
	(m-1).times { |i|
		mul *= max_nums[-1-i] + 1 if max_nums[-1-i] > 0
		muls.unshift((max_nums[-2-i] > 0) ? mul : 0)
	}

	val = push_cnt
	m.times { |i| val += init_nums[i] * muls[i] }

	res = []
	muls.each { |n|
		if n > 0
			res << (val/n)
			val %= n
		else
			res << 0
		end
	}
	m.times { |i| res[i] %= max_nums[i] + 1 }

	print(res.join(''))
else
	print('-1')
end
```

```
오답입니다. (통과하지 못한 테스트케이스가 있습니다.)
```

## 예선 - 3번 문제

#### 입력

공백으로 구분된, 높이와 너비

#### 출력

주어진 높이와 너비의 직사각형 체스 판 위에서, 맨 왼쪽 위 칸에서 출발한 나이트에 대해,

1. 나이트가 모든 칸에 도달할 수 있는지 여부 `T` / `F`
1. 최대 거리

이상을 한 줄에 공백 없이 한 줄로 출력한다.

### 풀이(?)

```ruby
MOVES = [[-2, -1], [-2, 1], [-1, -2], [-1, 2], [1, -2], [1, 2], [2, -1], [2, 1]]

w, h = gets.split.map(&:to_i)

cells = Array.new(h) { Array.new(w) }
cells[0][0] = 0

filled_cnt = 1
area = w * h

locations = [[0, 0]]
move_cnt = 1
while filled_cnt < area do
	next_locations = []
	filled_cnt_before = filled_cnt
	locations.each { |y, x|
		MOVES.each { |dy, dx|
			ny, nx = y+dy, x+dx
			if ny.between?(0, h-1) && nx.between?(0, w-1) && cells[ny][nx].nil?
				cells[ny][nx] = move_cnt
				filled_cnt += 1
				next_locations << [ny, nx]
			end
		}
	}
	locations = next_locations
	break if filled_cnt_before == filled_cnt
	move_cnt += 1
end

print ((filled_cnt == area) ? 'T' : 'F')
print move_cnt-1
```
