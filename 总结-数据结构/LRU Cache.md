## LRU Cache代码实现示例

### Python示例
```Python
class LRUCache(object): 

	def __init__(self, capacity): 
		self.dic = collections.OrderedDict() 
		self.remain = capacity

	def get(self, key): 
		if key not in self.dic: 
			return -1 
		v = self.dic.pop(key) 
		self.dic[key] = v   # key as the newest one 
		return v 

	def put(self, key, value): 
		if key in self.dic: 
			self.dic.pop(key) 
		else: 
			if self.remain > 0: 
				self.remain -= 1 
			else:   # self.dic is full
				self.dic.popitem(last=False) 
		self.dic[key] = value
```

### Java示例
```Java
public class LRUCache {
    private Map<Integer, Integer> map;
    public LRUCache(int capacity) {
        map = new LinkedCappedHashMap<>(capacity);
    }
    public int get(int key) {
        if(!map.containsKey(key)) { return -1; }
        return map.get(key);
    }
    public void put(int key, int value) {
        map.put(key,value); 
    }

    private static class LinkedCappedHashMap<K,V> extendsLinkedHashMap<K,V> {
        int maximumCapacity;
        LinkedCappedHashMap(int maximumCapacity) {
            super(16, 0.75f, true);
            this.maximumCapacity = maximumCapacity; 
        }
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > maximumCapacity; 
        } 
    }
}
```