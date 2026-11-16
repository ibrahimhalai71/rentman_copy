package com.rentmen.app.services.imp;

import com.rentmen.app.DTO.SkillDto;
import com.rentmen.app.DTO.UserDto;
import com.rentmen.app.entities.*;
import com.rentmen.app.exceptions.CustomException;
import com.rentmen.app.exceptions.ResourceNotFoundException;
import com.rentmen.app.repositories.*;
import com.rentmen.app.services.UserService;
import com.rentmen.app.utils.UtilFunctions;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        try {
            User user = dtoToUser(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User savedUser = userRepo.save(user);
            return userToDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new CustomException("Email already exists!");
            }
            throw new CustomException("An error occurred!");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
//		user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
//		user.setAbout(userDto.getAbout());
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
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.intValue()));
//        user.getRoles().clear();
//        userRepo.saveAndFlush(user);
//        userRepo.deleteUserReferences(userId);
//        userRepo.deleteUserReferencesInJobs(userId);
//        userRepo.delete(user);
        user.setActive(false);
        user.setStatus(Boolean.FALSE);
        userRepo.save(user);
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

        if ((userDto.getDayRate() != null && userDto.getDayRate() != 0)
                || (userDto.getNightRate() != null && userDto.getNightRate() != 0) || (userDto.getWeekendRate() != null && userDto.getWeekendRate() != 0)) {
            userDto.setTimeBasedRate(true);
        } else {
            userDto.setTimeBasedRate(false);
        }

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
        if (dto.getAvailabilityDates() != null) {
            serviceProvider.setAvailabilityDates(dto.getAvailabilityDates());
        }
        if (dto.getUnavailabilityDates() != null) {
            serviceProvider.setUnavailabilityDates(dto.getUnavailabilityDates());
        }
        if (dto.getUnknownDates() != null) {
            serviceProvider.setUnknownDates(dto.getUnknownDates());
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

        if (startDate.equals(endDate)) {
            serviceProviders = serviceProviderRepo.findServiceProvidersWithAvailabilityBetween(startDate, endDate);
        } else {
            List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
                    .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                    .collect(Collectors.toList());
            long size = dates.size();
            serviceProviders = serviceProviderRepo.findUsersAvailableOnDates(dates,size);
        }

        return modelMapper.map(serviceProviders, listType);
    }

    @Override
    public List<UserDto> getServiceProvidersUnavailableBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();

        if (startDate.equals(endDate)) {
            serviceProviders = serviceProviderRepo.findServiceProvidersWithUnavailabilityBetween(startDate, endDate);
        } else {
            List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
                    .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                    .collect(Collectors.toList());
            long size = dates.size();
            serviceProviders = serviceProviderRepo.findUsersUnavailableOnDates(dates,size);
        }

        return modelMapper.map(serviceProviders, listType);
    }

    @Override
    public List<UserDto> getServiceProvidersUnknownBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();

        if (startDate.equals(endDate)) {
            serviceProviders = serviceProviderRepo.findServiceProvidersWithUnknownBetween(startDate, endDate);
        } else {
            List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
                    .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                    .collect(Collectors.toList());
            long size = dates.size();
            serviceProviders = serviceProviderRepo.findUsersUnknownOnDates(dates,size);
        }

        return modelMapper.map(serviceProviders, listType);
    }

    @Override
    public List<UserDto> getServiceProvidersMaybeBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();

        if (startDate.equals(endDate)) {
            serviceProviders = serviceProviderRepo.findServiceProvidersWithMaybeBetween(startDate, endDate);
        } else {
            List<LocalDate> dates = Stream.iterate(startDate, date -> date.plusDays(1))
                    .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1)
                    .collect(Collectors.toList());
            long size = dates.size();
            serviceProviders = serviceProviderRepo.findUsersMaybeOnDates(dates);
        }

        return modelMapper.map(serviceProviders, listType);
    }


    public Set<Skill> getSkillSet(Set<SkillDto> skillDtoList) {
        return skillDtoList.stream()
                .map(skillDto -> skillRepo.findById(skillDto.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", skillDto.getId())))
                .collect(Collectors.toSet());
    }

    @Override
    public UserDto updateUserFile(Long userId, MultipartFile file, Integer docType) throws Exception {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (file != null && !file.isEmpty()) {
            String storedFileName = null;
            if (docType == 1) {// for profile image
                storedFileName = user.getProfileImage();
            } else if (docType == 2) {// for agreement
                storedFileName = user.getAgreement();
            } else {
                throw new Exception("Error: document type not mentioned");
            }
            if (storedFileName != null && !storedFileName.isEmpty()) {
                try {
                    UtilFunctions.deleteFile(storedFileName);
                } catch (Exception e) {
                    user.setProfileImage(null);
                    //userRepo.save(user);
                }
            }
            String fileName = UtilFunctions.createFileName(file, user.getEmail());
            String filePath = UtilFunctions.saveMultipartFileToPath(file, fileName, docType);
            if (filePath != null && !filePath.isEmpty()) {
                if (docType == 1) {
                    user.setProfileImage(fileName);
                } else if (docType == 2) {
                    user.setAgreement(fileName);
                }
                userRepo.save(user);
            } else {
                throw new Exception("Error: image save unsuccessful");
            }
        } else {
            throw new Exception("Error: Multipart Image null or empty");
        }
        return modelMapper.map(user, UserDto.class);
    }
}
