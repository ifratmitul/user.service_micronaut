package controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import model.User;
import service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller("/api/users")
public class UserController {

    private final UserService _userService;

    public UserController(UserService userService) {
        _userService = userService;
    }

    @Post
    public HttpResponse<User> createUser(@Body @Valid User user) {
        return HttpResponse.created(_userService.createUser(user));
    }

    @Get
    public HttpResponse<List<User>> getUsers() {
        return HttpResponse.ok(_userService.getUserList());
    }

    @Get("/{id}")
    public HttpResponse<User> getUserById(@PathVariable int id) {
        return HttpResponse.ok(_userService.getUserbyId(id));
    }

    @Put("/{id}")
    public HttpResponse<User> updateUser(@PathVariable int id, @Body User user) {
            return HttpResponse.ok(_userService.updateUser(id, user));
    }

    @Delete("/{id}")
    public void deleteUser (@PathVariable int id) {
       _userService.deleteUser(id);
    }

}
