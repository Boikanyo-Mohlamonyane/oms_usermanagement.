package service;

import dto.RegisterRequest;
import dto.RegisterResponse;
import enums.Role;
import jakarta.transaction.Transactional;
import model.Credentials;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.UserCredentialRepository;
import repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
@Autowired
private UserRepository userRepository;

@Autowired
private UserCredentialRepository userCredentialRepository;;

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
          User user =User.builder()
                  .first_name(registerRequest.getFirst_name())
                  .surname(registerRequest.getSurname())
                  .email(registerRequest.getEmail())
                  .role(Role.CUSTOMER)
                  .build();

         user= userRepository.save(user);

        Credentials credentials = Credentials.builder()
                .user(user)
                .password_hash(registerRequest.getPassword())
                .build();

        userCredentialRepository.save(credentials);



        return toResponse(user);
    }

    private RegisterResponse toResponse(User user){

       return RegisterResponse
               .builder().customerId(user.getCustomer_id())
               .firstName(user.getFirst_name())
               .surname(user.getSurname())
               .email(user.getEmail())
               .role(user.getRole().name()).build();

    }
}
