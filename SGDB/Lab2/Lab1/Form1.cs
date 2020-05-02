using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Configuration;


namespace Lab1
{
    public partial class Form1 : Form
    {
        //string sqlConnString = "Data Source = HP-ENVY; Initial Catalog=Evidenta_Medicamente;Integrated Security=True";
        string sqlConnString = ConfigurationManager.ConnectionStrings["sqldb"].ConnectionString;
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        private string parentTable = ConfigurationManager.AppSettings["parentTable"];
        private string childTable = ConfigurationManager.AppSettings["childTable"];
        private string category = ConfigurationManager.AppSettings["category1"];
        private string categoryb = ConfigurationManager.AppSettings["category4"];
        // Doar un dataSet
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
            {
                conn.Open();

                SqlCommand cmd = new SqlCommand("SELECT * FROM "+ parentTable, conn);
                da.SelectCommand = cmd;
                da.Fill(ds, parentTable);
                dataGridView1.DataSource = ds.Tables[parentTable];
            }
        }



        private void refreshgrid2()
        {
            if (dataGridView1.SelectedCells.Count > 0)
            {
                int selectedrowindex = dataGridView1.SelectedCells[0].RowIndex;
                DataGridViewRow selectedRow = dataGridView1.Rows[selectedrowindex];
                string a = Convert.ToString(selectedRow.Cells[category].Value);


                using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
                {
                    if (dataGridView2.Rows.Count > 0)
                        ds.Tables[childTable].Clear();
                    SqlCommand cmd = new SqlCommand("SELECT * FROM " + childTable + " where " + categoryb + " = " + a, conn);
                    da.SelectCommand = cmd;
                    da.Fill(ds, childTable);
                    dataGridView2.DataSource = ds.Tables[childTable];

                }
            }
        }

        private void dataGridView1_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            refreshgrid2();
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void delete(object sender, EventArgs e)
        {
            if (dataGridView2.SelectedCells.Count > 0)
            {
                int selectedrowindex = dataGridView2.SelectedCells[0].RowIndex;
                DataGridViewRow selectedRow = dataGridView2.Rows[selectedrowindex];
                string a = Convert.ToString(selectedRow.Cells[category].Value);

                using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
                {
                    
                    SqlParameter param = new SqlParameter();
                    SqlCommand cmd = new SqlCommand($"DELETE FROM {childTable} WHERE id = {a} ", conn);
                    da.SelectCommand = cmd;
                    da.Fill(ds, childTable);
                    dataGridView2.DataSource = ds.Tables[childTable];
                    refreshgrid2();
                    

                }
            }
        }

        private void save(object sender, EventArgs e)
        {
            try
            {
                using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
                {
                    da.SelectCommand.Connection = conn;
                    //da.Fill(ds, childTable);
                    SqlCommandBuilder builder = new SqlCommandBuilder(da);
                    da.Update(ds, childTable);

                }

            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Form2 f2 = new Form2();
            f2.Show();
        }
    }
}
