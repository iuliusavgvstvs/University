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
    public class ChildService : IService<Child>
    {
        private ChildDbRepo repo;
        private ChildValidator validator;

        public ChildService(ChildDbRepo rep, ChildValidator val)
        {
            this.validator = val;
            this.repo = rep;
        }
        public Child findOne(int id)
        {
            return repo.findOne(id);
        }

        public Child save(Child entity)
        {
            validator.validate(entity);
            return repo.save(entity);
        }
        
        public List<Child> findByAge(int minAge, int maxAge)
        {
            return repo.findByAge(minAge, maxAge);
        }

    }
}
