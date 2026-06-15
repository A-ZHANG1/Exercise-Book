# LeetCode 热题 100 刷题笔记

---

## Python 刷 LeetCode 常用函数补充：参数类型版

### 1. 基础函数

| 函数 | 常见参数类型 | 返回 | 说明 |
|---|---|---|---|
| `len(x)` | `str / list / tuple / dict / set` | `int` | 求长度 |
| `range(n)` | `int` | `range` 对象 | 常配合 `for` 用 |
| `range(a, b)` | `int, int` | `range` 对象 | `a` 到 `b-1` |
| `range(a, b, step)` | `int, int, int` | `range` 对象 | 带步长 |
| `enumerate(x)` | 可迭代对象：`str / list / tuple` 等 | 枚举器 | 产出 `(下标, 值)` |
| `enumerate(x, start)` | 可迭代对象, `int` | 枚举器 | 下标从 `start` 开始 |
| `zip(a, b)` | 两个可迭代对象 | 枚举器 | 一一配对 |
| `max(x)` | 可迭代对象，元素可比较 | 元素本身 | 例如 `list[int]`、`str` |
| `max(a, b, c)` | 多个可比较值 | 最大值 | 直接比多个值 |
| `min(x)` | 可迭代对象，元素可比较 | 元素本身 | 同 `max` |
| `sum(x)` | 一般是 `list[int] / tuple[int]` | 数字 | 元素通常得是数值 |
| `abs(x)` | `int / float` | 数字 | 绝对值 |
| `sorted(x)` | 可迭代对象 | `list` | 返回排好序的新列表 |
| `reversed(x)` | `list / str / tuple` 等可反转对象 | 迭代器 | 常配合 `list()` 或 `''.join()` |

### 2. 容易混的点

```python
sorted([3, 1, 2])      # [1, 2, 3]，返回新列表
list(reversed([1, 2, 3]))   # [3, 2, 1]
''.join(reversed("abc"))    # "cba"
sum([1, 2, 3])         # 6
max([1, 5, 3])         # 5
max(1, 5, 3)           # 5
```

### 3. 记忆重点

1. `sorted(x)` 返回新列表，`list.sort()` 是原地排序
2. `reversed(x)` 返回迭代器，不是直接返回列表
3. `sum(x)` 一般传数字序列
4. `max(x)` 和 `max(a, b, c)` 都能用

### 4. 常见特殊常量

#### 为什么最大值常写 `float('inf')` 或 `math.inf`

Python 3 的 `int` 没有固定上限，没有内置 `INT_MAX` 这种常量。

所以刷题里想表示“一个足够大的初始值”，通常直接写：

```python
float('inf')
```

或者：

```python
import math
math.inf
```

它们都表示正无穷，适合做最小值比较的初始值：

```python
min_val = float('inf')
max_val = -float('inf')
```

#### 常见特殊常量速查

| 写法 | 含义 | 常见用途 |
|---|---|---|
| `float('inf')` | 正无穷 | 初始化最小值、哨兵 |
| `-float('inf')` | 负无穷 | 初始化最大值 |
| `math.inf` | 正无穷 | 和 `float('inf')` 等价 |
| `-math.inf` | 负无穷 | 和 `-float('inf')` 等价 |
| `math.nan` | 非数字 | 很少用于 LeetCode |
| `True` | 布尔真 | 条件判断 |
| `False` | 布尔假 | 条件判断 |
| `None` | 空值 / 无对象 | 链表、树、默认返回值 |

#### 易记版本

1. Python 3 没有 `INT_MAX`
2. 刷题里“超大值”通常用 `float('inf')`
3. `math.inf` 和 `float('inf')` 基本等价
4. `None` 在题里非常常见，尤其是链表和树

```python
import math

min_val = math.inf
max_val = -math.inf
head = None
found = False
```

### 5. 常用辅助类 / 模块

这些不是 Python 基础关键字，主要来自 **`collections` 模块**，刷 LeetCode 很常用。

```python
from collections import defaultdict, Counter, deque, OrderedDict
```

| 名字 | 类型 | 作用 | 常见场景 |
|---|---|---|---|
| `defaultdict` | 字典子类 | key 不存在时给默认值 | 计数、分组、图 |
| `Counter` | 字典子类 | 统计频次 | 词频、Top K、哈希计数 |
| `deque` | 双端队列 | 头尾都能 O(1) 插入删除 | BFS、单调队列 |
| `OrderedDict` | 有序字典 | 维护插入顺序 | 老题、LRU 思路 |

#### `defaultdict`

```python
from collections import defaultdict

cnt = defaultdict(int)     # 默认值 0
groups = defaultdict(list) # 默认值 []
graph = defaultdict(list)
```

```python
cnt['a'] += 1
groups['x'].append(3)
graph[1].append(2)
```

> 记法：`defaultdict(int)` 很像“自动补 0 的 dict”

#### `Counter`

```python
from collections import Counter

cnt = Counter(nums)
cnt = Counter(s)
```

常见操作：

```python
cnt[x]                 # 元素 x 的个数
cnt.most_common(2)     # 出现最多的前 2 个
```

#### `deque`

```python
from collections import deque

q = deque()
q.append(x)
q.appendleft(x)
q.pop()
q.popleft()
```

> 普通 `list` 头部删除慢，所以 BFS 常用 `deque`

#### `OrderedDict`

```python
from collections import OrderedDict
```

现在 Python 3 里的普通 `dict` 也会保持插入顺序，所以 `OrderedDict` 在 LeetCode 里**没以前常用**。  
但有些题解、老题、LRU 设计题里你还是会看到它。

#### 最常用记忆版

1. `defaultdict(int)`：自动补 0
2. `defaultdict(list)`：自动补空列表
3. `Counter(x)`：直接统计频次
4. `deque`：BFS 队列首选
5. `OrderedDict`：现在较少，但要认识

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

## 155. 最小栈 (Min Stack) — 中等

**方法：** 双栈

**核心思路：**
1. `stack` 正常存所有入栈元素
2. `min_stack` 同步存“到当前位置为止的最小值”
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
