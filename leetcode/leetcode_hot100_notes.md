# LeetCode 热题 100 刷题笔记

---

## 📖 Python LeetCode 常用接口速查

> 刷题时容易忘的标准库接口，按数据结构分类。

### collections.OrderedDict — 有序字典（LRU 核心）

```python
from collections import OrderedDict

od = OrderedDict()
od['a'] = 1
od['b'] = 2
od['c'] = 3

od.move_to_end('a')           # 移到末尾 → b, c, a
od.move_to_end('c', last=False)  # 移到开头 → c, b, a
od.popitem(last=True)         # 弹出末尾 ('a', 1) — 类似栈
od.popitem(last=False)        # 弹出开头 ('c', 3) — 类似队列
```

**继承 OrderedDict 可直接当 LRU 用：** `self[key]` / `del self[key]` / `len(self)` / `key in self`

### collections.defaultdict — 带默认值的字典

```python
from collections import defaultdict

graph = defaultdict(list)      # 邻接表：graph[node].append(neighbor)
counter = defaultdict(int)     # 计数：counter[x] += 1
groups = defaultdict(set)      # 分组：groups[key].add(val)
```

### collections.deque — 双端队列

```python
from collections import deque

dq = deque([1, 2, 3])
dq.append(4)          # 右端入 → [1,2,3,4]
dq.appendleft(0)      # 左端入 → [0,1,2,3,4]
dq.pop()              # 右端出 → 4
dq.popleft()          # 左端出 → 0
dq.rotate(1)          # 右移一位 [3,1,2] → rotate(1) → [2,3,1]
dq.rotate(-1)         # 左移一位

# BFS 模板
queue = deque([(start, 0)])  # (节点, 距离)
while queue:
    node, dist = queue.popleft()
```

### collections.Counter — 计数器

```python
from collections import Counter

c = Counter("aabbc")           # Counter({'a':2, 'b':2, 'c':1})
c.most_common(2)               # [('a',2), ('b',2)] — 前 k 高频
c['z']                         # 0（不存在的键返回 0，不报错）
c.update("aaa")                # 追加计数 a→5
c.subtract("a")                # 减少计数 a→4
c1 + c2                        # 合并计数
c1 - c2                        # 差集（只保留正数）
c1 & c2                        # 交集（取 min）
c1 | c2                        # 并集（取 max）
+c                             # 去掉 0 和负数的项
list(c.elements())             # 按计数展开 ['a','a','b','b','c']
```

### heapq — 最小堆

```python
import heapq

nums = [3, 1, 4, 1, 5]
heapq.heapify(nums)            # 原地建堆 O(n)
heapq.heappush(nums, 2)       # 入堆
val = heapq.heappop(nums)     # 弹出最小值
heapq.heappushpop(nums, 6)    # push+pop 合一（更高效）
heapq.heapreplace(nums, 0)    # pop+push 合一（先弹后推）

# Top-K 最大的 K 个元素
heapq.nlargest(3, nums)        # 内部用最小堆实现
heapq.nsmallest(3, nums)

# 最大堆技巧：取反
heapq.heappush(heap, -val)     # 入堆
max_val = -heapq.heappop(heap) # 取反还原

# 带优先级的元组
heapq.heappush(heap, (priority, index, item))  # 用 index 打破平局
```

### bisect — 二分查找（有序数组）

```python
import bisect

arr = [1, 3, 3, 5, 7]
bisect.bisect_left(arr, 3)     # 2 — 插入点在相等元素左侧
bisect.bisect_right(arr, 3)    # 4 — 插入点在相等元素右侧
bisect.insort_left(arr, 4)     # 插入并保持有序 → [1,3,3,4,5,7]

# 常见用法
# 找第一个 >= target 的位置: bisect_left(arr, target)
# 找第一个 > target 的位置:  bisect_right(arr, target)
# 找最后一个 <= target:      bisect_right(arr, target) - 1
# 找最后一个 < target:       bisect_left(arr, target) - 1
# 统计 target 出现次数:      bisect_right(arr, target) - bisect_left(arr, target)
```

### sortedcontainers.SortedList — 有序列表（需 pip install）

```python
from sortedcontainers import SortedList

sl = SortedList([3, 1, 4])     # 自动排序 [1, 3, 4]
sl.add(2)                      # O(log n) 插入 → [1, 2, 3, 4]
sl.remove(3)                   # O(log n) 删除
sl.discard(99)                 # 不存在也不报错
sl.bisect_left(3)              # 二分查找位置
sl[0], sl[-1]                  # 最小/最大值
sl.irange(2, 5)                # 迭代 [2, 5] 范围内的元素
```

### itertools — 常用迭代工具

```python
import itertools

itertools.combinations([1,2,3], 2)    # (1,2),(1,3),(2,3) — 组合
itertools.permutations([1,2,3], 2)    # (1,2),(1,3),(2,1)... — 排列
itertools.product([0,1], repeat=3)    # 笛卡尔积 000,001,010...
itertools.accumulate([1,2,3,4])       # 前缀和 [1,3,6,10]
itertools.chain([1,2], [3,4])         # 拼接迭代器 → 1,2,3,4
itertools.groupby(sorted(data), key=fn) # 分组（需先排序）
```

### functools — 记忆化 & 比较

```python
from functools import lru_cache, cache, cmp_to_key

@cache                          # Python 3.9+ 无限缓存（等价 lru_cache(None)）
def dfs(i, j):
    ...

@lru_cache(maxsize=None)        # Python 3.2+ 记忆化递归
def dp(state):
    ...

# 自定义排序（如最大数拼接 #179）
nums.sort(key=cmp_to_key(lambda a, b: 1 if a+b < b+a else -1))
```

### 字符串/数组常用技巧

```python
# 字符串
s.isdigit() / s.isalpha() / s.isalnum()
ord('a')  # 97    chr(97)  # 'a'
s[::-1]   # 反转

# 数组
list(zip(*matrix))              # 矩阵转置
any(condition for x in arr)     # 存在性检查
all(condition for x in arr)     # 全称检查
divmod(10, 3)                   # (3, 1) — 商和余数

# 无穷大
float('inf') / float('-inf')
import math; math.inf
```

---

## 2. 两数相加 (Add Two Numbers) — 中等

**方法：** 模拟竖式加法 + 进位

**思路：**
1. 用哑节点 `dummy` 简化头节点处理
2. `curr` 指针构建结果链表
3. 逐位相加，维护 `carry` 进位
4. 链表走完补 0，最后若 carry > 0 再加一个节点

**⚠️ 易错点：链表拼接时 `curr` vs `curr.next`**

```python
# ❌ 错误写法：curr 指向新节点，断开了与 dummy 的连接
curr = ListNode(s % 10)

# ✅ 正确写法：在链表尾部接上新节点
curr.next = ListNode(s % 10)
```

> 记住：构建链表时永远是 `curr.next = 新节点`，然后 `curr = curr.next`

**完整代码：**

```python
class Solution:
    def addTwoNumbers(self, l1, l2):
        dummy = ListNode(0)
        curr = dummy
        carry = 0
        while l1 or l2 or carry:
            s = carry
            if l1: s += l1.val; l1 = l1.next
            if l2: s += l2.val; l2 = l2.next
            carry = s // 10
            curr.next = ListNode(s % 10)
            curr = curr.next
        return dummy.next
```

**复杂度：** 时间 O(max(m,n))，空间 O(max(m,n))

---

## 3. 无重复字符的最长子串 (Longest Substring Without Repeating Characters) — 中等

**方法：** 滑动窗口 + 集合

**思路：**
1. 用 `seen` 集合记录窗口内的字符
2. 右指针遍历字符串，左指针缩窗去重
3. 每次更新 `max_len`

**⚠️ 易错点1：遇到重复字符不能直接清零，要用左指针缩窗**

```python
# ❌ 清零：丢失有效部分，如 "abcdc" 遇到第二个 c，"d" 还能用
len = 0

# ✅ 缩窗：左指针右移，只移除必要的部分
while char in seen:
    seen.remove(s[left])
    left += 1
```

**⚠️ 易错点2：缩窗时必须同步从集合中删除元素，否则死循环**

```python
# ❌ 只移 left，集合没变 → 死循环
while char in seen:
    left += 1

# ✅ 同步删除
while char in seen:
    seen.remove(s[left])
    left += 1
```

**完整代码：**

```python
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        max_len = 0
        left = 0
        seen = set()
        for char in s:
            while char in seen:
                seen.remove(s[left])
                left += 1
            seen.add(char)
            max_len = max(max_len, len(seen))
        return max_len
```

**复杂度：** 时间 O(n)，空间 O(min(n, 字符集大小))

---

## 146. LRU 缓存 (LRU Cache) — 中等

**方法 1：继承 OrderedDict** ⭐ 面试首选写法

**核心思路：**
- `OrderedDict` 内部维护双向链表记录插入顺序
- `move_to_end(key)` → O(1) 移到末尾（标记为最近使用）
- `popitem(last=False)` → O(1) 弹出开头（淘汰最久未使用）

```python
class LRUCache(collections.OrderedDict):

    def __init__(self, capacity: int):
        super().__init__()
        self.capacity = capacity

    def get(self, key: int) -> int:
        if key not in self:
            return -1
        self.move_to_end(key)  # 标记为最近使用
        return self[key]

    def put(self, key: int, value: int) -> None:
        if key in self:
            self.move_to_end(key)  # 已存在则更新位置
        self[key] = value
        if len(self) > self.capacity:
            self.popitem(last=False)  # 淘汰最久未使用（开头）
```

**⚠️ 自己写 OrderedDict 版时踩过的坑：**

| 错误写法 | 问题 | 正确写法 |
|---------|------|---------|
| `self.cache = __super` | 混用"继承 OrderedDict"和"内部持有 cache"两套设计；`__super` 未定义 → `NameError` | 二选一：继承就直接用 `self`，或改成 `self.cache = collections.OrderedDict()` |
| `if key not in cache:` | 漏写 `self`，`cache` 是未定义变量 → `NameError` | 继承版用 `key not in self`；组合版用 `key not in self.cache` |
| `cache.pop_item()` | 方法名拼错，且 `popitem()` 默认弹**末尾（最新）** | `self.popitem(last=False)` —— 必须 `last=False` 才弹**开头（最旧）** |
| `self.cache.put(key)` | `OrderedDict` 没有 `put`，且没传 value | `self[key] = value`（直接当 dict 赋值） |
| put 里只 `move_to_end` 没赋值 | 命中已存在的 key 时忘了写入新 value，且新增 key 根本没插进去 | 无论是否已存在，最后统一 `self[key] = value`，再判断是否超容量淘汰 |
| `// 注释` | Python 用 `#`，`//` 是 C/Java 的写法 | `# 注释` |

> **为什么命中要 `move_to_end`？** 约定"末尾=最近使用、开头=最久未使用"。每次 get/put 命中就把它移到末尾标记为"最新"；容量满时 `popitem(last=False)` 从开头淘汰最久没碰过的，这就是 LRU 的核心。

**⚠️ 关键接口记忆：**

| 接口 | 作用 | 时间复杂度 |
|------|------|-----------|
| `od.move_to_end(key)` | 移到末尾 | O(1) |
| `od.move_to_end(key, last=False)` | 移到开头 | O(1) |
| `od.popitem(last=True)` | 弹出末尾（默认，类似栈） | O(1) |
| `od.popitem(last=False)` | 弹出开头（类似队列） | O(1) |
| `key in od` | 判断是否存在 | O(1) |
| `del od[key]` | 删除指定键 | O(1) |

**方法 2：哈希表 + 手写双向链表**（理解底层原理）

```python
class DLinkedNode:
    def __init__(self, key=0, value=0):
        self.key = key
        self.value = value
        self.prev = None
        self.next = None

class LRUCache:
    def __init__(self, capacity: int):
        self.cache = {}  # key → DLinkedNode
        self.capacity = capacity
        # 哨兵节点：避免边界判断
        self.head = DLinkedNode()  # dummy head
        self.tail = DLinkedNode()  # dummy tail
        self.head.next = self.tail
        self.tail.prev = self.head

    def get(self, key: int) -> int:
        if key not in self.cache:
            return -1
        node = self.cache[key]
        self._move_to_head(node)
        return node.value

    def put(self, key: int, value: int) -> None:
        if key in self.cache:
            node = self.cache[key]
            node.value = value
            self._move_to_head(node)
        else:
            node = DLinkedNode(key, value)
            self.cache[key] = node
            self._add_to_head(node)
            if len(self.cache) > self.capacity:
                removed = self._remove_tail()
                del self.cache[removed.key]  # ⚠️ 别忘了从哈希表中也删除

    def _add_to_head(self, node):
        node.prev = self.head
        node.next = self.head.next
        self.head.next.prev = node
        self.head.next = node

    def _remove_node(self, node):
        node.prev.next = node.next
        node.next.prev = node.prev

    def _move_to_head(self, node):
        self._remove_node(node)
        self._add_to_head(node)

    def _remove_tail(self):
        node = self.tail.prev
        self._remove_node(node)
        return node  # 返回节点（需要它的 key 来删哈希表）
```

**⚠️ 易错点：**
1. 淘汰尾部节点时，必须同时从 `self.cache` 字典中删除（所以节点要存 key）
2. 双向链表操作顺序：先断再接，4 个指针都要改
3. 哨兵节点简化边界：永远不需要判断 head/tail 是否为空

**复杂度：** get / put 均为 O(1)

---

## 155. 最小栈 (Min Stack) — 中等

**方法：** 双栈

**核心思路：**
1. `stack` 正常存所有入栈元素
2. `min_stack` 同步存"到当前位置为止的最小值"
3. 这样 `getMin()` 直接返回 `min_stack[-1]`

**为什么 `min_stack` 也要 `pop()`？**

因为两个栈必须**保持一一对应**。

例如依次压入：

```python
push(3)   -> stack=[3]        min_stack=[inf, 3]
push(5)   -> stack=[3,5]      min_stack=[inf, 3, 3]
push(2)   -> stack=[3,5,2]    min_stack=[inf, 3, 3, 2]
```

这时如果弹出 `2`：

```python
stack.pop()
```

那么当前最小值也应该从 `2` 回到 `3`，所以必须同步：

```python
min_stack.pop()
```

否则 `min_stack[-1]` 还会错误地停留在 `2`。

> 记住：`min_stack[i]` 表示 `stack` 前 `i` 个元素对应的最小值，所以两个栈长度必须同步变化。

**哨兵值：**

```python
self.min_stack = [float('inf')]
```

这里先放一个正无穷，方便第一次比较：

```python
min(x, float('inf')) == x
```

这样写可以少处理空栈分支。

**代码：**

```python
class MinStack(object):

    def __init__(self):
        self.stack = []
        self.min_stack = [float('inf')]

    def push(self, x):
        self.stack.append(x)
        self.min_stack.append(min(x, self.min_stack[-1]))

    def pop(self):
        self.stack.pop()
        self.min_stack.pop()

    def top(self):
        return self.stack[-1]

    def getMin(self):
        return self.min_stack[-1]
```

**复杂度：** `push / pop / top / getMin` 都是 O(1)

---