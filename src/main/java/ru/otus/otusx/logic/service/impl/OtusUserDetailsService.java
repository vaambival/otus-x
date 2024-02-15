package ru.otus.otusx.logic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.otusx.dao.UserDao;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtusUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        var userBox = userDao.findByUuid(UUID.fromString(uuid));
        if (userBox.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь с идентификатором " + uuid + " не найден");
        }
        var user = userBox.get();
        return new User(uuid, user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
    }
}
