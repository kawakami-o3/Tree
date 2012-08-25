n = 5
(1..n).each do |i|
  #puts (2**(n-i)-1) if n!=i
  print (" "*(2**(n-i)-1)) if n!=i
  i.times do |j|
    print "#{i}"
    print (" "*(2**(n-i+1)-1))
  end
  puts

end
