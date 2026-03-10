


using bonus_certs.model;
using Newtonsoft.Json;

namespace bonus_certs.dal {
    public class CertificateRepository : IRepo<Certificate, int>
    {
        private List<Certificate> certificates; 
        private string path;

        public CertificateRepository(string path){
            this.path = path;
            if (!File.Exists(path)){
                certificates = new List<Certificate>();
                File.Create(path);
            }
            else {
                certificates = JsonConvert.DeserializeObject<List<Certificate>>(File.ReadAllText(path)) ?? new List<Certificate>();
            }  
        }

        public int Add(Certificate entity)
        {
            if (certificates.FirstOrDefault(x => x.Id == entity.Id) != null){
                throw new Exception("Certificate with that Id already exists");
            }
            
            entity.Id = certificates.Count + 1;
            certificates.Add(entity);

            return certificates.Count;
        }

        public int Delete(Certificate entity)
        {
            if (certificates.FirstOrDefault(x => x.Id == entity.Id) == null){
                throw new Exception("Certificate with that Id not found");
            }

            certificates.RemoveAll(x => x.Id == entity.Id);

            return certificates.Count;
        }

        public void Dispose()
        {
            Save();
        }

        public Certificate Get(int id)
        {
            var certificate = certificates.FirstOrDefault(x => x.Id == id);

            return certificate == null 
                ? throw new Exception("Certificate with that Id not found")
                : certificate;
        }

        public Certificate Get(string serialNumber) {
            var certificate = certificates.FirstOrDefault(x => x.SerialNumber == serialNumber);

            return certificate == null 
                ? throw new Exception("Certificate with that Id not found")
                : certificate;
        }

        public IEnumerable<Certificate> GetAll()
        {
            return certificates;
        }

        public int Update(Certificate entity)
        {
            if (certificates.FirstOrDefault(x => x.Id == entity.Id) == null){
                throw new Exception("Certificate with that Id not found");
            }

            var index = certificates.FindIndex(0, x => x.Id == entity.Id);
            certificates.Insert(index, entity);

            return certificates.Count;
        }

        private void Save() {
            var content = JsonConvert.SerializeObject(certificates, Formatting.Indented);
            File.WriteAllText(path, content);
        }
    }
}