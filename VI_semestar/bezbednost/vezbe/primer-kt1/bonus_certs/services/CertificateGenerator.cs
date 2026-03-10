
using System.Security.Cryptography;
using System.Security.Cryptography.X509Certificates;
using bonus_certs.dal;
using bonus_certs.model;

namespace bonus_certs.services;

public class CertificateGenerator {
    private UserRepository _userRepository;
    private CertificateRepository _certificateRepository;

    private static string certDir = "certs";

    private Certificate issuer;
    private User subject;
    private X509KeyUsageFlags flags;
    private bool isAuthority;
    private X509Certificate2 issuerCertificate;
    private DateTime validTo;
    private RSA currentRSA;

    public CertificateGenerator(UserRepository userRepository, CertificateRepository certificateRepository)
    {
        _userRepository = userRepository;
        _certificateRepository = certificateRepository;
    }

    public Certificate IssueCertificate(string? issuerSN, string subjectUsername, string keyUsageFlags, DateTime validTo){
        Validate(issuerSN, subjectUsername, keyUsageFlags, validTo);
        var cert = GenerateCertificate();

        return ExportGeneratedCertificate(cert);
    }

    private Certificate ExportGeneratedCertificate(X509Certificate2 cert){
        var certificateForDb = new Certificate() {
            Issuer = issuer?.SerialNumber,
            Status = CertificateStatus.Valid,
            CertificateType = isAuthority 
                ? issuerCertificate == null
                    ? CertificateType.Root
                    : CertificateType.Intermediate
                : CertificateType.End,
            SerialNumber = cert.SerialNumber,
            SignatureAlgorithm = cert.SignatureAlgorithm.FriendlyName ?? "Unknown",
            Username = subject.Username,
            ValidFrom = cert.NotBefore,
            ValidTo = cert.NotAfter
        };

        _certificateRepository.Add(certificateForDb);

        File.WriteAllBytes($"{certDir}/{certificateForDb.SerialNumber}.crt", cert.Export(X509ContentType.Cert));
        File.WriteAllBytes($"{certDir}/{certificateForDb.SerialNumber}.key", currentRSA.ExportRSAPrivateKey());

        return certificateForDb;
    }
    // 009CE3087D7227AE30.key%00
    private void Validate(string? issuerSN, string subjectUsername, string keyUsageFlags, DateTime validTo) {
        if (!string.IsNullOrEmpty(issuerSN)){
            issuer = _certificateRepository.Get(issuerSN);
            //009CE3087D7227AE30
            issuerCertificate = new X509Certificate2($"{certDir}/{issuerSN}.crt");
            using (RSA rsa = RSA.Create()){
                rsa.ImportRSAPrivateKey(File.ReadAllBytes($"{certDir}/{issuerSN}.key"), out _);
                issuerCertificate = issuerCertificate.CopyWithPrivateKey(rsa);
            }
        }

        if (!(validTo > DateTime.Now && (string.IsNullOrEmpty(issuerSN) || validTo < issuerCertificate.NotAfter))){
            System.Console.WriteLine($"Comparing {validTo} and {DateTime.Now}");
            throw new Exception("The date is not in the accepted range");
        }
        this.validTo = validTo;

        subject = _userRepository.Get(subjectUsername);
        flags = ParseFlags(keyUsageFlags);
    }

    private X509Certificate2 GenerateCertificate() {
        var subjectText = $"CN={subject.Username}";
        currentRSA = RSA.Create(4096);

        var certificateRequest = new CertificateRequest(subjectText, currentRSA, HashAlgorithmName.SHA256, RSASignaturePadding.Pkcs1);

        certificateRequest.CertificateExtensions.Add(new X509BasicConstraintsExtension(isAuthority, false, 0, true));
        certificateRequest.CertificateExtensions.Add(new X509KeyUsageExtension(flags, false));
        
        var generatedCertificate = issuerCertificate == null
                ? certificateRequest.CreateSelfSigned(DateTime.Now, validTo)
                : certificateRequest.Create(issuerCertificate, DateTime.Now, validTo,
                    Guid.NewGuid().ToByteArray());
        return generatedCertificate;
    }
    //3,5,4 [3, 5, 4]
    private X509KeyUsageFlags ParseFlags(string keyUsageFlags) {
        if (string.IsNullOrEmpty(keyUsageFlags)){
            throw new Exception("KeyUsageFlags are mandatory");
        }

        var flagArray = keyUsageFlags.Split(",");
        var retVal = X509KeyUsageFlags.None;

        var possibleElements = Enum.GetValues<X509KeyUsageFlags>();
        
        foreach (var flag in flagArray){
            if (int.TryParse(flag, out int index)){
                var currentFlag = possibleElements[index];

                retVal |= currentFlag;

                if (currentFlag == X509KeyUsageFlags.KeyCertSign){
                    isAuthority = true;
                }
            }
            else {
                throw new Exception($"Unknown flag: {flag}");
            }
        }

        return retVal;
    }
}