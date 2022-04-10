from asyncio.windows_events import NULL
import sys


class RowElement:
    def __init__(self, row, values):
        self.row = row
        self.values = values

    def addValue(self, val):
        self.values.append(val)


class Main:
    def getRow(self, array, row):
        if row > len(array) - 1:
            return NULL
        return array[row]

    def readFromFile(self, filePath):
        elements = []
        file = open(filePath, 'r')
        for line in file:
            parsedData = line.split()
            rowData = int(parsedData[0])
            columnData = int(parsedData[1])
            valueData = parsedData[2]
            row = self.getRow(elements, rowData)
            if row != NULL:
                row.addValue(valueData)
                index = elements.index(row)
                elements.pop(index)
                elements.insert(index, row)
            else:
                val = []
                val.insert(columnData, valueData)
                rowElem = RowElement(rowData, val)
                elements.insert(rowData, rowElem)
        file.close()
        return elements

    def writeToFile(self, filePath, elements):
        file = open(filePath, 'w')
        for x in elements:
            for y in x.values:
                file.write(y + ' ')
            file.write('\n')
        file.close()

    def compareRows(self, a, b):
        mi = len(a)
        if len(b) < mi:
            mi = len(b)
        for i in range(0, mi):
            x = a[i]
            y = b[i]
            if x > y:
                return 1
            if x < y:
                return -1
        return 0

    def partition(self, start, end, array):
        pivot_index = start
        pivot = array[pivot_index]
        while start < end:
            while start < len(array) and (self.compareRows(array[start].values, pivot.values) < 1):
                start += 1
            while self.compareRows(array[end].values, pivot.values) > 0:
                end -= 1
            if(start < end):
                array[start], array[end] = array[end], array[start]
        array[end], array[pivot_index] = array[pivot_index], array[end]
        return end

    def quick_sort(self, start, end, array):
        if (start < end):
            p = self.partition(start, end, array)
            self.quick_sort(start, p - 1, array)
            self.quick_sort(p + 1, end, array)

    def main(self):
        filePath = sys.argv[1]
        #filePath = 'C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\input1.txt'
        elements = self.readFromFile(filePath)
        outputFilePath = 'C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\Python\\output.txt'
        self.quick_sort(0, len(elements)-1, elements)
        self.writeToFile(outputFilePath, elements)


app = Main()
app.main()
