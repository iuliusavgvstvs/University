using System.Data;
using System;
using System.Reflection;
using Mono.Data.Sqlite;

namespace Atletism_GUI.NET.repository
{

    public static class DBUtils
    {


        private static IDbConnection instance = null;

        public static IDbConnection getConnection()
        {
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = getNewConnection();
                instance.Open();
            }
            return instance;
        }

        private static IDbConnection getNewConnection()
        {

            return ConnectionFactory.getInstance().createConnection();


        }
    }
    public abstract class ConnectionFactory
    {
        protected ConnectionFactory()
        {
        }

        private static ConnectionFactory instance;

        public static ConnectionFactory getInstance()
        {
            if (instance == null)
            {

                Assembly assem = Assembly.GetExecutingAssembly();
                Type[] types = assem.GetTypes();
                foreach (var type in types)
                {
                    if (type.IsSubclassOf(typeof(ConnectionFactory)))
                        instance = (ConnectionFactory)Activator.CreateInstance(type);
                }
            }
            return instance;
        }

        public abstract IDbConnection createConnection();
    }

    public class SqliteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection()
        {
            Console.WriteLine("creating ... sqlite connection");
            //String connectionString = "URI=file:ChatMPP2017.db,Version=3";
            String connectionString = "Data Source=C:\\Users\\Iuliu\\OneDrive\\Desktop\\University\\Design and Programming Environments\\Atletism GUI .NET\\atletismdb.db;";
            return new SqliteConnection(connectionString);

            //connection string='Data Source=C:\Users\Iuliu\OneDrive\Desktop\University\Design and Programming Environments\Atletism GUI .NET\atletismdb.db"
        }
    }
}