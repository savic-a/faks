using System.Security.Cryptography.X509Certificates;
using System.Security.Cryptography;
using bonus_certs.dal;
using bonus_certs.services;

var builder = WebApplication.CreateBuilder(args);

// 00B14AEB26AD24AC44856012EAE8853A02
// 244553682431F22E
builder.WebHost.UseKestrel(options => {
    options.ListenLocalhost(7050, listenOptions => {
        var cert = "6223180DC8A2F544B41D839BD538615A";
        var certificate = new X509Certificate2($"certs/{cert}.crt");
        using (RSA rsa = RSA.Create()){
            rsa.ImportRSAPrivateKey(File.ReadAllBytes($"certs/{cert}.key"), out _);
            certificate = certificate.CopyWithPrivateKey(rsa);
        }
        listenOptions.UseHttps(certificate); 
    });
});

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddScoped<UserRepository>(_ => new UserRepository("users.txt"));
builder.Services.AddScoped<CertificateRepository>(_ => new CertificateRepository("certificates.txt"));
builder.Services.AddScoped<CertificateGenerator>();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();

app.Run();
