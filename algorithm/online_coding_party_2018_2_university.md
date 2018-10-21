# [온라인 코딩 파티 2018 시즌 2 \~나도 프로그래머다\~](https://codingparty.goorm.io/#university)


## 예선 - 1번 문제

#### 입력

1. 표준입력으로 메모 한 장의 내용이 한 줄로 주어진다.

1. 메모 내용 중 날짜는 `2018/12/13`, `2018-12-18`, `2018년12월13일`의 형태로 주어진다.

1. 연도에 2자리 숫자만 온다면 20XX년으로 가정한다. 월과 일이 1~9 범위의 숫자인 경우 0이 있을 수도 있고 없을 수도 있다.

1. 모든 메모에는 날짜가 반드시 1번만 포함된다.

1. 입력의 마지막으로 `END`가 주어진다.

#### 출력

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
  break if s == "END"
  ma = s.match(REGEX1) || s.match(REGEX2) || s.match(REGEX3)
  year, month, day = ma["year"], ma["month"], ma["day"]
  year = "20" + year if year.length == 2
  month = "0" + month if month.length == 1
  day = "0" + day if day.length == 1
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

  print(res.join(""))
else
  print("-1")
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

print ((filled_cnt == area) ? "T" : "F")
print move_cnt-1
```


## 예선 - 4번 문제

#### 문제

합해서 주어진 정수 n을 만들 수 있는 세 개의 수의 조합을 모두 구한다.

#### 입력

1. 첫 번째 줄에 정렬되지 않은 0 이상 20 이하의 정수가 공백으로 구분되어 주어진다. 중복된 수가 있을 수 있다.

1. 두 번째 줄에 정수 n이 주어진다.

#### 출력

1. 각 줄에 공백으로 구분된 세 개의 정수를 오름차순으로 출력한다.

1. 각 정수 뿐 아니라 세 정수의 조합 또한 오름차순으로 출력해야 한다.

1. 중복된 조합은 출력하지 않는다.

1. 결괏값이 없으면 `NO`를 출력한다.

### 풀이

```ruby
ns = gets.split.map(&:to_i).sort
sum = gets.to_i

res = []
ns.each_with_index { |n1, i1|
  ns.each_with_index { |n2, i2|
    next if i2 <= i1
    ns.each_with_index { |n3, i3|
      next if i3 <= i2
      joint = [n1, n2, n3].join(" ")
      res << joint if n1 + n2 + n3 == sum
    }
  }
}

res.uniq!
print((res.size > 0) ? res.join("\n") + "\n" : "NO")
```


## 본선 - 1번 문제

#### 문제

수면 위에 센서가 5개 있는데, 특정 지점에서 물결파가 발생하면 어떤 센서가 가장 먼저 반응하는지 찾는다.

#### 입력

1. 첫 줄에 물결파가 발생한 위치와 유효 반지름이 `X Y R` 형식으로 주어진다.

1. 이후 다섯 줄에 다섯 개의 센서에 대한 위치가 `X Y` 형식으로 주어진다. 센서의 번호는 1번부터 5번까지이다.

1. 주어지는 모든 수는 1000 이하의 정수이다.

1. 두 센서가 같은 위치에 있거나 물결파와의 거리가 같은 경우는 없다.

#### 출력

가장 먼저 물결파를 감지할 센서의 번호를 출력한다. 아무 센서도 물결파를 감지할 수 없다면 `-1`을 출력한다.

### 풀이

```ruby
wave_x, wave_y, wave_r = gets.split.map(&:to_i)
sensors = []
5.times { sensors << gets.split.map(&:to_i) }

res = -1
dist_min = nil
5.times { |i|
  sensor_x, sensor_y = sensors[i]
  dist = Math.sqrt( (wave_x - sensor_x) ** 2 + (wave_y - sensor_y) ** 2 )
  next if dist > wave_r
  if dist_min.nil? || dist < dist_min
    dist_min = dist
    res = i + 1
  end
}

puts(res)
```


## 본선 - 2번 문제

#### 입력

1. 공백으로 구분된 5개의 자연수가 `A B C D E` 형식으로 주어진다.

1. 가위, 바위, 보는 각각 `1`, `2`, `3`이다.

#### 출력

가위바위보에서 이긴 사람의 수를 출력한다.

### 풀이

```ruby
ns = gets.split.map(&:to_i)

has_1 = ns.include?(1)
has_2 = ns.include?(2)
has_3 = ns.include?(3)
res = (has_1 && has_2 && has_3) ? 0 :
      (has_1 && has_3) ? ns.count(1) :
      (has_2 && has_1) ? ns.count(2) :
      (has_3 && has_2) ? ns.count(3) :
      0  # 전부 같음
puts(res)
```


## 본선 - 3번 문제

#### 입력

각 줄에 `2018-08-03|10001|A02`처럼 판매일자와 상품번호와 판매지역이 `|`로 구분되어 주어진다.

#### 출력

1. 가장 많이 판매된 상품 3개를 내림차순으로 각 줄에 출력한다.

1. 출력 형식은 `상품번호:판매횟수=>판매지역:판매횟수,판매지역:판매횟수,판매지역:판매횟수`.

1. 판매횟수가 같은 경우 상품번호 혹은 판매지역에 대해 오름차순으로 정렬한다.

1. 상품별 판매지역은 최대 3개까지만 출력한다.

1. 내역이 없는 경우 `NO RESULT`를 출력한다.

### 풀이

키가 상품번호인 딕셔너리의 값으로 키가 판매지역인 딕셔너리를 넣는다.

```ruby
orders = {}
$<.each { |line|
	date, product, region = line.chomp.split("|")
	next if !date.between?("2018-08-02", "2018-08-09")
	orders[product] = {} if !orders.include?(product)
	orders[product][region] = 0 if !orders[product].include?(region)
	orders[product][region] += 1
}

# 코드(키)에 대해 오름차순 정렬 (2순위)
orders = orders.sort.to_h
orders.each { |product, regions| orders[product] = regions.sort.to_h }
# 판매횟수(값)에 대해 내림차순 정렬 (1순위)
orders = orders.sort_by { |product, regions| -regions.values.reduce(:+) }.to_h
orders.each { |product, regions| orders[product] = regions.sort_by { |code, cnt| -cnt }.to_h }

res = ""
orders.each { |product, regions|
	res << "#{product}:#{regions.values.reduce(:+)}=>"
	res << regions.map { |code, cnt| "#{code}:#{cnt}" }.join(",")
	res << "\n"
}
res.chomp!

print((res.length > 0) ? res : "NO RESULT")
```
