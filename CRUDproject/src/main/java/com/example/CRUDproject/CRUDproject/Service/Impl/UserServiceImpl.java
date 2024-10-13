package com.example.CRUDproject.CRUDproject.Service.Impl;

import com.example.CRUDproject.CRUDproject.Dto.UserDto;
import com.example.CRUDproject.CRUDproject.Entity.User;
import com.example.CRUDproject.CRUDproject.Repository.UserRepository;
import com.example.CRUDproject.CRUDproject.Service.UserService;
import com.example.CRUDproject.CRUDproject.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDto.getEmail() + " already exists.");
        }

        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(savedUser, UserDto.class)).getBody();
    }


    @Override
    public Page<UserDto> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size))
                .map(user -> modelMapper.map(user, UserDto.class));
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(value -> modelMapper.map(value, UserDto.class))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setEmail(userDto.getEmail());
            user.setAddress(userDto.getAddress());
            User updatedUser = userRepository.save(user);
            return modelMapper.map(updatedUser, UserDto.class);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}