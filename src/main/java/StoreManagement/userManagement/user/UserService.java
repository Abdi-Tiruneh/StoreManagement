package StoreManagement.userManagement.user;

import StoreManagement.userManagement.dto.UserUpdateReq;
import StoreManagement.userManagement.dto.ChangePassword;
import StoreManagement.userManagement.dto.UserRegistrationReq;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse register(UserRegistrationReq userReq);

    UserResponse me();

    List<UserResponse> getUsers();

    //
//    StoreInventoryResponse blockUser(Long id);
//
    UserResponse editUser(UserUpdateReq updateReq);
//
//    ResponseEntity<ApiResponse> delete(Long id);
}
