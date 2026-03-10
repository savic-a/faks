
namespace bonus_certs.contracts {
    public class IssueCertificateContracts {
        public string SubjectUsername {get; set;}
        public string KeyUsageFlags {get; set;} 
        public string? IssuerSN {get; set;} 
        public DateTime Date {get; set;}
    }
}

