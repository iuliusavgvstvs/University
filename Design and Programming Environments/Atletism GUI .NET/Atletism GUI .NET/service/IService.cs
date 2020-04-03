using Atletism_GUI.NET.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.service
{
    public interface IService<T> where T:IEntity
    {
        T findOne(int id);
        T save(T entity);
    }
}
