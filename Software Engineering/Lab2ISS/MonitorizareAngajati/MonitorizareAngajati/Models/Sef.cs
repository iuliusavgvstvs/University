using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonitorizareAngajati.Models
{
    [Table("Sef")]
    public class Sef
    {

        [Key(), DatabaseGenerated(DatabaseGeneratedOption.Identity),]
        public int ID { get; set; }
        public int UserID { get; set; }
        public string Name { get; set; }

        public Sef(int Id, String Nam)
        {
            UserID = Id;
            this.Name = Nam;
        }

        [Key(), ForeignKey("UserID")]
        public User user { get; set; }
    }
}
