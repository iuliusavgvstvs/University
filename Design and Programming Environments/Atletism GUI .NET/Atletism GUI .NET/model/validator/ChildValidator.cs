using System.Text.RegularExpressions;
using Atletism_GUI.NET.model.exceptions;

namespace Atletism_GUI.NET.model.validator
{
    public class ChildValidator : IValidator<Child>
    {
        bool isLetterAndSpace(string s)
        {
            if (s == null || s.Length == 0)
            {
                return false;
            }
            return Regex.IsMatch(s, @"^[a-zA-Z ]+$");
        }
        public void validate(Child c)
        {
            if (!isLetterAndSpace(c.FirstName))
            {
                throw new ValidationException("Invalid first name.");
            }
            if (!isLetterAndSpace(c.LastName))
            {
                throw new ValidationException("Invalid last name.");
            }
            if (c.Age < 6 || c.Age > 15)
            {
                throw new ValidationException("Invalid age.");
            }
        }
    }
}
