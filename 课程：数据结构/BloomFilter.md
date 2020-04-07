## BloomFilter
### 布隆过滤器Python实现示例（GeekForGeeks）
https://www.geeksforgeeks.org/bloom-filters-introduction-and-python-implementation/

### Python代码示例
```Python
from bitarray import bitarray 
import mmh3 

class BloomFilter: 
	def __init__(self, size, hash_num): 
		self.size = size 
		self.hash_num = hash_num 
		self.bit_array = bitarray(size) 
		self.bit_array.setall(0) 

	def add(self, s): 
		for seed in range(self.hash_num): 
			result = mmh3.hash(s, seed) % self.size 
			self.bit_array[result] = 1 

	def lookup(self, s): 
		for seed in range(self.hash_num): 
			result = mmh3.hash(s, seed) % self.size 
			if self.bit_array[result] == 0: 
				return "Nope" 
		return "Probably" 

bf = BloomFilter(500000, 7) 
bf.add("dantezhao") 
print (bf.lookup("dantezhao")) 
print (bf.lookup("yyj")) 
```

### 高性能布隆过滤器Python实现
https://github.com/jhgg/pybloof


### 布隆过滤器Java实现示例1
https://github.com/lovasoa/bloomfilter/blob/master/src/main/java/BloomFilter.java

### 布隆过滤器Java实现示例2
https://github.com/Baqend/Orestes-Bloomfilter
