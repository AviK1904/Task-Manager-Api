package com.aviralproject.TaskManager.service.impl;

import com.aviralproject.TaskManager.dto.AddUserRequestdto;
import com.aviralproject.TaskManager.dto.Userdto;
import com.aviralproject.TaskManager.entity.User;
import com.aviralproject.TaskManager.exception.UserNotFoundException;
import com.aviralproject.TaskManager.repository.UserRepository;
import com.aviralproject.TaskManager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Userdto createUser(AddUserRequestdto request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTimezone(request.getTimezone());
        user.setActive(request.isActive());
        User saved = userRepository.save(user);
        return  modelMapper.map(saved,Userdto.class);
    }

    @Override
    public Userdto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        return modelMapper.map(user,Userdto.class);
    }

    @Override
    public Page<Userdto> getAllUsers(int page,int size, String sortBy, String order) {
        Sort.Direction direction= order.equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
        Pageable pageable= PageRequest.of(page,size, Sort.by(direction, sortBy));

        return userRepository.findAll(pageable)
                .map(user->modelMapper.map(user,Userdto.class));
    }

    @Override
    public Userdto updateUser(Long id, AddUserRequestdto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setTimezone(request.getTimezone());
        user.setActive(request.isActive());
        return modelMapper.map(userRepository.save(user),Userdto.class);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


}
