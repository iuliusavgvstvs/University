using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.model
{
    class Proba:IEntity
    {
        private int id;
        private int copilID;
        private int distanta;

        public Proba(int Id, int CopilId, int Distanta)
        {
            this.id = Id;
            this.copilID = CopilId;
            this.distanta = Distanta;
        }

        public int Id
        {
            get { return id; }
            set { id = value; }
        }

        public int CopilID
        {
            get { return copilID; }
            set { copilID = value; }
        }

        public int Distanta
        {
            get { return distanta; }
            set { distanta = value; }
        }
    }
}
