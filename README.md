# Exercise-Book

算法、语言语法和刷题笔记的统一练习仓库。现在把原来的 Java 练习、Python 脚本/语法笔记，以及 LeetCode Hot 100 笔记整理到一个仓库里维护。

## 目录结构

```text
Exercise-Book/
├── java/
│   ├── src/                    # Java 算法代码
│   ├── resources/              # Java 基础笔记（如 Stack.md、javaBasic.md）
│   ├── 题型总结/                # 旧版 Java 语法/专题笔记
├── python/
│   ├── README.md               # Python 语法/数据清洗/脚本笔记
│   ├── scripts/
│   │   └── cleaner.py
│   └── notebooks/
│       └── regexCleaner.ipynb
└── leetcode/
   └── leetcode_hot100_notes.md # LeetCode Hot 100 + Python 刷题速查
```

## 内容说明

### Java

- 以算法题、数据结构和题型总结为主
- 早期内容主要围绕《程序员代码面试指南》和常见面试题

### Python

- 保留早期 Python 脚本和数据清洗相关笔记
- 补充常见语法、`collections` 工具、特殊常量等刷题常用知识

### LeetCode

- 当前重点维护 `leetcode_hot100_notes.md`
- 包含刷题过程中的易错点、题解思路和 Python 速查笔记

## 后续整理原则

1. 新的刷题笔记优先放在 `leetcode/`
2. 语言基础和工具类总结分别放在 `java/`、`python/`
3. 不再提交 `.idea`、`out`、`.DS_Store` 一类 IDE 或系统产物
