class Point
  attr_reader :x, :y

  def initialize(x, y)
    @x = x
    @y = y
  end
end

def ccw(p1, p2, p3)
  s2 = p1.x*p2.y + p2.x*p3.y + p3.x*p1.y - p1.y*p2.x - p2.y*p3.x - p3.y*p1.x
  return (s2 > 0) ? 1 : (s2 < 0) ? -1 : 0
end


points = []
3.times {
  x, y = gets.split.map(&:to_f)
  points << Point.new(x, y)
}

p ccw(points[0], points[1], points[2])
