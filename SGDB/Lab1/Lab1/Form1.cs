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

namespace Lab1
{
    public partial class Form1 : Form
    {
        string sqlConnString = "Data Source = HP-ENVY; Initial Catalog=Evidenta_Medicamente;Integrated Security=True";
        SqlDataAdapter da = new SqlDataAdapter();
        SqlDataAdapter da2 = new SqlDataAdapter();
        DataSet ds = new DataSet();
        DataSet ds2 = new DataSet();
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
                da.SelectCommand = new SqlCommand("SELECT * FROM Producator", conn);
                da.Fill(ds, "Producator");
                dataGridView1.DataSource = ds.Tables["Producator"];
                da2.SelectCommand = new SqlCommand("SELECT * FROM ContactProducator where ContactProducator.idproducator = 1", conn);
                da2.Fill(ds2, "ContactProducator");
                dataGridView2.DataSource = ds2.Tables["ContactProducator"];
                dataGridView2.Columns[0].ReadOnly = true;
                dataGridView2.Columns[3].ReadOnly = true;
            }
        }



        private void refreshgrid2()
        {
            if (dataGridView1.SelectedCells.Count > 0)
            {
                int selectedrowindex = dataGridView1.SelectedCells[0].RowIndex;
                DataGridViewRow selectedRow = dataGridView1.Rows[selectedrowindex];
                string a = Convert.ToString(selectedRow.Cells["id"].Value);

                using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
                {
                    ds2.Clear();
                    SqlParameter param = new SqlParameter();
                    param.ParameterName = "@id";
                    param.Value = a;
                    SqlCommand cmd = new SqlCommand("SELECT * FROM ContactProducator where ContactProducator.idproducator = @id;", conn);
                    cmd.Parameters.Add(param);
                    da2.SelectCommand = cmd;
                    da2.Fill(ds2, "ContactProducator");
                    dataGridView2.DataSource = ds2.Tables["ContactProducator"];

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
                string a = Convert.ToString(selectedRow.Cells["id"].Value);

                using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
                {
                    ds2.Clear();
                    SqlParameter param = new SqlParameter();
                    param.ParameterName = "@id";
                    param.Value = a;
                    SqlCommand cmd = new SqlCommand("DELETE FROM CONTACTPRODUCATOR WHERE id = @id;", conn);
                    cmd.Parameters.Add(param);
                    da2.SelectCommand = cmd;
                    da2.Fill(ds2, "ContactProducator");
                    dataGridView2.DataSource = ds2.Tables["ContactProducator"];
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
                    conn.Open();
                    int selectedrowindex = dataGridView1.SelectedCells[0].RowIndex;
                    DataGridViewRow selectedRow = dataGridView1.Rows[selectedrowindex];
                    string a = Convert.ToString(selectedRow.Cells["id"].Value);
                    using (SqlCommand cmd = new SqlCommand("DELETE FROM CONTACTPRODUCATOR WHERE idproducator = @id;", conn))
                    {
                        cmd.Parameters.AddWithValue("@id", a);
                        cmd.ExecuteNonQuery();
                    }
                        foreach (DataGridViewRow row in dataGridView2.Rows)
                    {

                        if (!row.IsNewRow)
                        {
                            using (SqlCommand cmd = new SqlCommand("INSERT INTO ContactProducator (adresa, nr_telefon, idproducator) VALUES(@c1,@c2,@c3)", conn))
                            {

                                cmd.Parameters.AddWithValue("@C1", row.Cells[1].Value);
                                cmd.Parameters.AddWithValue("@C2", row.Cells[2].Value);
                               
                                cmd.Parameters.AddWithValue("@C3", a);
                                 cmd.ExecuteNonQuery();

                            }
                        }
                    }
                    refreshgrid2();
                    MessageBox.Show("Table updated", "Update", MessageBoxButtons.OK, MessageBoxIcon.Information);
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message, "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
