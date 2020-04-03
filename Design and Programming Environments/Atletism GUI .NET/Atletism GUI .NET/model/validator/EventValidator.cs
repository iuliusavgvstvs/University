using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model.exceptions;

namespace Atletism_GUI.NET.model.validator
{
    public class EventValidator : IValidator<Event> { 

            
        public void validate(Event entity)
        {
            int distance = entity.Distanta;
            if (distance != 50 && distance != 100 && distance != 1000 && distance != 1500)
            throw new ValidationException("Invalid distance");
                
        }
    }

   
}
