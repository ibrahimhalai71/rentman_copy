package com.rentmen.app.services.imp;

import com.rentmen.app.Constants.Constants;
import com.rentmen.app.DTO.SkillDto;
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
import com.rentmen.app.utils.UtilFunctions;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		User savedUser = userRepo.save(user);
		return userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		User updatedUser = userRepo.save(user);
		UserDto updatedUserDto = userToDto(updatedUser);
		return updatedUserDto;
	}

	@Override
	public UserDto getUserById(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
		return userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Long userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
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

	@Override
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
			if (!userDto.getSkills().isEmpty()) {
				sp.setSkills(getSkillSet(userDto.getSkills()));
			}
			sp.setPassword(passwordEncoder.encode(sp.getPassword()));
			sp.getRoles().add(role);
			sp = serviceProviderRepo.save(sp);
			
			return modelMapper.map(sp, UserDto.class);
		}

		user = userRepo.save(user);
		return modelMapper.map(user, UserDto.class);

	}

	@Override
	public List<UserDto> getAllClients() {
		List<Client> clients = clientRepo.findAll();
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		return modelMapper.map(clients, listType);
	}

	@Override
	public List<UserDto> getAllModerators() {
		List<Moderator> moderators = moderatorRepo.findAll();
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		return modelMapper.map(moderators, listType);
	}

	@Override
	public List<UserDto> getAllServiceProviders(Float rating) {
		List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();
		if (rating == null || rating == 0) {
			serviceProviders = serviceProviderRepo.findAll();
		} else {
			serviceProviders = serviceProviderRepo.findByRatingGreaterThanOrEqual(rating);
		}
		return modelMapper.map(serviceProviders, listType);
	}

	@Override
	public UserDto getClient(Long id) {
		Client client = clientRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
		return modelMapper.map(client, UserDto.class);
	}

	@Override
	public UserDto getModerator(Long id) {
		Moderator mod = moderatorRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Moderator", "id", id));
		return modelMapper.map(mod, UserDto.class);
	}

	@Override
	public UserDto getServiceProvider(Long id) {
		ServiceProvider sp = serviceProviderRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", id));
		return modelMapper.map(sp, UserDto.class);
	}

	@Override
	public UserDto updateClient(UserDto dto) {
		Client client = clientRepo.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Client", "id", dto.getId()));
		Client updatedClient = modelMapper.map(dto, Client.class);
		try {
			UtilFunctions.mergeObjects(updatedClient, client);
			updatedClient = clientRepo.save(updatedClient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMapper.map(updatedClient, UserDto.class);
	}
	
	@Override
	public UserDto updateModerator(UserDto dto) {
		Moderator moderator = moderatorRepo.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Moderator", "id", dto.getId()));
		Moderator updatedModerator = modelMapper.map(dto, Moderator.class);
		try {
			UtilFunctions.mergeObjects(updatedModerator, moderator);
			updatedModerator = moderatorRepo.save(updatedModerator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMapper.map(updatedModerator, UserDto.class);
	}
	
	@Override
	public UserDto updateServiceProvider(UserDto dto) {
		ServiceProvider serviceProvider = serviceProviderRepo.findById(dto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ServiceProvider", "id", dto.getId()));
		ServiceProvider updatedServiceProvider = modelMapper.map(dto, ServiceProvider.class);
		if (!dto.getSkills().isEmpty()) {
			serviceProvider.setSkills(getSkillSet(dto.getSkills()));
		}
		try {
			UtilFunctions.mergeObjects(serviceProvider, updatedServiceProvider);
			updatedServiceProvider = serviceProviderRepo.save(serviceProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMapper.map(updatedServiceProvider, UserDto.class);
	}
	
	@Override
	public List<UserDto> getServiceProvidersAvailableBetweenDates(LocalDate startDate, LocalDate endDate) {
		List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
		Type listType = new TypeToken<List<UserDto>>() {
		}.getType();

		serviceProviders = serviceProviderRepo.findAvailableBetweenDates(startDate, endDate);

		return modelMapper.map(serviceProviders, listType);
	}
	
	public Set<Skill> getSkillSet(Set<SkillDto> skillDtoList){
		return skillDtoList.stream()
				.map(skillDto -> skillRepo.findById(skillDto.getId())
						.orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
				.collect(Collectors.toSet());
	}
	@Override
	public UserDto updateUserProfileImage(Long userId, MultipartFile image) throws Exception {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		if (image != null && !image.isEmpty()) {
			boolean toReturn = deleteUserProfileImage(user.getProfileImage());
			if (toReturn) {
				String imageName = UtilFunctions.createFileName(image, user.getName());
				String imagePath = UtilFunctions.saveMultipartFileToPath(image, imageName);
				if (imagePath != null && !imagePath.isEmpty()) {
					user.setProfileImage(imageName);
					userRepo.save(user);
				} else {
					throw new Exception("Error: image save unsuccessful");
				}
			} else {
				throw new Exception("Error: image deletion unsuccessful");
			}
		} else {
			throw new Exception("Error: Multipart Image null or empty");
		}
		return modelMapper.map(user, UserDto.class);
	}
	
	private boolean deleteUserProfileImage(String oldImagePath) {
		boolean toReturn = false;
		if (oldImagePath != null && !oldImagePath.isEmpty()) {
			File fileToDelete = new File(Constants.PROFILE_IMAGE_PATH+"/"+oldImagePath);
			if (fileToDelete.exists()) {
				// Delete the file
				if (fileToDelete.delete()) {
					System.out.println("File deleted successfully: " + oldImagePath);
					toReturn = true;
				} else {
					System.out.println("Failed to delete file: " + oldImagePath);
				}
			} else {
				System.out.println("File does not exist: " + oldImagePath);
			}
		}else {
			toReturn = true;
		}
		return toReturn;
	}
	
}
