using System;

namespace Atletism_GUI.NET.model
{
    public class User:IEntity
    {
        private string username;
        private string password;

        public User(String user, String pass)
        {
            this.username = user;
            this.password = pass;
        }

        public int Id { get; set; }

        public string Username
        {
            get { return username; }
            set { username = value; }
        }

        public string Password
        {
            get { return password; }
            set { password = value; }
        }
    }
}
