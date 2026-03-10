using bonus_certs.dal;
using bonus_certs.model;
using Microsoft.AspNetCore.Mvc;

namespace bonus_certs.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    private readonly ILogger<UserController> _logger;
    private readonly UserRepository _userRepository;

    public UserController(ILogger<UserController> logger, UserRepository userRepository)
    {
        _logger = logger;
        _userRepository = userRepository;
    }

    [HttpGet]
    public IEnumerable<User> Get()
    {
        return _userRepository.GetAll();
    }

    [HttpPost]
    public IActionResult AddUser(User user){
        try {
            var count = _userRepository.Add(user);
            return Ok(new { count, user });
        }
        catch (Exception e){
            return BadRequest(e.Message);
        }
    }
}
