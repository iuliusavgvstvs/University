﻿using System;
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
    public int Row { get; }
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
    static int CompareRows(List<string> a, List<string> b)
    {
      for (int i = 0; i < a.Count; i++)
      {
        string x = a[i];
        if (i >= b.Count)
        {
          break;
        }
        string y = b[i];
        int result = String.Compare(x, y);
        if (result > 0)
          return 1;
        if (result < 0)
          return -1;
      }
      return 0;
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
