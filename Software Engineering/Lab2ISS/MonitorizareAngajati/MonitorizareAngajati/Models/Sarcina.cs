using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonitorizareAngajati.Models
{
    [Table("Sarcina")]
    public class Sarcina
    {
        [Key(), DatabaseGenerated(DatabaseGeneratedOption.Identity),]
        public int ID { get; set; }

        public int SefID { get; set; }
        public Sef Sef { get; set; }

        public int AngajatID { get; set; }

        public Angajat Angajat { get; set; }
        public DateTime Date { get; set; }
        public string Descriere { get; set; }

        [Key(), ForeignKey("AngajatID")]
        public Angajat angajat { get; set; }

        [Key(), ForeignKey("SefID")]
        public Sef sef { get; set; }
    }
}
