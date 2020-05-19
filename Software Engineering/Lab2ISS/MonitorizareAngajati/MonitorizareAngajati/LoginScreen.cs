using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using MonitorizareAngajati.Services;
using MonitorizareAngajati.Models;

namespace MonitorizareAngajati
{
    public partial class LoginScreen : Form
    {
        private UserService Uservice;
        public LoginScreen(UserService Userv)
        {
            InitializeComponent();
            this.Uservice = Userv;
        }


        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        Point lastPoint;
        private void LoginScreen_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                this.Left += e.X - lastPoint.X;
                this.Top += e.Y - lastPoint.Y;
            }
        }

        private void LoginScreen_MouseDown(object sender, MouseEventArgs e)
        {
            lastPoint = new Point(e.X, e.Y);
        }

        private void loginBtn_Click(object sender, EventArgs e)
        {
            User user = Uservice.GetUser(txtUsername.Text, txtPassword.Text); 
            if(user == null)
            {
                infoLabel.Text = "Wrong username or password";
            }
            else
            {
                if (user.Type.Equals(UserType.sef.ToString()))
                {
                    infoLabel.Text = "";
                    this.Hide();
                    SefWindow SefScreen = new SefWindow();
                    SefScreen.Show();
                }
                if (user.Type.Equals(UserType.angajat.ToString()))
                {
                    infoLabel.Text = "";
                    this.Hide();
                    AngajatWindow AngajatScreen = new AngajatWindow();
                    AngajatScreen.Show();
                }
            }
        }
    }
}
