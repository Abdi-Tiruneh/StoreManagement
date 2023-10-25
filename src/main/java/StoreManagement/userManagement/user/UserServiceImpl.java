package StoreManagement.userManagement.user;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ForbiddenException;
import StoreManagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import StoreManagement.userManagement.dto.*;
import StoreManagement.userManagement.role.Role;
import StoreManagement.userManagement.role.RoleService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final RoleService roleService;
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
        Users user = currentlyLoggedInUser.getUser();

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
        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }


    @Override
    @Transactional
    public UserResponse editUserRoleAndStatus(Long userId, UserRoleAndStatusUpdateReq updateReq) {
        Users loggedInUserUser = currentlyLoggedInUser.getUser();
        if (!loggedInUserUser.getRole().getRoleName().equalsIgnoreCase("ADMIN"))
            throw new ForbiddenException("Admin access required.");

        Users user = userUtils.getById(userId);

        if (updateReq.getStatus() != null)
            changeStatus(user, updateReq.getStatus());

        if (updateReq.getRoleId() != null)
            changeUserRole(user, updateReq.getRoleId());

        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    private void changeStatus(Users user, String status) {
        if (!("ACTIVE".equals(status) || "SUSPENDED".equals(status) || "BANNED".equals(status)))
            throw new BadRequestException("Invalid status. Status should be one of: ACTIVE, SUSPENDED, BANNED");

        user.setUserStatus(UserStatus.getEnum(status));
    }

    private void changeUserRole(Users user, Short roleId) {
        Role role = roleService.getRoleById(roleId);
        user.setRole(role);
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
