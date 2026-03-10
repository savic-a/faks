using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zadatak2
{
    class Bazen
    {

        public double Level { get; set; }
        public double MinLevel { get; set; }
        public double MaxLevel { get; set; }
        public Status status { get; set; }

        Bazen() {
            this.Level = 2.5;
            this.status = Status.polupun;
            this.MinLevel = 0;
            this.MaxLevel = 5;
        }
        
    }
}
