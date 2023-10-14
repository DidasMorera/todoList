package mz.com.saifodine.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

/*
 * public- qualquer pode acessar
 * protected- so no mesmo pacote
 * private- restricao
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;
    

    /*Atributos
    char
     * Date(data)
     * void
     */

     /*
      * Body
      */
      @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel){

        var user = this.userRepository.findByUsername(userModel.getUsername());

        if(user !=null){
            
            //Mensagem erro ou status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
        }

       var passwordHashred =  BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }
}
