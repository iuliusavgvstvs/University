using Atletism_GUI.NET.model;
using Atletism_GUI.NET.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.service
{
    public class UserService : IService<User>
    {
        private UserDbRepo repo;
        public UserService(UserDbRepo rep)
        {
            this.repo = rep;
        }
        public User findOne(int id)
        {
            throw new NotImplementedException();
        }

        public User save(User entity)
        {
            throw new NotImplementedException();
        }

        public bool getLogin(string username, string password)
        {
            if (repo.findBy(username, password) != null)
                return true;
            return false;
        }
    }
}
