using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonitorizareAngajati.Models
{
    public enum StareAngajat
    {
        online, offline
    }

    [Table("Angajat")]
    public class Angajat
    {
        [Key(), DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ID { get; set; }
        public int UserID { get; set; }
        public string Name { get; set; }

        public string Stare { get; set; }

        public Angajat(int UserId, string Nam, StareAngajat St)
        {
            UserID = UserId;
            this.Name = Nam;
            this.Stare = St.ToString();
        }

        public Angajat(int UserId, string Nam)
        {
            UserID = UserId;
            this.Name = Nam;
            this.Stare = StareAngajat.offline.ToString();
        }

        [Key(), ForeignKey("UserID")]
        public User user { get; set; }
    }
}
