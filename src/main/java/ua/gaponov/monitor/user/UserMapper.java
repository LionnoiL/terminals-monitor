package ua.gaponov.monitor.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.gaponov.monitor.utils.Mapper;

@RequiredArgsConstructor
@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO mapEntityToDto(User source) throws RuntimeException {
        UserDTO target = new UserDTO();
        target.setPassword(source.getPassword());
        target.setId(source.getUserId());
        target.setUserName(source.getUserName());
        target.setEnable(source.isEnable());
        target.setRole(source.getRole());
        return target;
    }


    @Override
    public User mapDtoToEntity(UserDTO source) throws RuntimeException {
        User target = new User();
        target.setPassword(source.getPassword());
        target.setUserId(source.getId());
        target.setUserName(source.getUserName());
        target.setEnable(source.isEnable());
        target.setRole(source.getRole());
        return target;
    }
}
