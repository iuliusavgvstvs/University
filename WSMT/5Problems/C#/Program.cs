using System;
using System.Collections.Generic;
using System.IO;

namespace C_
{

  public class RowElement
  {
    public RowElement(int row, SortedDictionary<int, string> vals)
    {
      Row = row;
      Values = vals;
    }
    public int Row { get; }
    public SortedDictionary<int, string> Values { get; set; }

    public void AddValue(int column, string value)
    {
      Values.Add(column, value);
    }

  }

  class Program
  {

    static RowElement GetRow(List<RowElement> array, int row)
    {
      if (array.Count > row)
      {
        return array[row];
      }
      return null;
    }

    static List<RowElement> ReadFromFile(string FilePath)
    {
      List<RowElement> elements = new List<RowElement>();
      string[] data = System.IO.File.ReadAllLines(FilePath);
      foreach (string line in data)
      {
        string[] parsedData = line.Split(" ");
        int rowData = Int32.Parse(parsedData[0]);
        int columnData = Int32.Parse(parsedData[1]);
        string valueData = parsedData[2];
        RowElement row = GetRow(elements, rowData);
        if (row != null)
        {
          int index = elements.IndexOf(row);
          row.AddValue(columnData, valueData);
          elements[index] = row;
        }
        else
        {
          SortedDictionary<int, string> val = new SortedDictionary<int, string>();
          val.Add(columnData, valueData);
          elements.Add(new RowElement(rowData, val));
        }
      }
      return elements;
    }

    async static void WriteToFile(string FilePath, List<RowElement> elements)
    {
      using StreamWriter file = new(FilePath);

      foreach (RowElement element in elements)
      {
        foreach (KeyValuePair<int, string> kvp in element.Values)
        {
          await file.WriteAsync(kvp.Value + " ");
        }
        await file.WriteLineAsync();
      }
    }

    // Swap 2 elements of type RowElement in a List<RowElement>
    static void Swap(List<RowElement> list, int i, int j)
    {
      RowElement temp = list[i];
      list[i] = list[j];
      list[j] = temp;
    }


    /*
      * compares two arrays of strings a and b
      * returns 1 if a > b
      * 0 if equal
      * -1 if a < b
      */
    static int CompareRows(SortedDictionary<int, string> a, SortedDictionary<int, string> b)
    {
      int mi = a.Count;
      if (b.Count < mi)
        mi = b.Count;
      int i = 0;
      while (i < mi)
      {
        if (String.Compare(a[i], b[i]) == 0)
          i++;
        else if (String.Compare(a[i], b[i]) < 0)
          return -1;
        else
          return 1;
      }
      if (a.Count == b.Count)
        return 0;
      if (a.Count < b.Count)
        return -1;
      return 1;
    }

    static int Partition(List<RowElement> elements, int low, int high)
    {
      RowElement pivot = elements[high];
      int i = (low - 1);
      for (int j = low; j <= high - 1; j++)
      {
        if (CompareRows(elements[j].Values, pivot.Values) < 1)
        {
          i++;
          Swap(elements, i, j);
        }
      }
      Swap(elements, i + 1, high);
      return (i + 1);
    }

    static void QuickSort(List<RowElement> elements, int low, int high)
    {
      if (low < high)
      {
        int pi = Partition(elements, low, high);
        QuickSort(elements, low, pi - 1);
        QuickSort(elements, pi + 1, high);
      }
    }
    static void Main(string[] args)
    {
      List<RowElement> elements = ReadFromFile(args[0]);
      string OutputFilePath = "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\C#\\output.txt";
      QuickSort(elements, 0, elements.Count - 1);
      WriteToFile(OutputFilePath, elements);
    }
  }
}
