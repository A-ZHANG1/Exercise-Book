# LeetCode 热题 100 刷题笔记

---

这里专门记录 **Hot 100 题解、思路和易错点**。  
Python 语法、常用函数、`collections` 工具和特殊常量已整理到 `python/README.md`。

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
