using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;

namespace Atletism_GUI.NET.repository
{
    public interface IRepository<T> where T:IEntity
    {
        void save(T entity);

        T findOne(int id);
    }
}
