using System;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Windows.Forms;

namespace Lab1
{
    public partial class Form2 : Form
    {

        string sqlConnString = ConfigurationManager.ConnectionStrings["sqldb"].ConnectionString;
        SqlDataAdapter da = new SqlDataAdapter();
        SqlDataAdapter da2 = new SqlDataAdapter();
        DataSet ds = new DataSet();
        BindingSource bs = new BindingSource();
        private string parentTable = ConfigurationManager.AppSettings["parentTable"];
        private string childTable = ConfigurationManager.AppSettings["childTable"];

        public Form2()
        {
            InitializeComponent();
        }


        private void Form2_Load(object sender, EventArgs e)
        {
            this.Text = ConfigurationManager.AppSettings["formTitle"];
            using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
            {
                conn.Open();
                da.SelectCommand = new SqlCommand(ConfigurationManager.AppSettings["populateTable1"], conn);
                da.Fill(ds, parentTable);
                foreach (DataRow r in ds.Tables[parentTable].Rows)
                {
                    listBox1.Items.Add(r[ConfigurationManager.AppSettings["listBoxKey"]].ToString());
                }

                bs.DataSource = ds.Tables[parentTable];
                label1.Text = ConfigurationManager.AppSettings["category1"].ToUpper();
                label2.Text = ConfigurationManager.AppSettings["category2"].ToUpper();
                label5.Text = ConfigurationManager.AppSettings["category3"];
                label3.Text = ConfigurationManager.AppSettings["table2Text"];
                textBox1.DataBindings.Add("Text", bs, ConfigurationManager.AppSettings["category1"]);
                textBox3.DataBindings.Add("Text", bs, ConfigurationManager.AppSettings["category2"]);
                listBox1.SelectedIndex = 0;
                
            }
        }

        private void showContact()
        {
            using (SqlConnection conn = new SqlConnection { ConnectionString = sqlConnString })
            {
                conn.Open();
                if (ds.Tables[childTable] != null)
                {
                    ds.Tables[childTable].Clear();
                }
                SqlCommand cmd = new SqlCommand(ConfigurationManager.AppSettings["populateTable2"], conn);
                cmd.Parameters.AddWithValue("@id", textBox1.Text);
                da2.SelectCommand = cmd;
                da2.Fill(ds, childTable);
                dataGridView1.DataSource = ds.Tables[childTable];
                textBox4.Text = dataGridView1.Rows.Count.ToString();
            }
        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (bs != null)
            {
                bs.Position = listBox1.SelectedIndex;
            }
            showContact();
        }
    }
}
