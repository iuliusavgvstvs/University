using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;
using Atletism_GUI.NET.repository;

namespace Atletism_GUI.NET.repository
{
    public class ChildDbRepo : IChildDbRepo
    {
        public List<Child> findByAge(int minAge, int maxAge)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM Copil WHERE Varsta between @minage and @maxage;";
                var paramAge = comm.CreateParameter();
                paramAge.ParameterName = "@minage";
                paramAge.Value = minAge;
                comm.Parameters.Add(paramAge);
                var paramAge2 = comm.CreateParameter();
                paramAge2.ParameterName = "@maxage";
                paramAge2.Value = maxAge;
                comm.Parameters.Add(paramAge2);
                List<Child> list = new List<Child>();
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Child c = new Child(dataR.GetString(1), dataR.GetString(2), dataR.GetInt32(3));
                        c.Id = dataR.GetInt32(0);
                        list.Add(c);
                    }

                }
                if (list.Any())
                    return list;
            }
            return null;
        }

        public Child findOne(int id)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM Copil WHERE id is @id;";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    {
                        Child c = new Child(dataR.GetString(1), dataR.GetString(2), dataR.GetInt32(3));
                        c.Id = dataR.GetInt32(0);
                        return c;
                    }

                }
            }
            return null;
        }

        public Child save(Child entity)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "INSERT INTO Copil(nume,prenume,varsta) VALUES(@fname,@lname,@age);SELECT last_insert_rowid();";
                var paramFName = comm.CreateParameter();
                paramFName.ParameterName = "@fname";
                paramFName.Value = entity.FirstName;
                comm.Parameters.Add(paramFName);
                var paramLName = comm.CreateParameter();
                paramLName.ParameterName = "@lname";
                paramLName.Value = entity.LastName;
                comm.Parameters.Add(paramLName);
                var paramAge = comm.CreateParameter();
                paramAge.ParameterName = "@age";
                paramAge.Value = entity.Age;
                comm.Parameters.Add(paramAge);
                using (var dataR = comm.ExecuteReader())
                {
                    if (dataR.Read())
                    { 
                        entity.Id = dataR.GetInt32(0);
                        return entity;
                    }

                }
            }
            return null;
        }
    }
}
