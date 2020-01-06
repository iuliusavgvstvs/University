package controller;

import domain.Raport;
import domain.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.comparators.AverageGradeComparator;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EditRaportController {
    @FXML
    BarChart chart;
    @FXML
    CategoryAxis categoryAxis;
    @FXML
    NumberAxis numberAxis;
    @FXML
    TableView<Raport> table1;
    @FXML
    TableColumn<Raport,String> IdColumn;
    @FXML
    TableColumn<Raport, String> firstNameColumn;
    @FXML
    TableColumn<Raport, String> lastNameColumn;
    @FXML
    TableColumn<Raport, String> groupColumn;
    @FXML
    TableColumn<Raport, String> gradeColumn;

    Stage dialogStage;
    Iterable<Raport> raports;

    public void setService(Stage stage, Iterable<Raport> rap, boolean isHomework){
        this.dialogStage=stage;
        this.raports=rap;
        AverageGradeComparator comp = new AverageGradeComparator();

        if(!isHomework) {
            ArrayList sortedRaports = sortRaports(rap, comp, true);
            populateTable(sortedRaports);
            populateChart(sortedRaports);
        }
        else {
            numberAxis.setLabel("Average grade");
            categoryAxis.setLabel("Homework ID");
            firstNameColumn.setText("Start Week");
            lastNameColumn.setText("Deadline Week");
            groupColumn.setText("Description");
            ArrayList sortedRaports = sortRaports(rap, comp, false);
            populateTableHomeworks(sortedRaports);
            populateChartHomeworks(sortedRaports);
            table1.getItems().get(1);

        }

    }




    @FXML
    public void initialize(){ }

    public ArrayList sortRaports(Iterable<Raport> raports,  Comparator comp, boolean descending){
        HashMap<Integer,Raport> hmap = new HashMap<>();
        Iterator<Raport> it = raports.iterator();
        ArrayList list = new ArrayList<>();
        while(it.hasNext())
            list.add(it.next());
        Collections.sort(list,comp);
        if(descending==true)
            Collections.reverse(list);
        for(Object r: list){
            Raport rp = (Raport) r;
            if(rp.getSt()!=null)
            hmap.put(Integer.parseInt((String)rp.getSt().getId()),rp);
            else
                hmap.put(Integer.parseInt((String)rp.getHm().getId()),rp);
        }
        return list;
    }

    public void populateTable(ArrayList raports){
        IdColumn.setCellValueFactory((new PropertyValueFactory<Raport,String>("id")));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("lastName"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("group"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("average"));
        table1.getItems().setAll(raports);
    }

    private void populateTableHomeworks(ArrayList raports) {
        IdColumn.setCellValueFactory((new PropertyValueFactory<Raport,String>("id")));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("startWeek"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("deadlineWeek"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("description"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<Raport, String>("average"));
        table1.getItems().setAll(raports);
    }

    public void populateChart(ArrayList raports){
        for (Object obj : raports) {
            Raport r= (Raport) obj;
            XYChart.Series set1 = new XYChart.Series();
            String name = r.getSt().getFirstName() + " " + r.getSt().getLastName();
            set1.setName(Double.toString(r.getAverage()));
            set1.getData().add(new XYChart.Data(name, (Number) r.getAverage()));
            set1.dataProperty();
            chart.getData().addAll(set1);
        }
    }
    private void populateChartHomeworks(ArrayList raports) {
        for (Object obj : raports) {
            Raport r= (Raport) obj;
            XYChart.Series set1 = new XYChart.Series();
            String name =(String) r.getHm().getId();
            set1.setName(Double.toString(r.getAverage()));
            set1.getData().add(new XYChart.Data(name, r.getAverage()));
            set1.dataProperty();
            chart.getData().addAll(set1);
        }
    }

}
