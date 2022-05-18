package ru.itis.shop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.shop.models.User;
import ru.itis.shop.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findUserByUsername(username);
        if (userCandidate.isPresent()) {
            User user = userCandidate.get();
            return UserDetailsImpl.builder()
                    .userId(user.getId())
                    .username(username)
                    .password(user.getHashPassword())
                    .roles(user.getRoles())
                    .build();
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
