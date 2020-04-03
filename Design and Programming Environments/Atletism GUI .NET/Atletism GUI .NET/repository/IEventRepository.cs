using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;

namespace Atletism_GUI.NET.repository
{
    public interface IEventRepository : IRepository<Event>
    {
        List<Event> findByChildId(int id);
        List<Event> findByDistance(int distance);
    }
}
