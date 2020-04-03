using Atletism_GUI.NET.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;

namespace Atletism_GUI.NET.repository
{
    public class EventDbRepository : IEventRepository
    {
        public List<Event> findByChildId(int id)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM Proba WHERE CopilID is @id;";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                List<Event> list = new List<Event>();
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Event e = new Event(dataR.GetInt32(1), dataR.GetInt32(2));
                        e.Id = dataR.GetInt32(0);
                        list.Add(e);
                    }
                    if(list.Any())
                        return list;

                }
            }
            return null;
        }

        public List<Event> findByDistance(int distance)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM Proba WHERE Distanta is @dist;";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@dist";
                paramId.Value = distance;
                List<Event> list = new List<Event>();
                using (var dataR = comm.ExecuteReader())
                {
                    while (dataR.Read())
                    {
                        Event e = new Event(dataR.GetInt32(1), dataR.GetInt32(2));
                        e.Id = dataR.GetInt32(0);
                        list.Add(e);
                    }
                    if (list.Any())
                        return list;

                }
            }
            return null;
        }

        public Event findOne(int id)
        {
            throw new NotImplementedException();
        }

        public Event save(Event entity)
        {
            var con = DBUtils.getConnection();
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "INSERT INTO Proba(CopilID,Distanta) VALUES(@id,@dist);SELECT last_insert_rowid();";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = entity.CopilID;
                comm.Parameters.Add(paramId);
                var paramDist = comm.CreateParameter();
                paramDist.ParameterName = "@dist";
                paramDist.Value = entity.Distanta;
                comm.Parameters.Add(paramDist);
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
