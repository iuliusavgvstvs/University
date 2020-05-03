package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Copil;
import model.Proba;
import model.TableEntity;
import model.User;
import model.exceptions.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ChatException;
import services.IObserver;
import services.IService;

import java.util.ArrayList;
import java.util.List;

public class MainAppUI implements IObserver {

    private static final Logger logger = LogManager.getLogger();
    ObservableList<TableEntity> data = FXCollections.observableArrayList();
    ObservableList<TableEntity> datab = FXCollections.observableArrayList();
    @FXML
    ComboBox<String> categoryComboBox;
    @FXML
    ComboBox<String> ageCombo;
    @FXML
    TableColumn<TableEntity, Integer> idColumn;
    @FXML
    TableColumn<TableEntity, String> fnameColumn;
    @FXML
    TableColumn<TableEntity, String> lnameColumn;
    @FXML
    TableColumn<TableEntity, Integer> ageColumn;
    @FXML
    TableColumn<TableEntity, String> eventColumn;
    @FXML
    TableColumn<TableEntity, Integer> idColumnb;
    @FXML
    TableColumn<TableEntity, String> fnameColumnb;
    @FXML
    TableColumn<TableEntity, String> lnameColumnb;
    @FXML
    TableColumn<TableEntity, Integer> ageColumnb;
    @FXML
    TableColumn<TableEntity, String> eventColumnb;
    @FXML
    TableView<TableEntity> tabel;
    @FXML
    TableView<TableEntity> tabel2;
    @FXML
    TextField fnameField;
    @FXML
    TextField lnameField;
    @FXML
    Spinner<Integer> ageSpinner;
    @FXML
    CheckBox m50;
    @FXML
    CheckBox m100;
    @FXML
    CheckBox m1000;
    @FXML
    CheckBox m1500;
    @FXML
    CheckBox m50b;
    @FXML
    CheckBox m100b;
    @FXML
    CheckBox m1000b;
    @FXML
    CheckBox m1500b;
    @FXML
    Label infoLabel;
    @FXML
    Button addBtn;
    private IService server;
    private Stage stage;
    private User user;
    private int minAge, maxAge;

    public void setServices(IService serv, Stage stg, User u) throws ChatException {
        this.server = serv;
        this.stage = stg;
        this.user = u;
        initModel();
        initModelb();
    }

    private void setCategories() {
        ArrayList<String> list = new ArrayList<>();
        String cat1 = "6-8 years";
        String cat2 = "9-11 years";
        String cat3 = "12-15 years";
        list.add(cat1);
        list.add(cat2);
        list.add(cat3);
        categoryComboBox.getItems().setAll(list);
        categoryComboBox.setValue(list.get(0));
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("6");
        list2.add("7");
        list2.add("8");
        list2.add("9");
        list2.add("10");
        list2.add("11");
        list2.add("12");
        list2.add("13");
        list2.add("14");
        list2.add("15");
        ageCombo.getItems().setAll(list2);
        ageCombo.setValue(list2.get(5));

    }

    @FXML
    public void initialize() {
        infoLabel.setText("");
        setCategories();
        populateTable();
        populateTableb();
        setSpinnerAge();
        m50.setDisable(true);
        m1500.setDisable(true);
        ageSpinner.valueProperty().addListener((v, oldValue, newValue) -> refreshEvents());
    }

    public void initModel() throws ChatException {
        ArrayList<TableEntity> list = getData();
        List<TableEntity> tableList = new ArrayList<>(list);
        data.setAll(tableList);
    }

    public void initModelb() throws ChatException {
        ArrayList<TableEntity> list = getDatab();
        List<TableEntity> tableList = new ArrayList<>(list);
        datab.setAll(tableList);
        refreshEventsb();
    }

    public void populateTable() {
        populate(idColumn, fnameColumn, lnameColumn, ageColumn, eventColumn, tabel, data);
    }

    public void populateTableb() {
        populate(idColumnb, fnameColumnb, lnameColumnb, ageColumnb, eventColumnb, tabel2, datab);
    }

    private void populate(TableColumn<TableEntity, Integer> idColumnb, TableColumn<TableEntity, String> fnameColumnb, TableColumn<TableEntity, String> lnameColumnb, TableColumn<TableEntity, Integer> ageColumnb, TableColumn<TableEntity, String> eventColumnb, TableView<TableEntity> tabel2, ObservableList<TableEntity> datab) {
        idColumnb.setCellValueFactory((new PropertyValueFactory<>("id")));
        fnameColumnb.setCellValueFactory(new PropertyValueFactory<>("copilFname"));
        lnameColumnb.setCellValueFactory(new PropertyValueFactory<>("copilLname"));
        ageColumnb.setCellValueFactory(new PropertyValueFactory<>("copilAge"));
        eventColumnb.setCellValueFactory(new PropertyValueFactory<>("distances"));
        tabel2.setItems(datab);
    }

    private void getAge() {
        String[] text = categoryComboBox.getSelectionModel().getSelectedItem().replaceAll("[a-zA-Z ]", "").split("-");
        this.minAge = Integer.parseInt(text[0]);
        this.maxAge = Integer.parseInt(text[1]);

    }

    private ArrayList<TableEntity> getData() throws ChatException {
        ArrayList<TableEntity> all = new ArrayList<>();
        getAge();
        for (Copil c : this.server.findByAge(minAge, maxAge)) {
            Proba[] probas = server.getByCopilID(c.getId());
            if (probas != null) {
                if (probas.length == 1)
                    all.add(new TableEntity(c.getId(), c, probas[0]));
                if (probas.length == 2) {
                    all.add(new TableEntity(c.getId(), c, probas[0], probas[1]));
                }
            }
        }
        return all;
    }

    private ArrayList<TableEntity> getDatab() throws ChatException {
        ArrayList<TableEntity> all = new ArrayList<>();
        int age = Integer.parseInt(ageCombo.getSelectionModel().getSelectedItem());
        for (Copil c : server.findByAge(age, age)) {
            Proba[] probas = server.getByCopilID(c.getId());
            if (probas != null) {
                if (age < 9) {
                    if (m50b.isSelected() && !m100b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 50)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (!m50b.isSelected() && m100b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 100)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (m50b.isSelected() && m100b.isSelected())
                        if (probas.length == 2)
                            all.add(new TableEntity(c.getId(), c, probas[0], probas[1]));
                } else if (age < 12) {
                    if (m100b.isSelected() && !m1000b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 100)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (!m100b.isSelected() && m1000b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 1000)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (m100b.isSelected() && m1000b.isSelected())
                        if (probas.length == 2)
                            all.add(new TableEntity(c.getId(), c, probas[0], probas[1]));
                } else {
                    if (m1000b.isSelected() && !m1500b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 1000)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (!m1000b.isSelected() && m1500b.isSelected())
                        if (probas.length == 1 && probas[0].getDistanta() == 1500)
                            all.add(new TableEntity(c.getId(), c, probas[0]));
                    if (m1000b.isSelected() && m1500b.isSelected())
                        if (probas.length == 2)
                            all.add(new TableEntity(c.getId(), c, probas[0], probas[1]));
                }
            }
        }
        return all;
    }

    private void setSpinnerAge() {
        ageSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(6, 15, 11));
    }

    public void refreshEvents() {
        int age = ageSpinner.getValue();
        refresh(age, m50, m100, m1000, m1500);
    }

    private void refresh(int age, CheckBox m50, CheckBox m100, CheckBox m1000, CheckBox m1500) {
        if (age < 9) {
            m50.setDisable(false);
            m100.setDisable(false);
            m1000.setDisable(true);
            m1000.setSelected(false);
            m1500.setSelected(false);
            m1500.setDisable(true);
        } else if (age < 12) {
            m50.setDisable(true);
            m50.setSelected(false);
            m100.setDisable(false);
            m1000.setDisable(false);
            m1500.setDisable(true);
            m1500.setSelected(false);
        } else {
            m50.setDisable(true);
            m50.setSelected(false);
            m100.setDisable(true);
            m100.setSelected(false);
            m1000.setDisable(false);
            m1500.setDisable(false);
        }
    }

    public void refreshEventsb() {
        int age = Integer.parseInt(ageCombo.getValue());
        refresh(age, m50b, m100b, m1000b, m1500b);
    }

    private void clearFields() {
        fnameField.clear();
        lnameField.clear();
        setSpinnerAge();
        refreshEvents();
        m50.setSelected(false);
        m100.setSelected(false);
        m1000.setSelected(false);
        m1500.setSelected(false);
    }

    private void initAll() throws ChatException {
        initModel();
        initModelb();
    }

    @FXML
    public void save() throws ChatException {
        infoLabel.setTextFill(Paint.valueOf("#ff0000"));
        boolean error = false;
        TableEntity t = null;
        Proba p1 = null, p2 = null;

        Copil copil = new Copil(1, fnameField.getText(), lnameField.getText(), ageSpinner.getValue());
        if (!(m50.isSelected() || m100.isSelected() || m1000.isSelected() || m1500.isSelected())) {
            infoLabel.setTextFill(Paint.valueOf("#ff0000"));
            infoLabel.setText("No event selected!");
        } else {

            if (m50.isSelected() && !m100.isSelected()) {
                p1 = new Proba(1, copil.getId(), 50);
            } else if (!m50.isSelected() && m100.isSelected()) {
                p1 = new Proba(1, copil.getId(), 100);
            } else if (m50.isSelected() && m100.isSelected()) {
                p1 = new Proba(1, copil.getId(), 50);
                p2 = new Proba(2, copil.getId(), 100);
            } else if (m100.isSelected() && !m1000.isSelected()) {
                p1 = new Proba(1, copil.getId(), 100);
            } else if (!m100.isSelected() && m1000.isSelected()) {
                p1 = new Proba(1, copil.getId(), 100);
            } else if (m100.isSelected() && m1000.isSelected()) {
                p1 = new Proba(1, copil.getId(), 100);
                p2 = new Proba(2, copil.getId(), 1000);
            } else if (m1000.isSelected() && !m1500.isSelected()) {
                p1 = new Proba(1, copil.getId(), 1000);
            } else if (!m1000.isSelected() && m1500.isSelected()) {
                p1 = new Proba(1, copil.getId(), 1000);
            } else if (m1000.isSelected() && m1500.isSelected()) {
                p1 = new Proba(1, copil.getId(), 1000);
                p2 = new Proba(2, copil.getId(), 1500);
            }


            try {
                TableEntity te = new TableEntity(1, copil, p1, p2);
                server.add(te);
                infoLabel.setTextFill(Paint.valueOf("#00ff00"));
                infoLabel.setText("Added succesfully.");
                clearFields();
            } catch (ValidationException e) {
                logger.error(e);
                infoLabel.setText(String.valueOf(e.getMessage()));
            }
        }

    }

    public void logout() {
        try {
            server.logout(user, this);
        } catch (ChatException | ValidationException e) {
            System.out.println("Logout error " + e);
        }
        System.exit(0);
    }

    @Override
    public void enitityAdded(TableEntity entity) throws ValidationException, ChatException {
        tabel.getItems().add(entity);
        tabel2.getItems().add(entity);
    }
}
