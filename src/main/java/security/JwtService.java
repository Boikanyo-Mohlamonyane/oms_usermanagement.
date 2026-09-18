package security;


import model.User;

public interface JwtService {

    String generateToken(User user);

    boolean validateToken(String token,String email);

    String extractEmailFromToken(String token);
}
