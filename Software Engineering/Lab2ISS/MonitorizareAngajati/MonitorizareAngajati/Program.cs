using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using MonitorizareAngajati.Models;
using MonitorizareAngajati.DbUtils;
using MonitorizareAngajati.Repository;
using MonitorizareAngajati.Services;

namespace MonitorizareAngajati
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {

            AngajatiContext context = new AngajatiContext();
            UserRepository URepo = new UserRepository(context);
            UserService UServ = new UserService(URepo);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginScreen(UServ));
        }
    }
}
