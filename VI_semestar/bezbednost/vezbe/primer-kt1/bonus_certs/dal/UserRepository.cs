
using bonus_certs.model;
using Newtonsoft.Json;

namespace bonus_certs.dal {
    public class UserRepository : IRepo<User, string>
    {
        private List<User> users; 
        private string path;

        public UserRepository(string path){
            this.path = path;
            if (!File.Exists(path)){
                users = new List<User>();
                File.Create(path);
            }
            else {
                users = JsonConvert.DeserializeObject<List<User>>(File.ReadAllText(path)) ?? new List<User>();
            }  
        }

        public int Add(User entity)
        {
            if (users.FirstOrDefault(x => x.Username == entity.Username) != null){
                throw new Exception("User with that username already exists");
            }

            users.Add(entity);

            return users.Count;
        }

        public int Delete(User entity)
        {
            if (users.FirstOrDefault(x => x.Username == entity.Username) == null){
                throw new Exception("User with that username not found");
            }

            users.RemoveAll(x => x.Username == entity.Username);

            return users.Count;
        }

        public void Dispose()
        {
            Save();
        }

        public User Get(string id)
        {
            var user = users.FirstOrDefault(x => x.Username == id);

            return user == null 
                ? throw new Exception("User with that username not found")
                : user;
        }

        public IEnumerable<User> GetAll()
        {
            return users;
        }

        public int Update(User entity)
        {
            if (users.FirstOrDefault(x => x.Username == entity.Username) == null){
                throw new Exception("User with that username not found");
            }

            var index = users.FindIndex(0, x => x.Username == entity.Username);
            users.Insert(index, entity);

            return users.Count;
        }

        private void Save() {
            var content = JsonConvert.SerializeObject(users, Formatting.Indented);
            File.WriteAllText(path, content);
        }
    }
}