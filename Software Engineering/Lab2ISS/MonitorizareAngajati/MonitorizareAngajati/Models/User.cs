using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MonitorizareAngajati.Models
{

    public enum UserType { angajat, sef}

    [Table("User")]
    public class User
    {
        [Key(), DatabaseGenerated(DatabaseGeneratedOption.Identity),]
        public int ID { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Type { get; set; }

        public User( string User, string Pass)
        {
            this.Username = User;
            this.Password = Pass;
        }

        public User(int Id,string User, string Pass, UserType Typ)
        {
            this.ID = Id;
            this.Username = User;
            this.Password = Pass;
            this.Type = Typ.ToString();
        }
        public User() { }

    }
}
