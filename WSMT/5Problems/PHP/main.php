<?php

class RowElement
{
  public $row;
  public $values;
  public function __construct($row, $values)
  {
    $this->row = $row;
    $this->values = $values;
  }
  public function addValue($column, $value)
  {
    $this->values[$column] = $value;
  }

  public function getValues()
  {
    return $this->values;
  }
}

class Main
{
  public function getRow($array, $row)
  {
    if (!array_key_exists($row, $array)) {
      return null;
    }
    return $array[$row];
  }

  public function readFromFile($filePath)
  {
    $handle = fopen($filePath, "r");
    $elements = array();
    if ($handle) {
      while (($line = fgets($handle)) !== false) {
        $parsedData = explode(" ", $line);
        $rowData = $parsedData[0];
        $columnData = $parsedData[1];
        $valueData = $parsedData[2];
        $row = $this->getRow($elements, $rowData);
        if ($row) {
          $row->addValue($columnData, $valueData);
        } else {
          $val = array();
          $val[$columnData] = $valueData;
          $rowElem = new RowElement($rowData, $val);
          $elements[$rowData] = $rowElem;
        }
      }
      fclose($handle);
    }
    return $elements;
  }

  public function writeToFile($filePath, $elements)
  {
    $file = fopen($filePath, "w", 1);
    fwrite($file, "");
    $file = fopen($filePath, "a", 1);
    for ($i = 0; $i < count($elements); $i++) {
      $values = $elements[$i]->getValues();
      for ($j = 0; $j < count($values); $j++) {
        fwrite($file, $values[$j]);
        fwrite($file, " ");
      }
      fwrite($file, "\n");
    }
  }

  // Swap 2 elements of type RowElement in a list
  public function swap(&$list, $i, $j)
  {
    $temp = $list[$i];
    $list[$i] = $list[$j];
    $list[$j] = $temp;
    // [$list[$i], $list[$j]] = [$list[$j], $list[$i]];
  }

  /*
   * compares two arrays of strings a and b
   * returns 1 if a > b
   * 0 if equal
   * -1 if a < b
   */
  public function compareRows($a, $b)
  {
    $mi = count($a);
    if (count($b) < $mi) {
      $mi = count($b);
    }
    $i = 0;
    while ($i < $mi) {
      if (strcmp($a[$i], $b[$i]) === 0) $i++;
      else if (strcmp($a[$i], $b[$i]) < 0) return -1;
      else return 1;
    }
    if (count($a) === count($b)) return 0;
    if (count($a) < count($b)) return -1;
    return 1;
  }

  public function partition(&$elements, $low, $high)
  {
    $pivot = $elements[$high];
    $i = $low - 1;
    for ($j = $low; $j <= $high - 1; $j++) {
      if ($this->compareRows($elements[$j]->getValues(), $pivot->getValues()) < 1) {
        $i++;
        $this->swap($elements, $i, $j);
      }
    }
    $this->swap($elements, $i + 1, $high);
    return $i + 1;
  }

  public function quickSort(&$elements, $low, $high)
  {
    if ($low < $high) {
      $pi = $this->partition($elements, $low, $high);
      $this->quickSort($elements, $low, $pi - 1);
      $this->quickSort($elements, $pi + 1, $high);
    }
  }
  public function run()
  {
    global $argv;
    $elements = $this->readFromFile($argv[1]);
    $outputFilePath = "C:\\Users\\Iuliu\\OneDrive\\Desktop\\Things\\GitHubRepos\\University\\WSMT\\5Problems\\PHP\\output.txt";
    $this->quickSort($elements, 0, count($elements) - 1);
    $this->writeToFile($outputFilePath, $elements);
  }
}

$main = new Main();
$main->run();
