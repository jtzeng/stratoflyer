require 'java'

java_import 'java.util.List'
java_import 'java.util.ArrayList'

class ListTest

	def init_test_list
		list = ArrayList.new
		100.times do |i|
			list.add i
		end
		return list
	end

	def init_list_sum(list)
		sum = 0
		for i in list
			sum += i
		end
		return sum
	end
end

list_test = ListTest.new
puts list_test.init_list_sum(list_test.init_test_list)