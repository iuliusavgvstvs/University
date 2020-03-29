using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.model
{
    class Copil:IEntity
    {
        private string firstName;
        private string lastName;
        private int age;

        public Copil(string fName, string lName, int nAge)
        {
            this.firstName = fName;
            this.lastName = lName;
            this.age = nAge;
        }

        public int Id { get; set; }

        public string FirstName
        {
            get { return firstName; }
            set { firstName = value; }
        }

        public string LastName
        {
            get { return lastName; }
            set { lastName = value; }
        }

        public int Age
        {
            get { return age; }
            set { age = value; }
        }

        public override string ToString()
        {
            return string.Format("Name: {0} {1}, age: {2}", firstName, lastName, age);
        }
    }
}
