using System.Globalization;
using bonus_certs.dal;
using bonus_certs.model;
using bonus_certs.contracts;
using bonus_certs.services;
using Microsoft.AspNetCore.Mvc;

namespace bonus_certs.Controllers;

[ApiController]
[Route("[controller]")]
public class CertificateController : ControllerBase
{
    private readonly ILogger<CertificateController> _logger;
    private readonly CertificateRepository _certificateRepository;
    private readonly CertificateGenerator _certificateGenerator;

    public CertificateController(ILogger<CertificateController> logger, CertificateRepository certificateRepository, CertificateGenerator certificateGenerator)
    {
        _logger = logger;
        _certificateRepository = certificateRepository;
        _certificateGenerator = certificateGenerator;
    }

    [HttpGet]
    public IEnumerable<Certificate> Get()
    {
        return _certificateRepository.GetAll();
    }

    [HttpPost]
    public IActionResult IssueCertificate(IssueCertificateContracts contract){
        try {
            var certificate = _certificateGenerator.IssueCertificate(contract.IssuerSN, contract.SubjectUsername, contract.KeyUsageFlags, contract.Date);
            return Ok(certificate);
        }
        catch (Exception e){
            return BadRequest(e.Message);
        }
    }
}
