const fs = require("fs");
const arguments = process.argv;

class RowElement {
  constructor(row, values) {
    this.row = row;
    this.values = values;
  }
  addValue(val) {
    this.values.push(val);
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
        row.addValue(valueData);
      } else {
        let val = [];
        val.splice(columnData, 0, valueData);
        const rowElem = new RowElement(rowData, val);
        elements.splice(rowData, 0, rowElem);
      }
    });
    return elements;
  }

  writeToFile(filePath, elements) {
    fs.writeFileSync(filePath, "");
    elements.map((el) => {
      el.values.map((val) => {
        fs.appendFileSync(filePath, `${val} `);
      });
      fs.appendFileSync(filePath, "\n");
    });
  }

  main(args) {
    const elements = this.readFromFile(args[2]);
    const outputFilePath =
      "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\JS\\output.txt";
    this.writeToFile(outputFilePath, elements);
    console.log(elements);
  }
}
const main = new Main();

main.main(arguments);
