using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.model
{
    public class TableEntity
    {
        private int id;
        private Child copil;
        private Event p1, p2;
        String distances;

        public TableEntity(int id, Child c, Event p1, Event p2)
        {
            this.id = id;
            this.copil = c;
            this.p1 = p1;
            this.p2 = p2;
            this.distances = p1.Distanta + "m, " + p2.Distanta + "m";
        }


        public TableEntity(int id, Child c, Event p1)
        {
            this.id = id;
            this.copil = c;
            this.p1 = p1;
            this.distances = p1.Distanta + "m";
        }

        public int Id
        {
            get
            {
                return this.id;
            }
            set
            {
                this.id = value;
            }
        }


        public Event P1
        {
            get
            {
                return this.p1;
            }
            set
            {
                this.p1 = value;
            }
        }

        public Child Copil 
        {
            get
            {
                return this.copil;
            }
            set
            {
                this.copil = value;
            }
        }
        public Event P2
        {
            get
            {
                return this.p2;
            }
            set
            {
                this.p2 = value;
            }
        }

        public String Distances 
        {
            get
            {
                return this.distances;
            }
            set
            {
                this.distances = value;
            }
        }

    }
}
