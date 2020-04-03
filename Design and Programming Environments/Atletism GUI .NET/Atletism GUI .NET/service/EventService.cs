using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Atletism_GUI.NET.model;
using Atletism_GUI.NET.model.validator;
using Atletism_GUI.NET.repository;

namespace Atletism_GUI.NET.service
{
    public class EventService : IService<Event>
    {
        private EventDbRepository repo;
        private EventValidator validator;

        public EventService(EventDbRepository rep, EventValidator val)
        {
            this.repo = rep;
            this.validator = val;
        }
        public Event findOne(int id)
        {
            return repo.findOne(id);
        }

        public Event save(Event entity)
        {
            validator.validate(entity);
            return repo.save(entity);
        }

        public List<Event> findByChildId(int id)
        {
            return repo.findByChildId(id);
        }
        public List<Event> findByDistance(int dist)
        {
            return repo.findByDistance(dist);
        }
    }
}
