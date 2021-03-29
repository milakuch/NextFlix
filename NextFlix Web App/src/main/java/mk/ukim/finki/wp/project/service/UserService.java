package mk.ukim.finki.wp.project.service;

import mk.ukim.finki.wp.project.model.User;
import mk.ukim.finki.wp.project.model.Role;

import java.util.List;

public interface UserService {

    /**
     * (5 points) This method should create a new user. The password should be encrypted before saving.
     *
     * @param username
     * @param password
     * @param role
     * @return
     */
    User create(String username, String password, Role role);
    User find (String username) throws Exception;
    List<User> listAll();

}
