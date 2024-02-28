package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.entities.Client;
import com.rentmen.app.entities.Moderator;
import com.rentmen.app.entities.Role;
import com.rentmen.app.entities.ServiceProvider;
import com.rentmen.app.entities.Skill;
import com.rentmen.app.entities.User;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.ClientRepo;
import com.rentmen.app.repositories.ModeratorRepo;
import com.rentmen.app.repositories.RoleRepo;
import com.rentmen.app.repositories.ServiceProviderRepo;
import com.rentmen.app.repositories.SkillRepo;
import com.rentmen.app.repositories.UserRepo;
import com.rentmen.app.services.UserService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ClientRepo clientRepo;
    
    @Autowired
    private ModeratorRepo moderatorRepo;
    
    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private SkillRepo skillRepo;

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

		if (user.getDepId() == 1) {
			Client client = modelMapper.map(userDto, Client.class);
			client.setPassword(passwordEncoder.encode(client.getPassword()));
			client.getRoles().add(role);
			client = clientRepo.save(client);

			return modelMapper.map(client, UserDto.class);
		} else if (user.getDepId() == 2) {
			Moderator moderator = modelMapper.map(userDto, Moderator.class);
			moderator.setPassword(passwordEncoder.encode(moderator.getPassword()));
			moderator.getRoles().add(role);
			moderator = moderatorRepo.save(moderator);

			return modelMapper.map(moderator, UserDto.class);
		} else if (user.getDepId() == 3) {
			ServiceProvider sp = modelMapper.map(userDto, ServiceProvider.class);
			if(!userDto.getSkills().isEmpty()) {
				Set<Skill> skills = userDto.getSkills().stream()
                .map(skillDto -> skillRepo.findById(skillDto.getId())
                                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
                .collect(Collectors.toSet());
				sp.setSkills(skills);
			}
			sp.setPassword(passwordEncoder.encode(sp.getPassword()));
			sp.getRoles().add(role);
			sp = serviceProviderRepo.save(sp);
			
			return modelMapper.map(sp, UserDto.class);
		}

		user = userRepo.save(user);
		return modelMapper.map(user, UserDto.class);

	}

	public List<UserDto> getAllClients(){
		List<Client> clients  = clientRepo.findAll();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		return modelMapper.map(clients, listType);
	}
	
	public List<UserDto> getAllModerators(){
		List<Moderator> moderators  = moderatorRepo.findAll();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		return modelMapper.map(moderators, listType);
	}
	
	public List<UserDto> getAllServiceProviders(Float rating){
		List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		if(rating == null || rating == 0) {
		    serviceProviders = serviceProviderRepo.findAll();
		}else {
			serviceProviders = serviceProviderRepo.findByRatingGreaterThanOrEqual(rating);
		}
		return modelMapper.map(serviceProviders, listType);
	}
}
