package StoreManagement.userManagement.user;

import StoreManagement.userManagement.dto.UserRegistrationReq;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.dto.UserUpdateReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users API.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMe() {
        return ResponseEntity.ok(userService.me());
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRegistrationReq userReq) {
        UserResponse user = userService.register(userReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<UserResponse> editUser(@RequestBody @Valid UserUpdateReq updateReq) {
        return ResponseEntity.ok(userService.editUser(updateReq));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> editOtherUserAccount(
            @PathVariable Long userId,
            @RequestBody @Valid UserUpdateReq updateReq) {
        return ResponseEntity.ok(userService.editOtherUserAccount(userId, updateReq));
    }

//    public ResponseEntity<StoreInventoryResponse> blockUser(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.blockUser(id));
//    }
//
//    @DeleteMapping({"/{id}"})
//    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
//        return userService.delete(id);
//    }

}


