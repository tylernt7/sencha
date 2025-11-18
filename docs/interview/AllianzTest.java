import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/*# Task: Implement a GET Endpoint to Return User Information

Implement a GET endpoint /api/user in a Spring Boot application that returns user information based on a JWT token
provided in the Authorization header. The JWT token contains the user's email address.
If the email address is not present in the token or the token is invalid, the endpoint should return an HTTP status 403 FORBIDDEN.

        ## Requirements:

        ### JWT Token Processing:

The JWT token is passed in the Authorization header as Bearer <token>.
Extract the email address from the JWT token.

        ### User Information:

Use the provided UserDto class to represent user information.
Implement the method getUserByEmail(String email) in the UserDao class to retrieve user information based on the email address.

        ### Error Handling:

Return 403 FORBIDDEN if the email address is not present in the token or the token is invalid.

        ## Provided Classes:
*/
@Data
public class UserDto {
    private String uuid; // Should normally be UUID uuid, just as an example.
    private String firstName;
    private String lastName;
    private String email;

    public UserDto(String uuid, String firstName, String lastName, String email) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final JwtDecoder jwtDecoder;
    private final UserDao userDao;

    public UserController(JwtDecoder jwtDecoder, UserDao userDao) {
        this.jwtDecoder = jwtDecoder;
        this.userDao = userDao;
    }	
	
    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authHeader) {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            String token = authHeader.substring(7);
			try {
                Jwt jwt = jwtDecoder.decode(token);
			} catch (JwtException e) {
			    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}				

            String email = jwt.getClaimAsString("email");
            if (email == null || email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            UserDto user = userDao.getUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(user);		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
        var token = authHeader.replaceAll("Bearer ");

        var forbidden = ResponseEntity.status(HttpStatus.FORBIDDEN).body("str2");
        Jwt jwt;
        try {
            jwt = jwtDecoder.decode(token);
        } catch (Exception e) {
            return forbidden;
        }
        var exp = jwt.getExpiresAt();

        var email = jwt.getClaim("email");

        var user = userDao.getUserByEmail(email.toString());

        var ok = ResponseEntity.status(HttpStatus.OK).body(user);
        if (email == null || !exp.equals(Instant.now())) {

            return forbidden;
        }

        return ok;
    }

@Service
public class UserDao {
    public UserDto getUserByEmail(String email) {
        // Example implementation that returns a fixed user


        return new UserDto("01987aeb-bf0f-7240-b39f-754e10b46d04", "Steffen", "Kruschina",
                "steffen.kruschina@allianz.de");
    }
}
 
/*## Provided additional Dependencys

The Library JwtDecoder jwtDecoder should be used.*/