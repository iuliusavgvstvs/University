using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Atletism_GUI.NET.model;
using Atletism_GUI.NET.service;


namespace Atletism_GUI.NET
{
    public partial class Login : Form
    {

        private UserService uService;
        private ChildService cService;
        private EventService eService;

        public Login(UserService uServ, ChildService cServ, EventService eServ)
        {
            this.uService = uServ;
            this.cService = cServ;
            this.eService = eServ;
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }



        private void button2_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void button1_Click(object sender, EventArgs e)
        {

            if (!uService.getLogin(textBox1.Text, textBox2.Text))
            {
                label5.Text = "Login failed, please try again.";
            }
            else
            {
                label5.Text = "";
                this.Hide();
                Main main = new Main(cService, eService, this);
                main.Show();
            }
        }
    }
}
