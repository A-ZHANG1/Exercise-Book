

### 6. Python 刷题常见 Pitfall

刷题过程中容易踩的坑，按类型汇总。

#### 6.1 语法错误

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| if 条件缺冒号 | `if x == 1` | `if x == 1:` | Python 的 if/for/while/def/class 末尾必须加 : |
| if 括号不匹配 | `if(a and b return` | `if a and b:` | 要么不用括号，要么括号必须配对 |
| 布尔值大小写 | `return true / false` | `return True / False` | Python 布尔值首字母大写 |
| 行尾加分号 | `x = 1;` | `x = 1` | Python 不需要分号（不报错但非惯例） |

#### 6.2 整数除法

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| / 做索引 | `mid = len(nums)/2` | `mid = len(nums)//2` | / 返回 loat，不能做列表索引 |

`python
# 错误：TypeError: list indices must be integers
nums[len(nums) / 2]

# 正确
nums[len(nums) // 2]
`

#### 6.3 索引越界（Off-by-One）

| Pitfall | 错误写法 | 正确写法 | 说明 |
|---|---|---|---|
| 1-indexed vs 0-indexed | `mid = n//2 + 1` | `mid = n//2` | 数学上中间是第 
//2+1 个，Python 0-indexed 要减 1 |

`python
# 长度为奇数的数组，中间元素索引
# 数学：第 (n//2 + 1) 个 → Python 索引：n//2
nums = [1, 2, 3]       # 中间元素 nums[1]，即 len//2 = 1 ✓
nums = [1, 2, 3, 4, 5] # 中间元素 nums[2]，即 len//2 = 2 ✓
`

> **记忆口诀**：数学位置 = Python 索引 + 1，写代码时用 //2 就够了。