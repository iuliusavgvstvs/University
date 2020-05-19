using MonitorizareAngajati.DbUtils;
using MonitorizareAngajati.Models;
using System.Linq;

namespace MonitorizareAngajati.Repository
{
    public class UserRepository
    {
        private AngajatiContext context;

        public UserRepository(AngajatiContext c)
        {
            context = c;
        }

        public User GetUser(string username, string password)
        {
            foreach (User us in context.User.ToList())
                if (us.Username.Equals(username) && us.Password.Equals(password))
                    return us;
            return null;
        }
    }
}
