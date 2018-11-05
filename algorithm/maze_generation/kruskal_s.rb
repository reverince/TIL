WIDTH = 10
HEIGHT = 10

N, S, W, E = 1, 2, 4, 8
DX = { N => 0, S => 0, W => -1, E => 1 }.freeze
DY = { N => -1, S => 1, W => 0, E => 0 }.freeze
OPPOSITE = { N => S, S => N, W => E, E => W }.freeze

class Tree
  attr_accessor :parent

  def initialize
    @parent = nil
  end

  def root
    @parent ? @parent.root : self
  end

  def connected?(tree)
    root == tree.root
  end

  def connect(tree)
    tree.root.parent = self
  end

end

def display_maze(grid)
  puts " " + "_" * (WIDTH * 2 - 1)
  grid.each_with_index do |row, y|
    print "|"
    row.each_with_index do |cell, x|
      print((cell & S != 0) ? " " : "_")

      if cell & E != 0
        print(((cell | row[x+1]) & S != 0) ? " " : "_")
      else
        print "|"
      end
      print "\e[m" if cell == 0
    end
    puts
  end
end

grid = Array.new(HEIGHT) { Array.new(WIDTH, 0) }
sets = Array.new(HEIGHT) { Array.new(WIDTH) { Tree.new } }

edges = []
HEIGHT.times do |y|
  WIDTH.times do |x|
    edges << [x, y, N] if y != 0
    edges << [x, y, W] if x != 0
  end
end
edges.shuffle!

# Generate
until edges.empty?
  x, y, direction = edges.pop
  nx, ny = x + DX[direction], y + DY[direction]
  set1, set2 = sets[y][x], sets[ny][nx]

  unless set1.connected?(set2)    
    set1.connect(set2)
    grid[y][x] |= direction
    grid[ny][nx] |= OPPOSITE[direction]
  end
end

display_maze(grid)

0
