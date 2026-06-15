# -*- coding: UTF-8 -*-
import pandas as pd
import xlrd
import re
from fuzzywuzzy import fuzz
from fuzzywuzzy import process

fileName = '编码20150228最终版(查).xlsx'
df = pd.read_excel(fileName)

#读入csv文件和解决中文文件名无法识别问题
#pd.read_csv(filename, engine = 'python')

spec = '规格型号'

class Cleaner:
    def clean(self):
        for idx,row in df.iterrows():
            cell = df.loc[idx,spec] #str object
            #去除首尾空格
            df.set_value(idx,spec,cell.strip())
            #去除中间空格
            if ' ' in cell:
                df.set_value(idx,spec,''.join(cell.split()))
            # 小写字母装换
            if (re.search(r'.*[a-z].*',cell) != None):
                #去除包含单位
                if(re.match(r'^((?!kW).)*$', cell) != None) and (re.match(r'^((?!kΩ).)*$', cell) != None):
                        df.set_value(idx,spec,cell.upper())
            # 替换包含错误下划线模式
            if (re.search(r'[_]',cell) != None):
                df.set_value(idx,spec,cell.replace('_', '-'))
            if (re.search(r'[－]',cell) != None):
                df.set_value(idx,spec,cell.replace('－', '-'))

        #检查包含全角数字字母dash
        for idx,row in df.iterrows():
            cell = df.loc[idx,spec] #str object
            if (re.search(r'[^0-9a-zA-Z-]',cell) != None):
                print(cell)
                mis = cell

        print(' ' in mis.strip())

        print(df)

        #写入excel
        df.to_excel('excel_output.xlsx')

        #regex测试
        eg = " 2000W"
        # eg = " SCREEN"
        #去除首尾空格
        # if ' ' in eg:
        #     eg = eg.strip()
        # print(eg)

        #去除中间空格
        # eg = ''.join(eg.split())
        # print(eg)
        # print(re.match(r'[0-9]*', eg) == True)

        #包含小写字母
        # print(re.match(r'.*[a-z].*',eg))

        #包含特定字符
        # print(re.match(r'^((?!kW).)*$',eg))

        #检查包含半角数组符号
        # print(re.search(r'[^0-9a-zA-Z]+',eg))

supplierName = '供应商名称'
supplierNameDic = '供应商简称'

#按照单元格内容删除指定行
class Dropper:
    def drop(self):

        # indexNames = df[ (df[supplierName] >= 30) & (dfObj['Country'] == 'India') ].index

        indexNamesNull = df[pd.isnull(df[supplierName])].index
        df.drop( indexNamesNull , inplace=True) #inplace=True:在原对象上修改

        indexNamesNo = df[df[supplierName] =='no'].index
        df.drop( indexNamesNo , inplace=True)

        # print(df)

#规格型号模糊匹配
scoreList = []
class FuzzyMatch:
    def fuzzyMatch(self):
        for sn in set(df[supplierName].str.strip()):
            for sn2 in set(df[supplierNameDic].str.strip()):
                score = fuzz.ratio(sn, sn2)
                scoreList.append(score)
        print(scoreList)

#规格模式匹配
patternList = [r'^((?!kW).)*$']
class PatternMatch:
    def notInPattern(self):
        for sn in set(df[spec].str.strip()):
            for p in patternList:
                if re.match(p, sn) != None:
                    scoreList.append(sn)
        print(scoreList)

#统计结果及展示
class Statistics:
    def getStatistics(self):
        typeStandards = df.groupby(spec).sum()
        print(typeStandards)

        # df_out = pd.concat([df.set_index('index'),df.set_index('index').agg(['max','min','mean'])]).rename(index={'max':'Max','min':'Min','mean':'Average'}).reset_index()


# c = Cleaner()
# c.clean()

# d = Dropper()
# d.drop()
#
# fm = FuzzyMatch()
# fm.fuzzyMatch()

st = Statistics()
st.getStatistics()

# st = PatternMatch()
# st.notInPattern()
