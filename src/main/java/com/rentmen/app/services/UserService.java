package com.rentmen.app.services;

import com.rentmen.app.DTO.UserDto;
import java.util.List;

public interface UserService {
  UserDto createUser(UserDto paramUserDto);
  
  UserDto updateUser(UserDto paramUserDto, Long paramInteger);
  
  UserDto getUserById(Long paramInteger);
  
  List<UserDto> getAllUsers();
  
  UserDto registerNewUser(UserDto paramUserDto);
  
  void deleteUser(Long paramInteger);
  
}