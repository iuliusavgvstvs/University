using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Atletism_GUI.NET.model
{
    public class Event:IEntity
    {
        private int copilID;
        private int distanta;

        public Event(int CopilId, int Distanta)
        {
            this.copilID = CopilId;
            this.distanta = Distanta;
        }

        public int Id
        {
            get; set;
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
