package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.entities.Role;
import com.rentmen.app.entities.User;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.RoleRepo;
import com.rentmen.app.repositories.UserRepo;
import com.rentmen.app.services.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);
        return userToDto(savedUser);
    }

    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        User updatedUser = userRepo.save(user);
        UserDto updatedUserDto = userToDto(updatedUser);
        return updatedUserDto;
    }

    public UserDto getUserById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
        return userToDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
        userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public UserDto registerNewUser(UserDto userDto) {
        Role role;
        User user = modelMapper.map(userDto, User.class);

        System.out.println(user.getDepId());

        System.out.println("PRRINTTIINNG");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        if (userDto.getDepartmentsSet().stream().anyMatch(e -> e.getDepName().equals("ADMIN"))) {
            role = roleRepo.findById(1).get();
//        } else {
//            role = roleRepo.findById(2).get();
//        }
        user.getRoles().add(role);

        User newUser = userRepo.save(user);

        return modelMapper.map(newUser, UserDto.class);
    }

}
