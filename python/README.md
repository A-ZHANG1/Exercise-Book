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
