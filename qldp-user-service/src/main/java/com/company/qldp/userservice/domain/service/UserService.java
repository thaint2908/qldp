package com.company.qldp.userservice.domain.service;

import com.company.qldp.domain.Role;
import com.company.qldp.domain.User;
import com.company.qldp.userservice.domain.dto.UserDto;
import com.company.qldp.userservice.domain.exception.RoleNotFoundException;
import com.company.qldp.userservice.domain.exception.UserAlreadyExistException;
import com.company.qldp.userservice.domain.exception.UserNotFoundException;
import com.company.qldp.userservice.domain.repository.RoleRepository;
import com.company.qldp.userservice.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    
    @Autowired
    public UserService(
        UserRepository userRepository,
        RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    
    public User createUser(UserDto userDto, String keycloakUid) {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("email");
        }
        
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("name");
        }
        
        List<Role> roleList = getRoleList(userDto);
        
        User user = User.builder()
            .username(userDto.getUsername())
            .email(userDto.getEmail())
            .keycloakUid(keycloakUid)
            .roles(roleList)
            .build();
        
        return userRepository.save(user);
    }
    
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
    
    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
    
    private List<Role> getRoleList(UserDto userDto) {
        List<Role> roleList = new ArrayList<>();
        
        for (String roleStr : userDto.getRoles()) {
            Integer roleId = Integer.parseInt(roleStr);
            
            Optional<Role> role = roleRepository.findById(roleId);
            if (role.isEmpty()) {
                throw new RoleNotFoundException();
            }
            
            roleList.add(role.get());
        }   
        
        return roleList;
    }
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public User findUserById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    }
    
    public List<User> findUsers() {
        return userRepository.findAll();
    }
}
