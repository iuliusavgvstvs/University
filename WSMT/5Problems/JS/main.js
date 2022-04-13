const fs = require("fs");
const arguments = process.argv;

class RowElement {
  constructor(row, values) {
    this.row = row;
    this.values = values;
  }

  addValue(column, value) {
    this.values[column] = value;
  }

  getValues() {
    return this.values;
  }
}

class Main {
  getRow(array, row) {
    if (row > array.length - 1) {
      return null;
    }
    return array[row];
  }

  readFromFile(filePath) {
    let elements = [];
    const file = fs.readFileSync(filePath, "utf-8");
    file.split(/\r?\n/).forEach((line) => {
      const parsedData = line.split(" ");
      const rowData = parsedData[0];
      const columnData = parsedData[1];
      const valueData = parsedData[2];
      let row = this.getRow(elements, rowData);
      if (row) {
        row.addValue(columnData, valueData);
      } else {
        let val = [];
        val[columnData] = valueData;
        const rowElem = new RowElement(rowData, val);
        elements[rowData] = rowElem;
      }
    });
    return elements;
  }

  writeToFile(filePath, elements) {
    fs.writeFileSync(filePath, "");
    elements.map((el) => {
      el.getValues().map((val) => {
        fs.appendFileSync(filePath, `${val} `);
      });
      fs.appendFileSync(filePath, "\n");
    });
  }

  // Swap 2 elements of type RowElement in a list
  swap(list, i, j) {
    const temp = list[i];
    list[i] = list[j];
    list[j] = temp;
  }

  /*
   * compares two arrays of strings a and b
   * returns 1 if a > b
   * 0 if equal
   * -1 if a < b
   */
  compareRows(a, b) {
    let mi = a.length;
    if (b.length < mi) mi = b.length;
    let i = 0;
    while (i < mi) {
      if (a[i].localeCompare(b[i]) === 0) i++;
      else return a[i].localeCompare(b[i]);
    }
    if (a.length === b.length) return 0;
    if (a.length < b.length) return -1;
    return 1;
  }

  partition(elements, low, high) {
    const pivot = elements[high];
    let i = low - 1;
    for (let j = low; j <= high - 1; j++) {
      if (this.compareRows(elements[j].getValues(), pivot.getValues()) < 1) {
        i++;
        this.swap(elements, i, j);
      }
    }
    this.swap(elements, i + 1, high);
    return i + 1;
  }

  quickSort(elements, low, high) {
    if (low < high) {
      const pi = this.partition(elements, low, high);
      this.quickSort(elements, low, pi - 1);
      this.quickSort(elements, pi + 1, high);
    }
  }

  main(args) {
    const elements = this.readFromFile(args[2]);
    const outputFilePath =
      "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\JS\\output.txt";

    this.quickSort(elements, 0, elements.length - 1);
    this.writeToFile(outputFilePath, elements);
  }
}

const main = new Main();
main.main(arguments);
