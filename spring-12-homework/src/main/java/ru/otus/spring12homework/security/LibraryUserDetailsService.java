package ru.otus.spring12homework.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring12homework.dao.UserRepository;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LibraryUserDetails(
                userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username))
        );
    }
}
