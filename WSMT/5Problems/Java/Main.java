import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class RowElement {
  int row;
  ArrayList<String> values = new ArrayList<>();

  RowElement(int row, ArrayList<String> values) {
    this.row = row;
    this.values = values;
  }

  void addValue(int column, String value) {
    this.values.add(column, value);
  }

  ArrayList<String> getValues() {
    return this.values;
  }
}

class Main {

  private static RowElement getRow(ArrayList<RowElement> array, int row) {
    try {
      return array.get(row);
    } catch (Exception e) {
      return null;
    }
  }

  private static ArrayList<RowElement> readFromFile(String filePath) throws IOException {
    ArrayList<RowElement> elements = new ArrayList<>();
    File file = new File(filePath);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String data;
    String[] parsedData;

    while ((data = br.readLine()) != null) {
      parsedData = data.split(" ");
      int rowData = Integer.valueOf(parsedData[0]);
      int columnData = Integer.valueOf(parsedData[1]);
      String valueData = parsedData[2];
      RowElement row = getRow(elements, rowData);
      if (row != null) {
        int index = elements.indexOf(row);
        row.addValue(columnData, valueData);
        elements.set(index, row);
      } else {
        ArrayList<String> val = new ArrayList<>();
        val.add(columnData, valueData);
        elements.add(new RowElement(rowData, val));
      }
    }
    br.close();
    return elements;
  }

  private static void writeToFile(String filePath, ArrayList<RowElement> elements) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    for (int i = 0; i < elements.size(); i++) {
      RowElement row = elements.get(i);
      for (int j = 0; j < row.getValues().size(); j++) {
        writer.append(row.getValues().get(j));
        writer.append(' ');
      }
      writer.append('\n');
    }
    writer.close();
  }

  // Swap 2 elements of type RowElement in an ArrayList<RowElement>
  static void swap(ArrayList<RowElement> elements, int i, int j) {
    RowElement temp = elements.get(i);
    elements.set(i, elements.get(j));
    elements.set(j, temp);
  }

  /*
   * compares two strings
   * returns 1 if a > b
   * 0 if equal
   * -1 if a < b
   */
  static int compareRows(ArrayList<String> a, ArrayList<String> b) {
    for (int i = 0; i < a.size(); i++) {
      String x = a.get(i);
      String y = b.get(i);
      if (x != null && y != null) {
        int result = x.compareTo(y);
        if (result > 0)
          return 1;
        if (result < 0)
          return -1;
      }
    }
    return 0;
  }

  static int partition(ArrayList<RowElement> elements, int low, int high) {
    RowElement pivot = elements.get(high);
    int i = (low - 1);
    for (int j = low; j <= high - 1; j++) {
      if (compareRows(elements.get(j).getValues(), pivot.getValues()) < 1) {
        i++;
        swap(elements, i, j);
      }
    }
    swap(elements, i + 1, high);
    return (i + 1);
  }

  static void quickSort(ArrayList<RowElement> elements, int low, int high) {
    if (low < high) {
      int pi = partition(elements, low, high);
      quickSort(elements, low, pi - 1);
      quickSort(elements, pi + 1, high);
    }
  }

  public static void main(String[] args) throws IOException {
    ArrayList<RowElement> elements = readFromFile(args[0]);

    // TO DO: Change output file path
    String outputFilePath = "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\Java\\output.txt";

    quickSort(elements, 0, elements.size() - 1);
    writeToFile(outputFilePath, elements);
  }
}