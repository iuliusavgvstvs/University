using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Atletism_GUI.NET.model;
using Atletism_GUI.NET.config;

namespace Atletism_GUI.NET
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //Application.Run(new Form1());

            List<string> useri = new List<string>();
            using ( var db = new mainEntities())
            {
                useri = (from u in db.Users select u.Username).ToList();
                db.Dispose();
            }
            foreach(string str in useri)
            {
                Console.WriteLine(str);
            }

        }
        
    }
}
