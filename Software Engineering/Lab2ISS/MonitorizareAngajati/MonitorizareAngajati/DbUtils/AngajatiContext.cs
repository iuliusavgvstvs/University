using MonitorizareAngajati.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonitorizareAngajati.DbUtils
{
    public class AngajatiContext : DbContext
    {
        public AngajatiContext() : base("name=DefaultConnection")   {}
        public DbSet<User> User { get; set; }
        public DbSet<Sef> Sef { get; set; }
        public DbSet<Angajat> Angajat { get; set; }

    }
}
