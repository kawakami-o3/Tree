require "./tree.jar"
require 'java'
include Java
#java.lang.System.out.println("hello")
#p Node.new(1)
tree = Java::tree.BinaryTree.new()
[3,5,56].each do |i|
  tree.insert(Java::tree.Node.new(i))
end
tree.inorderWalk
