using Atletism_GUI.NET.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.repository
{
    public class UserDbRepo : IUserDbRepo
    {
        public User findBy(string username, string password)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "select * from user where username=@user and password=@pass";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@user";
                paramId.Value = username;
                comm.Parameters.Add(paramId);
                var paramPass = comm.CreateParameter();
                paramPass.ParameterName = "@pass";
                paramPass.Value = password;
                comm.Parameters.Add(paramPass);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        User u = new User(dataR.GetString(1), dataR.GetString(2));
                        u.Id = dataR.GetInt32(0);
                        return u;
                    }
   
                }
            }
            return null;
        }

        public User findOne(int id)
        {
            throw new NotImplementedException();
        }

        public User save(User entity)
        {
            throw new NotImplementedException();
        }
    }
}
