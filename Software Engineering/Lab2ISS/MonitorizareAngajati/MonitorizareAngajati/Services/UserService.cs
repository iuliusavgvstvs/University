using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using MonitorizareAngajati.Repository;
using MonitorizareAngajati.Models;

namespace MonitorizareAngajati.Services
{
    public class UserService
    {
        private UserRepository Repo;

        public UserService(UserRepository Rep)
        {
            this.Repo = Rep;
        }

        public User GetUser(string username, string password)
        {
            return Repo.GetUser(username, password);
        }
    }
}
