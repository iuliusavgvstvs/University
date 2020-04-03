using Atletism_GUI.NET.service;
using Atletism_GUI.NET.model;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Atletism_GUI.NET.model.exceptions;
using log4net;

namespace Atletism_GUI.NET
{
    public partial class Main : Form
    {
        private ChildService cService;
        private EventService eService;
        private Login login;
        DataTable table;
        private int minAge, maxAge;
        private static readonly ILog logger = LogManager.GetLogger(typeof(Program));


        private void populateTable(DataGridView grid, List<TableEntity> data)
        {
            this.table = new DataTable();
            this.table.Columns.Add("Id", typeof(int));
            this.table.Columns.Add("First name", typeof(string));
            this.table.Columns.Add("Last name", typeof(string));
            this.table.Columns.Add("Age", typeof(int));
            this.table.Columns.Add("Event/s", typeof(string));
            foreach (TableEntity te in data){
                this.table.Rows.Add(te.Id, te.Copil.FirstName, te.Copil.LastName, te.Copil.Age, te.Distances);
            }
            grid.DataSource = this.table;
        }
        private void setAge()
        {
            if (comboBox1.SelectedIndex == 0) {
                minAge = 6;
                maxAge = 8;
            }
            else if (comboBox1.SelectedIndex == 1)
            {
                minAge = 9;
                maxAge = 11;
            }
            else
            {
                minAge = 12;
                maxAge = 15;
            }
        }
        private int getAge()
        {
            return 6 + comboBox2.SelectedIndex;
        }
        private List<TableEntity> getData() {

             List<TableEntity> all = new List<TableEntity>();
              setAge();
            foreach(Child c in cService.findByAge(minAge, maxAge)){
                    List<Event> probas = eService.findByChildId(c.Id);
                    if (probas != null)
                    {
                        if (probas.Count == 1)
                            all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (probas.Count == 2)
                        {
                            all.Add(new TableEntity(c.Id, c, probas.ElementAt(0), probas.ElementAt(1)));
                        }
                    }
                
            }
            return all;
        
    }
        public Main(ChildService cServ, EventService eServ, Login l)
        {
            this.cService = cServ;
            this.eService = eServ;
            this.login = l;
            InitializeComponent();
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {

        }

        private void label5_Click(object sender, EventArgs e)
        {

        }

        private void checkedListBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void Main_Load(object sender, EventArgs e)
        {
            comboBox1.SelectedIndex = 1;
            comboBox2.SelectedIndex = 6;
            checkBox1.Enabled = false;
            checkBox4.Enabled = false;
            populateTable(dataGridView1, getData());
            populateTable(dataGridView3, getDatab());
        }

        private void AddBtn_Click(object sender, EventArgs e)
        {
            save();
        }

        private void LogOutBtn_Click(object sender, EventArgs e)
        {
            this.Close();
            login.Show();
        }

        private void AgeUD_ValueChanged(object sender, EventArgs e)
        {
            refresh_checks(AgeUD.Value, checkBox1, checkBox2, checkBox3, checkBox4);
        }

        private void refresh_checks(decimal value, CheckBox c1, CheckBox c2, CheckBox c3, CheckBox c4)
        {
            if (value < 9)
            {
                c3.Checked = false;
                c3.Enabled = false;
                c4.Checked = false;
                c4.Enabled = false;

                c1.Enabled = true;
                c2.Enabled = true;
            }
            else if (value< 12)
            {
                c1.Checked = false;
                c1.Enabled = false;
                c4.Checked = false;
                c4.Enabled = false;

                c2.Enabled = true;
                c3.Enabled = true;
            }
            else
            {
                c1.Checked = false;
                c1.Enabled = false;
                c2.Checked = false;
                c2.Enabled = false;

                c3.Enabled = true;
                c4.Enabled = true;
            }

        }
        
        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            populateTable(dataGridView1, getData());
        }
        private void save()
        {
            infoLabel.ForeColor = System.Drawing.Color.Red;
            bool error = false;
            Child copil = new Child( fnameBox.Text, lnameBox.Text, Decimal.ToInt32(AgeUD.Value));
            if (!(checkBox1.Checked || checkBox2.Checked || checkBox3.Checked || checkBox4.Checked))
            {
                infoLabel.ForeColor = System.Drawing.Color.Red;
                infoLabel.Text = "No event selected.";
            }
            else
            {
                try
                {
                    copil = cService.save(copil);
                }
                catch (ValidationException e)
                {
                    logger.Error(e);
                    infoLabel.Text = e.Message;
                    error = true;
                }
                if (!error)
                {
                    if (checkBox1.Checked)
                        try
                        {
                            eService.save(new Event(copil.Id, 50));
                        }
                        catch (ValidationException e)
                        {
                            logger.Error(e);
                            infoLabel.Text = e.Message;
                        }
                    if (checkBox2.Checked)
                        try
                        {
                            eService.save(new Event(copil.Id, 100));
                        }
                        catch (ValidationException e)
                        {
                            logger.Error(e);
                            infoLabel.Text = e.Message;
                        }
                    if (checkBox3.Checked)
                        try
                        {
                            eService.save(new Event(copil.Id, 1000));
                        }
                        catch (ValidationException e)
                        {
                            logger.Error(e);
                            infoLabel.Text = e.Message;
                        }
                    if (checkBox4.Checked)
                        try
                        {
                            eService.save(new Event(copil.Id, 1500));
                        }
                        catch (ValidationException e)
                        {
                            logger.Error(e);
                            infoLabel.Text = e.Message;
                        }

                    infoLabel.ForeColor = System.Drawing.Color.Green;
                    infoLabel.Text = "Added succesfully.";
                    populateTable(dataGridView1, getData());
                    populateTable(dataGridView3, getDatab());
                    clearFields();
                }
            }

        }

        private void panel3_Paint(object sender, PaintEventArgs e)
        {

        }

        private void label8_Click(object sender, EventArgs e)
        {

        }

        private void comboBox2_SelectedIndexChanged(object sender, EventArgs e)
        {
            refresh_checks(getAge(), checkBox5, checkBox6, checkBox7, checkBox8);
            populateTable(dataGridView3, getDatab());
        }

        private void clearFields()
        {
            fnameBox.Text = "";
            lnameBox.Text = "";
            checkBox1.Enabled = false;
            checkBox4.Enabled = false;
            
            AgeUD.Value = 11;
            refresh_checks(11,checkBox1, checkBox2, checkBox3, checkBox4);
        }

        private void checkBox5_CheckedChanged(object sender, EventArgs e)
        {
            populateTable(dataGridView3, getDatab());
        }

        private void checkBox6_CheckedChanged(object sender, EventArgs e)
        {
            populateTable(dataGridView3, getDatab());
        }

        private void checkBox7_CheckedChanged(object sender, EventArgs e)
        {
            populateTable(dataGridView3, getDatab());
        }

        private void checkBox8_CheckedChanged(object sender, EventArgs e)
        {
            populateTable(dataGridView3, getDatab());
        }

        private List<TableEntity> getDatab()
        {
            List<TableEntity> all = new List<TableEntity>();
            int age = getAge();
            foreach (Child c in cService.findByAge(age, age))
            {
                List<Event> probas = eService.findByChildId(c.Id);
                if (probas != null)
                {
                    if (age < 9)
                    {
                        if (checkBox5.Checked && !checkBox6.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 50)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (!checkBox5.Checked && checkBox6.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 100)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (checkBox5.Checked && checkBox6.Checked)
                            if (probas.Count == 2)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0), probas.ElementAt(1)));
                    }
                    else if (age < 12)
                    {
                        if (checkBox6.Checked && !checkBox7.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 100)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (!checkBox6.Checked && checkBox7.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 1000)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (checkBox6.Checked && checkBox7.Checked)
                            if (probas.Count == 2)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0), probas.ElementAt(1)));
                    }
                    else
                    {
                        if (checkBox7.Checked && !checkBox8.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 1000)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (!checkBox7.Checked && checkBox8.Checked)
                            if (probas.Count == 1 && probas.ElementAt(0).Distanta == 1500)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0)));
                        if (checkBox7.Checked && checkBox8.Checked)
                            if (probas.Count == 2)
                                all.Add(new TableEntity(c.Id, c, probas.ElementAt(0), probas.ElementAt(1)));
                    }
                }
            }
            return all;
        }
    }
}
