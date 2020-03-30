using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;
namespace Atletism_GUI.NET.repository

{
    public interface IUserDbRepo:IRepository<EUser>
    {
        EUser findBy(string username, string password);
    }
}
