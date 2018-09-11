require "set"

WIDTH = 5
HEIGHT = 5

N, S, W, E = 1, 2, 4, 8
DX = {N => 0, S => 0, W => -1, E => 1}.freeze
DY = {N => -1, S => 1, W => 0, E => 0}.freeze
OPPOSITE = {N => S, S => N, W => E, E => W}.freeze


# 벽을 뚫어 미로 생성
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

# 미로의 표현 방식 변환
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

# 변환 전 미로 출력
def display_maze(grid)
  puts " " + "_" * (WIDTH * 2 - 1)
  HEIGHT.times do |y|
    print "|"
    WIDTH.times do |x|
      print((grid[y][x] & S != 0) ? " " : "_")
      if grid[y][x] & E != 0
        print(((grid[y][x] | grid[y][x + 1]) & S != 0) ? " " : "_")
      else
        print "|"
      end
    end
    puts
  end
end

# 변환된 미로 출력
def display_converted_maze(grid_OX)
   grid_OX.each do |line|
    puts line.join(" ")
  end
end

# 두 점을 이동하는 경로 탐색
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

# 도착 지점부터 출발 지점까지 역추적해 경로 출력
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

# 그리드 생성
grid = Array.new(HEIGHT) { Array.new(WIDTH, 0) }
parents = {}
visited = Set.new

# 미로 생성
carve_passages_from(0, 0, grid)
display_maze(grid)

# 미로 변환
grid_c = convert_maze(grid)
display_converted_maze(grid_c)

# BFS
start_x, start_y, goal_x, goal_y = 1, 1, WIDTH*2-1, HEIGHT*2-1

bfs(start_x, start_y, goal_x, goal_y, grid_c, parents, visited)

if parents[[goal_y, goal_x]]
  puts "경로를 찾았습니다."
else
  puts "경로를 찾지 못했습니다."
end
way(start_x, start_y, goal_x, goal_y, grid_c, parents)
puts

#display_converted_maze(grid_c)

0
