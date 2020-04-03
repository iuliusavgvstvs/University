using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Atletism_GUI.NET.model;
using Atletism_GUI.NET.model.validator;
using Atletism_GUI.NET.repository;
using Atletism_GUI.NET.service;
using log4net;

namespace Atletism_GUI.NET
{
    static class Program
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(Program));
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        /// 
        [STAThread]
        static void Main()
        {
            log4net.Config.XmlConfigurator.Configure();
            logger.Info("Program Started");

            UserDbRepo urepo = new UserDbRepo();
            ChildDbRepo crepo = new ChildDbRepo();
            EventDbRepository erepo = new EventDbRepository();
            ChildValidator cvalidator = new ChildValidator();
            EventValidator evalidator = new EventValidator();
            UserService userv = new UserService(urepo);
            ChildService cserv = new ChildService(crepo, cvalidator);
            EventService eserv = new EventService(erepo, evalidator);
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Login(userv, cserv, eserv));
            //ChildDbRepo repo = new ChildDbRepo();
            //Child  c = repo.save(new Child("Adrian", "Ionel", 9));
            logger.Info("Program ended.");
        }
    }
}
