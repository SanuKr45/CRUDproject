package com.example.CRUDproject.CRUDproject.Service;

import com.example.CRUDproject.CRUDproject.Dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    UserDto createUser(UserDto userDto);

    Page<UserDto> getAllUsers(int page, int size);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);
}
