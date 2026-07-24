# Python 笔记与脚本

这里保留了早期 Python 学习时积累的小脚本、Jupyter Notebook 和一些零散语法笔记。

## 文件

- `scripts/cleaner.py`：数据清洗脚本
- `notebooks/regexCleaner.ipynb`：正则清洗相关 Notebook

## 常见命令

```python
exit()
```

```bash
jupyter notebook
```

## Jupyter 常见问题

1. cell 执行顺序错乱时，通常需要重启 kernel
2. `OS Error : engine = 'python'` 这类报错要先检查环境和依赖

## 数据清洗

### Regex

- 基本匹配符：[菜鸟教程](https://www.runoob.com/python/python-reg-expressions.html)

### Pandas

- df 操作总览：[参考](https://chf2012.github.io/2017/05/17/%E8%BD%AF%E4%BB%B6%E5%BA%94%E7%94%A8_%E7%A8%8B%E5%BA%8F%E7%BC%96%E7%A8%8B/Python/Python_%E4%B8%93%E9%A2%98%E6%80%BB%E7%BB%93/Python_%E6%95%B0%E6%8D%AE%E5%A4%84%E7%90%86_pandas_old/)
- 细化到单元格的清洗：`iterrows / loc / at`
- 修改单元格的值：`df.set_value(idx, colName, newValue)`

### 正则匹配

1. `re.match(pattern, string)`：从头开始匹配
2. `re.search(pattern, string)`：从任意位置开始匹配

### 字符串模糊匹配

1. 编辑距离
2. `fuzzywuzzy`
3. regex / pattern matching

## Python 语言笔记

### 推荐资源

1. [SICP](http://composingprograms.com/pages/12-elements-of-programming.html)
2. [中文版](https://www.bookstack.cn/read/sicp-py-zh/2.5.md)
3. [CS61A 课件](http://www-inst.eecs.berkeley.edu/~cs61a/fa11/61a-python/content/www/slides/61A-013-Constraints_1pp.pdf)

### 常见知识点

- `None` 不是 `null`
- `==` 比较值，`is` 比较是不是同一个对象
- `dir(obj)` 可以查看实例对象的属性
- 字符串拼接：`str1 + str2`、`sep.join(list_of_str)`
- `set` 支持交并差等集合操作
- `dict` / hashmap 平均查找复杂度通常是 O(1)

```python
from collections import defaultdict

hashmap = defaultdict(int)
hashmap[key] = val
```

## Python 刷题速查

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
sorted([3, 1, 2])           # [1, 2, 3]，返回新列表
list(reversed([1, 2, 3]))   # [3, 2, 1]
''.join(reversed("abc"))    # "cba"
sum([1, 2, 3])              # 6
max([1, 5, 3])              # 5
max(1, 5, 3)                # 5
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

现在 Python 3 里的普通 `dict` 也会保持插入顺序，所以 `OrderedDict` 在 LeetCode 里没以前常用。  
但有些题解、老题、LRU 设计题里你还是会看到它。

#### 最常用记忆版

1. `defaultdict(int)`：自动补 0
2. `defaultdict(list)`：自动补空列表
3. `Counter(x)`：直接统计频次
4. `deque`：BFS 队列首选
5. `OrderedDict`：现在较少，但要认识


### 6. Python 刷题常见 Pitfall

刷题过程中容易踩的坑，按类型汇总。

#### 6.1 语法错误

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| `if` 条件缺冒号 | `if x == 1` | `if x == 1:` | Python 的 `if/for/while/def/class` 末尾必须加 `:` |
| `if` 括号不匹配 | `if(a and b return` | `if a and b:` | 要么不用括号，要么括号必须配对 |
| 布尔值大小写 | `return true / false` | `return True / False` | Python 布尔值首字母大写 |
| 行尾加分号 | `x = 1;` | `x = 1` | Python 不需要分号（不报错但非惯例） |

#### 6.2 整数除法

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| `/` 做索引 | `mid = len(nums)/2` | `mid = len(nums)//2` | `/` 返回 `float`，不能做列表索引 |

```python
# 错误：TypeError: list indices must be integers
nums[len(nums) / 2]

# 正确
nums[len(nums) // 2]
```

#### 6.3 索引越界（Off-by-One）

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| 1-indexed vs 0-indexed | `mid = n//2 + 1` | `mid = n//2` | 数学上中间是第 `n//2+1` 个，Python 0-indexed 要减 1 |

```python
# 长度为奇数的数组，中间元素索引
# 数学：第 (n//2 + 1) 个 → Python 索引：n//2
nums = [1, 2, 3]       # 中间元素 nums[1]，即 len//2 = 1 ✓
nums = [1, 2, 3, 4, 5] # 中间元素 nums[2]，即 len//2 = 2 ✓
```

> **记忆口诀**：数学位置 = Python 索引 + 1，写代码时用 `//2` 就够了。
#### 6.4 整数逐位处理：字符串法也是常规思路

在 Python 刷题里，如果只是想**看一个整数的每一位**，先转成字符串通常更直观，不算“假操作”，而是很常见的写法。

| 方法 | 写法特点 | 适合场景 |
|---|---|---|
| 字符串法 | 简洁直观 | 比较每位大小、计数、判断是否包含某位 |
| 数学拆位法 | 更通用 | 进位、反转数字、构造新数、跨语言写法 |

```python
# 字符串法
s = str(num)
mx = max(s)
mn = min(s)
digit_range = int(mx) - int(mn)
```

```python
# 数学拆位法
x = num
mx = 0
mn = 9
while x > 0:
    d = x % 10
    mx = max(mx, d)
    mn = min(mn, d)
    x //= 10
digit_range = mx - mn
```

> 记法：如果题目只是“遍历数位、比较数位”，Python 里优先想想能不能先 `str(num)`。

#### 6.5 常见代码坑：别覆盖内置函数

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| 把 `str` 当变量名 | `str = ...` | `s = str(num)` | 会覆盖内置 `str()` |
| 把 `min/max/sum` 当变量名 | `min = ...`, `max = ...`, `sum = ...` | `mn/mx/total` | 会覆盖内置函数，后面不能正常调用 |
| 误写成 `string(n)` | `string(n)` | `str(n)` | Python 没有内置 `string()` |
| 直接相减字符 | `max(s) - min(s)` | `int(max(s)) - int(min(s))` | `max(s)` 和 `min(s)` 得到的是字符 |

#### 6.6 维护答案时的思路坑

这类题经常不是“找到一个更优值就继续加”，而是：

1. 如果当前值 **更大**，先更新最佳标准，再**重置答案**
2. 如果当前值 **相等**，再把当前数字加进去

```python
best_range = -1
total = 0

for num in nums:
    s = str(num)
    digit_range = int(max(s)) - int(min(s))

    if digit_range > best_range:
        best_range = digit_range
        total = num
    elif digit_range == best_range:
        total += num
```

> 关键 pitfall：**遇到更大的标准时，答案要重置，不是继续累加。**

### 7. 数组（list）常用函数

Python 列表（`list`）自带的常用方法，刷题里几乎离不开，比如 `append`、`sort`。

| 方法 | 作用 | 返回值 | 是否原地修改 |
|---|---|---|---|
| `list.append(x)` | 末尾添加一个元素 | `None` | 是 |
| `list.extend(iterable)` | 末尾追加另一个可迭代对象的所有元素 | `None` | 是 |
| `list.insert(i, x)` | 在下标 `i` 处插入元素 | `None` | 是 |
| `list.remove(x)` | 删除第一个值为 `x` 的元素（找不到会报错） | `None` | 是 |
| `list.pop(i=-1)` | 删除并返回下标 `i` 的元素，默认删最后一个 | 被删的元素 | 是 |
| `list.clear()` | 清空整个列表 | `None` | 是 |
| `list.index(x)` | 返回第一个值为 `x` 的下标（找不到会报错） | `int` | 否 |
| `list.count(x)` | 统计 `x` 出现的次数 | `int` | 否 |
| `list.sort()` | 原地排序 | `None` | 是 |
| `list.reverse()` | 原地反转 | `None` | 是 |
| `list.copy()` | 浅拷贝出一份新列表 | `list` | 否 |

#### 常见写法

```python
nums = [3, 1, 2]

nums.append(4)       # [3, 1, 2, 4]
nums.extend([5, 6])  # [3, 1, 2, 4, 5, 6]
nums.insert(0, 9)    # [9, 3, 1, 2, 4, 5, 6]
nums.pop()           # 返回 6，nums 变为 [9, 3, 1, 2, 4, 5]
nums.remove(9)       # 按值删除，nums 变为 [3, 1, 2, 4, 5]
nums.sort()          # 原地排序，nums 变为 [1, 2, 3, 4, 5]
nums.reverse()       # 原地反转，nums 变为 [5, 4, 3, 2, 1]
```

#### 最大的坑：这些方法大多返回 `None`

`append / extend / insert / remove / sort / reverse / clear` 都是**原地修改、返回 `None`**，千万不能写成赋值：

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| 用返回值覆盖原列表 | `nums = nums.sort()` | `nums.sort()` | `sort()` 返回 `None`，`nums` 会变成 `None` |
| append 当成能返回新列表 | `nums2 = nums.append(x)` | `nums.append(x)` 再 `nums2 = nums` | `append()` 不返回新列表，也是 `None` |
| pop 和 remove 搞混 | `nums.pop(x)` 想按值删 | `nums.remove(x)` | `pop(i)` 传的是**下标**，`remove(x)` 传的才是**值** |

> 记法：想要"返回新对象"用 `sorted(x)` / `list(reversed(x))`；想要"原地改"用 `x.sort()` / `x.reverse()`。同一个坑，`sort/sorted` 和 `reverse/reversed` 是一对。

#### 切片（slice）也很常用

```python
nums[1:3]     # 下标 1、2（不含 3）
nums[:2]      # 前两个
nums[-2:]     # 最后两个
nums[::-1]    # 整体反转，返回新列表，不改变原列表
nums[:]       # 浅拷贝
```

### 8. 集合（set）常用操作

`set` 是无序、不重复元素的集合，刷题里常用来去重、查重（`in` 判断平均 O(1)），以及处理交并差集问题。

#### 常用方法

| 方法 | 作用 | 返回值 | 是否原地修改 |
|---|---|---|---|
| `set.add(x)` | 添加一个元素 | `None` | 是 |
| `set.remove(x)` | 删除元素 `x`（不存在会报错 `KeyError`） | `None` | 是 |
| `set.discard(x)` | 删除元素 `x`（不存在不报错） | `None` | 是 |
| `set.pop()` | 随机弹出并返回一个元素 | 被删的元素 | 是 |
| `set.clear()` | 清空集合 | `None` | 是 |
| `set.copy()` | 浅拷贝出一份新集合 | `set` | 否 |
| `set.union(other)` | 并集 | 新 `set` | 否 |
| `set.intersection(other)` | 交集 | 新 `set` | 否 |
| `set.difference(other)` | 差集（在 `set` 里但不在 `other` 里） | 新 `set` | 否 |
| `set.symmetric_difference(other)` | 对称差集（只出现在其中一个里） | 新 `set` | 否 |
| `set.update(other)` | 并集结果写回自己 | `None` | 是 |
| `set.intersection_update(other)` | 交集结果写回自己 | `None` | 是 |
| `set.difference_update(other)` | 差集结果写回自己 | `None` | 是 |
| `set.issubset(other)` | 是否是 `other` 的子集 | `bool` | 否 |
| `set.issuperset(other)` | 是否是 `other` 的超集 | `bool` | 否 |
| `set.isdisjoint(other)` | 和 `other` 是否没有交集 | `bool` | 否 |

#### 运算符写法（更常用，等价于上面部分方法）

```python
a = {1, 2, 3}
b = {2, 3, 4}

a | b   # 并集 -> {1, 2, 3, 4}
a & b   # 交集 -> {2, 3}
a - b   # 差集 -> {1}
a ^ b   # 对称差集 -> {1, 4}
```

对应的原地版本：

```python
a |= b   # 等价于 a.update(b)
a &= b   # 等价于 a.intersection_update(b)
a -= b   # 等价于 a.difference_update(b)
a ^= b   # 等价于 a.symmetric_difference_update(b)
```

#### 集合推导式

```python
squares = {x * x for x in range(5)}   # {0, 1, 4, 9, 16}
```

#### 常见坑

| Pitfall | 错误写法 / 误解 | 正确写法 / 说明 |
|---|---|---|
| 空集合写成 `{}` | `s = {}` | `{}` 是空 `dict`，空集合要写 `s = set()` |
| `remove` 找不到元素会报错 | `s.remove(x)`（`x` 不在 `s` 里） | 不确定是否存在就用 `s.discard(x)`，不会报错 |
| set 无序，不能索引 | `s[0]` | `set` 不支持下标访问，要用 `in` 判断，或者先转成 `list` |
| set 元素必须可哈希 | `s.add([1, 2])` | `list` 不可哈希不能放进 `set`；可以用 `tuple` 代替 |
| 依赖 `pop()` 弹出顺序 | 认为 `pop()` 会按插入顺序弹出 | `set` 无序，`pop()` 弹出哪个元素不保证 |

#### 记忆重点

1. 去重、查重优先想 `set`，`in` 判断平均 O(1)
2. 交并差符号：`|` 并集、`&` 交集、`-` 差集、`^` 对称差集
3. 想原地修改就用 `update` 系列方法或 `|= &= -= ^=`
4. `remove` 会报错、`discard` 不会——不确定元素是否存在优先用 `discard`
5. 空集合必须写 `set()`，`{}` 是字典不是集合

### 9. 固定窗口滑动 + 字符计数（Anagram 类问题）

用于"在字符串 `s` 中找长度等于 `len(p)` 的子串，且是 `p` 的字符排列（anagram）"这类题，比如「找到字符串中所有字母异位词」。

#### 核心思路

- 用两个长度 26 的计数数组：`s_cnt` 表示当前窗口（大小固定为 `len(p)`）里每个字符出现的次数，`p_cnt` 表示 `p` 里每个字符出现的次数（从头到尾**只建立一次，之后不再变化**）。
- 判断"当前窗口是不是 `p` 的异位词"，只需要 `s_cnt == p_cnt`。
- 因为窗口大小固定，**不需要 while 循环收缩**，每次滑动只做"一进一出"：减去滑出窗口的字符、加上滑入窗口的字符。

#### 模板代码

```python
def findAnagrams(s: str, p: str) -> List[int]:
    s_len, p_len = len(s), len(p)
    res = []
    if s_len < p_len:
        return res

    s_cnt, p_cnt = [0] * 26, [0] * 26
    for i in range(p_len):
        s_cnt[ord(s[i]) - 97] += 1
        p_cnt[ord(p[i]) - 97] += 1   # 目标计数，只在这里建立一次

    if s_cnt == p_cnt:
        res.append(0)

    for i in range(s_len - p_len):
        s_cnt[ord(s[i]) - 97] -= 1              # 滑出窗口的字符
        s_cnt[ord(s[i + p_len]) - 97] += 1      # 滑入窗口的字符（同样更新 s_cnt！）
        if s_cnt == p_cnt:
            res.append(i + 1)

    return res
```

#### 常见坑

| Pitfall | 错误写法 | 说明 |
|---|---|---|
| 目标计数数组被误改 | 循环里写 `p_cnt[...] += 1` | `p_cnt` 应该固定不变，只能在最初建立一次，滑动过程中只准动 `s_cnt` |
| 建立目标计数时用错字符串 | `p_cnt[ord(s[i]) - 97] += 1` | 应该用 `p[i]`，不是 `s[i]`；写错会让 `p_cnt` 变成 `s` 的前缀计数，判断永远"看起来对" |
| 只减不加 / 只加不减 | 滑动时忘记同时做"移出+移入" | 每次滑动窗口都要**一减一加**，少一步窗口就不对了，`s_cnt` 甚至会出现负数 |

#### 进阶优化：用 diff 计数代替整体比较

每次都比较两个长度 26 的数组是 O(26)。如果不想每步都全量比较，可以维护一个 `diff` 变量，表示"当前窗口计数和目标计数不同的字符种类数"，每次加入/移出字符时只 O(1) 更新 `diff`，`diff == 0` 时就是异位词：

```python
def findAnagrams(s: str, p: str) -> List[int]:
    s_len, p_len = len(s), len(p)
    if s_len < p_len:
        return []

    cnt = [0] * 26          # cnt[c] = s 当前窗口里 c 的个数 - p 里 c 的个数
    for ch in p:
        cnt[ord(ch) - 97] -= 1

    res = []
    diff = sum(1 for x in cnt if x != 0)   # 有多少个字符的计数不为 0

    for i in range(s_len):
        c = ord(s[i]) - 97               # 滑入 s[i]
        if cnt[c] == 0:
            diff += 1
        cnt[c] += 1
        if cnt[c] == 0:
            diff -= 1

        left = i - p_len
        if left >= 0:                    # 滑出 s[left]
            c = ord(s[left]) - 97
            if cnt[c] == 0:
                diff += 1
            cnt[c] -= 1
            if cnt[c] == 0:
                diff -= 1

        if i >= p_len - 1 and diff == 0:
            res.append(i - p_len + 1)

    return res
```

#### 记忆重点

1. 窗口大小**固定** → 不用 while 收缩，直接"一进一出"
2. 目标计数数组（如 `p_cnt`）建立后**不能再变**，滑动时只改窗口计数数组（如 `s_cnt`）
3. 判断异位词 = 两个计数数组相等；追求性能可以用 diff 计数把每步比较降到 O(1)
4. 建立目标计数时字符串别搞混（`p[i]` 不是 `s[i]`）
