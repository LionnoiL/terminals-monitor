package ua.gaponov.monitor.user;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.gaponov.monitor.utils.Helper;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
            new NoSuchElementException("User with id: [" + id + "] does not exist!"));
    }

    public void updateUser(UserDTO userDTO) {
        UserDTO dto = userMapper.mapEntityToDto(findUserById(userDTO.getId()));
        BeanUtils.copyProperties(userDTO, dto, Helper.getNullPropertyNames(userDTO));
        userValidator.setUserService(this);
        userValidator.validate(dto, false);
        userRepository.save(userMapper.mapDtoToEntity(dto));
    }

    public void updateUser(String email,
        String password) {
        User currentUser = getCurrentUser();
        UserDTO userDTO = userMapper.mapEntityToDto(currentUser);
        userDTO.setUserName(email);

        if (!password.isBlank()) {
            if (!passwordEncoder.matches(password, currentUser.getPassword())) {
                userDTO.setPassword(passwordEncoder.encode(password));
            }
        }
        updateUser(userDTO);
    }

    public void createNewUser(String username, String password) {
        int userCount = userRepository.findAll().size();
        UserRoles role = UserRoles.ROLE_USER;
        if (userCount == 0){
            role = UserRoles.ROLE_ADMIN;
        }
        UserDTO userDTO = UserDTO.builder()
            .userName(username)
            .password(password)
            .isEnable(true)
            .role(role)
            .build();
        userValidator.setUserService(this);
        userValidator.validate(userDTO, true);
        userDTO.setPassword(passwordEncoder.encode(password));
        User user = userMapper.mapDtoToEntity(userDTO);
        userRepository.save(user);
    }

    public User findUserByName(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
            new NoSuchElementException("User with name: [" + userName + "] does not exist!"));
        return user;
    }

    public User getCurrentUser() {
        String username = "";
        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
            username = principal.getUsername();
        } catch (Exception e) {
            //NOP
        }
        return findUserByName(username);
    }
}
