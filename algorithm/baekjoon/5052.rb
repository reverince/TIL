class Node
  attr_accessor :p, :final

  def initialize(p=[nil]*10, final=false)
    @p = p
    @final = final
  end
end

gets.to_i.times {  # test case
  root = Node.new()  # root node
  res = "YES"
  numbers = []

  gets.to_i.times {  # input
    numbers << gets.chop.chars.map(&:to_i)
    number = numbers[-1]
    last_node = root
    number[0..-1].each { |n|
      if last_node.p[n].nil?
        last_node.p[n] = Node.new()
      end
      last_node = last_node.p[n]
    }
    last_node.final = true
  }

  numbers.each { |number|
    counter = number.size
    node = root
    number.each { |digit|
      node = node.p[digit]
      counter -= 1
      unless node
        res = "NO"
        break
      end
      if node.final && counter > 0
        res = "NO"
        break
      end
    }
  }

  puts res
}
