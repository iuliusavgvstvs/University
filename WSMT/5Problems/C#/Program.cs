using System;
using System.Collections.Generic;
using System.IO;

namespace C_
{

  public class RowElement
  {
    public RowElement(int row, List<string> vals)
    {
      Values = vals;
    }
    int row;
    public List<string> Values { get; set; }

    public void AddValue(int column, string value)
    {
      Values.Insert(column, value);
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
          List<string> val = new List<string>();
          val.Add(valueData);
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
        foreach (string val in element.Values)
        {
          await file.WriteAsync(val + " ");
        }
        await file.WriteLineAsync();
      }
    }
    static void Main(string[] args)
    {

      List<RowElement> elements = ReadFromFile(args[0]);
      //   foreach (RowElement elem in elements)
      //   {
      //     foreach (string val in elem.Values)
      //       Console.WriteLine(val);
      //     Console.WriteLine();
      //   }
      string OutputFilePath = "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\C#\\output.txt";
      WriteToFile(OutputFilePath, elements);

    }
  }
}
