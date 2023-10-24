package StoreManagement.userManagement.user;

import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.userManagement.dto.UserMapper;
import StoreManagement.userManagement.dto.UserRegistrationReq;
import StoreManagement.userManagement.dto.UserResponse;
import StoreManagement.userManagement.dto.UserUpdateReq;
import StoreManagement.userManagement.role.Role;
import StoreManagement.userManagement.role.RoleService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse register(UserRegistrationReq userReq) {
        if (userUtils.isEmailTaken(userReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken");

        if (userUtils.isUsernameTaken(userReq.getUsername()))
            throw new ResourceAlreadyExistsException("Username is already taken");

        Role role = roleService.getRoleById(userReq.getRoleId());

        Users loggedInUser = currentlyLoggedInUser.getUser();
        Users user = userUtils.createUser(userReq, loggedInUser, role);
        Users savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse editUser(UserUpdateReq updateReq) {
        Users user = userUtils.getById(updateReq.getUserId());

        if (updateReq.getFullName() != null)
            user.setFullName(updateReq.getFullName());

        // Update email if provided and different from the current email
        if (updateReq.getEmail() != null && !user.getEmail().equalsIgnoreCase(updateReq.getEmail())) {
            // Check if the new email is already taken
            if (userUtils.isEmailTaken(updateReq.getEmail()))
                throw new ResourceAlreadyExistsException("Email is already taken");

            user.setEmail(updateReq.getEmail());
        }

        // Update username if provided and different from the current username
        if (updateReq.getUsername() != null && !user.getUsername().equals(updateReq.getUsername())) {
            // Check if the new username is already taken
            if (userUtils.isUsernameTaken(updateReq.getUsername()))
                throw new ResourceAlreadyExistsException("Username is already taken");

            user.setUsername(updateReq.getUsername());
        }

        Users savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse me() {
        Users user = currentlyLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers() {
        List<Users> users = userRepository.findAll(Sort.by(Sort.Order.asc("id")));
        return users.stream().map(UserMapper::toUserResponse).toList();
    }
//
//    @Override
//    public StoreInventoryResponse blockUser(Long id) {
//        Users user = userUtils.getById(id);
//
//        user.setUserStatus(Status.BLOCKED);
//        user.setUpdatedBy(currentLoggedInUser.getUser().getFullName());
//
//        Users savedUser = userRepository.save(user);
//        return StoreInventoryMapper.toUserResponse(savedUser);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public ResponseEntity<ApiResponse> delete(Long id) {
//        Users user = userUtils.getById(id);
//
//        // Set user attributes for deletion
//        user.setDeleted(true);
//        user.setDeletedAt(LocalDateTime.now().toString());
//        user.setDeleteBy(currentLoggedInUser.getUser().getFullName());
//
//        userRepository.save(user);
//        return ApiResponse.success("Password Changed Successfully!");
//    }

}
