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
