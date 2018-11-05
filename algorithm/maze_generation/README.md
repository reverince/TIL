# Maze Generation

[Maze Generation: Algorithm Recap](http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap)


## Recursive Backtracking

###### recursive_backtracking.rb

### 모듈 로딩과 상수 선언

```ruby
require "set"

WIDTH = 5
HEIGHT = 5

N, S, W, E = 1, 2, 4, 8
DX = {N => 0, S => 0, W => -1, E => 1}.freeze
DY = {N => -1, S => 1, W => 0, E => 0}.freeze
OPPOSITE = {N => S, S => N, W => E, E => W}.freeze
```

* 각 칸은 `0`으로 초기화하며 뚫린 방향에 대한 비트를 1로 가진다. 즉 서쪽(`W`) 벽만 뚫린 경우 `4`, 서쪽 벽과 남쪽(`S`) 벽이 뚫린 경우 `6`(`= 4 | 2`)을 값으로 가진다.

  ```ruby
  grid = Array.new(HEIGHT) { Array.new(WIDTH, 0) }
  ```

### 벽을 뚫어 미로 생성

```ruby
def carve_passages_from(cx, cy, grid)
  # 각 방향을 뚫는다
  directions = [N, S, W, E].shuffle
  directions.each do |direction|
    nx, ny = cx + DX[direction], cy + DY[direction]
    # 그리드 범위 이내이고 이미 다른 방향이 뚫리지 않았다면
    if ny.between?(0, grid.size - 1) && nx.between?(0, grid[ny].size - 1) && grid[ny][nx] == 0
      # 각 칸은 뚫린 방향에 대한 비트를 1로 가진다
      grid[cy][cx] |= direction
      grid[ny][nx] |= OPPOSITE[direction]
      # 뚫은 칸부터 진행
      carve_passages_from(nx, ny, grid)
    end
  end
end
```

```
 _________
| |   |   |
| |_|___| |
|_____  | |
|  ___|_  |
|_________|
```

### 미로의 표현 방식 변환

```ruby
def convert_maze(grid)
  height = grid.size * 2 + 1
  width = grid[0].size * 2 + 1
  grid_OX = Array.new(height) { Array.new(width, "X") }
  (0...height).each do |j|
    oj = (j - 1) / 2
    if j.even?
      (1..width-2).step(2).each do |i|
        oi = (i - 1) / 2
        grid_OX[j][i] = " " if grid[oj][oi] & S != 0
      end
    else
      (1...width).step(2).each do |i|
        grid_OX[j][i] = " "
      end
      (2..width-3).step(2).each do |i|
        oi = (i - 1) / 2
        grid_OX[j][i] = " " if grid[oj][oi] & E != 0
      end 
    end
  end

  grid_OX
end
```

```
X X X X X X X X X X X
X   X       X       X
X   X   X   X   X   X
X   X   X       X   X
X   X X X X X X X   X
X               X   X
X X X X X X X   X   X
X           X       X
X   X X X X X X X   X
X                   X
X X X X X X X X X X X
```

### 두 점을 이동하는 경로 탐색

```ruby
def bfs(x, y, goal_x, goal_y, grid, parents = {}, visited = Set.new, cnt = 0)
  visited << [y, x]
  return if x == goal_x && y == goal_y
  
  # 각 방향으로 탐색
  directions = [N, S, W, E].shuffle
  directions.each do |direction|
    nx, ny = x + DX[direction], y + DY[direction]
    # 그리드 영역 이내이고 방문한 적 없으며 벽이 아니면
    if ny.between?(0, grid.size - 1) && nx.between?(0, grid[ny].size - 1) && !visited.include?([ny, nx]) && grid[ny][nx] == " "
      bfs(nx, ny, goal_x, goal_y, grid, parents, visited, cnt + 1)
      parents[[ny, nx]] = [-DY[direction], -DX[direction]]
    end
  end
end
```

### 도착 지점부터 역추적해 경로 출력

```ruby
def way(start_x, start_y, x, y, grid, parents, cnt = 0)
  # 지나온 칸으로 표시
  grid[y][x] = "_"
  unless x == start_x && y == start_y
    return false if parents[[y, x]].nil?
    py, px = parents[[y, x]]
    way(start_x, start_y, x + px, y + py, grid, parents, cnt + 1)
    # 이하는 재귀 아래에 있으므로 역순 출력
    if py == -1
      print "↓ "
    elsif py == 1
      print "↑ "
    elsif px == -1
      print "→ "
    elsif px == 1
      print "← "
    end
  end
end
```

### 출력

```
 _________
| |   |_  |
| | |___  |
|___|  _| |
|  _  |  _|
|___|_____|
경로를 찾았습니다.
↓ ↓ ↓ ↓ → → ↑ ↑ ↑ ↑ → → ↓ ↓ → → → → ↓ ↓ ↓ ↓ ← ← ↓ ↓ → →
X X X X X X X X X X X
X _ X _ _ _ X       X
X _ X _ X _ X X X   X
X _ X _ X _ _ _ _ _ X
X _ X _ X X X X X _ X
X _ _ _ X       X _ X
X X X X X   X X X _ X
X           X _ _ _ X
X   X X X   X _ X X X
X       X     _ _ _ X
X X X X X X X X X X X
```
