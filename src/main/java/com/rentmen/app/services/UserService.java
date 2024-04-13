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
  
  List<UserDto> getAllClients();
  
  List<UserDto> getAllServiceProviders(Float rating);
  
  List<UserDto> getAllModerators();
  
  UserDto getClient(Long id);
  
  UserDto getModerator(Long id);
  
  UserDto getServiceProvider(Long id);
  
  UserDto updateClient(UserDto dto);
  
  UserDto updateModerator(UserDto dto);
 
  UserDto updateServiceProvider(UserDto dto);
  
}