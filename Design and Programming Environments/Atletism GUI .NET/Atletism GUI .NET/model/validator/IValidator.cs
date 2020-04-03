using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.model.validator
{
    interface IValidator<T> where T:IEntity
    {
        void validate(T entity);

    }
}
