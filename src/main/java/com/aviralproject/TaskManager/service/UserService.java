package com.aviralproject.TaskManager.service;

import com.aviralproject.TaskManager.dto.AddUserRequestdto;
import com.aviralproject.TaskManager.dto.Userdto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    Userdto createUser(AddUserRequestdto request);

    Userdto getUserById(Long id);

    Page<Userdto> getAllUsers(int page,int size, String sortBy, String order);

    Userdto updateUser(Long id, AddUserRequestdto request);

    void deleteUser(Long id);
}
