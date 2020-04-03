using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;

namespace Atletism_GUI.NET.repository
{
    public interface IChildDbRepo : IRepository<Child>
    {
        List<Child> findByAge(int minAge, int maxAge);
    }
}
