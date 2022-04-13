import sys


class RowElement:
    def __init__(self, row, values):
        self.row = row
        self.values = values

    def addValue(self, column, value):
        self.values[column] = value

    def getValues(self):
        return self.values


class Main:
    def getRow(self, array, row):
        if row > len(array) - 1:
            return None
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
            if row != None:
                row.addValue(columnData, valueData)
            else:
                val = {}
                val[columnData] = valueData
                rowElem = RowElement(rowData, val)
                elements.insert(rowData, rowElem)
        file.close()
        return elements

    def writeToFile(self, filePath, elements):
        file = open(filePath, 'w')
        for x in elements:
            for y in sorted(x.getValues().keys()):
                file.write(x.getValues()[y] + ' ')
            file.write('\n')
        file.close()

    # Swap 2 elements of type RowElement in a list
    def swap(self, list, i, j):
        list[i], list[j] = list[j], list[i]

    # compares two arrays of strings a and b
    # returns 1 if a > b
    # 0 if equal
    # -1 if a < b

    def compareRows(self, a, b):
        mi = len(a)
        if len(b) < mi:
            mi = len(b)
        i = 0
        while (i < mi):
            if a[i] == b[i]:
                i = i+1
            elif a[i] < b[i]:
                return -1
            else:
                return 1
        if len(a) == len(b):
            return 0
        if len(a) < len(b):
            return -1
        return 1

    def partition(self, elements, low, high):
        pivot = elements[high]
        i = low - 1
        for j in range(low, high):
            if self.compareRows(elements[j].getValues(), pivot.getValues()) < 1:
                i = i+1
                self.swap(elements, i, j)
        self.swap(elements, i+1, high)
        return i+1

    def quick_sort(self, elements, low, high):
        if (low < high):
            p = self.partition(elements, low, high)
            self.quick_sort(elements, low, p - 1)
            self.quick_sort(elements, p + 1, high)

    def main(self):
        filePath = sys.argv[1]
        elements = self.readFromFile(filePath)
        outputFilePath = 'C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\Python\\output.txt'
        self.quick_sort(elements, 0, len(elements)-1)
        self.writeToFile(outputFilePath, elements)


app = Main()
app.main()
