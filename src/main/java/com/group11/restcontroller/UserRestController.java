package com.group11.restcontroller;



import com.group11.entity.UserEntity;
import com.group11.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<Page<UserEntity>> getUser(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        String roleName = "Customer";
        Page<UserEntity> promotions = userService.getAllUsersByRoleName(roleName,page, size);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<Page<UserEntity>> searchUsers(@PathVariable String keyword,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Page<UserEntity> promotions = userService.searchUser(keyword,page, size);
        return ResponseEntity.ok(promotions);
    }

    @PutMapping("/active/{id}")
    public ResponseEntity<String> updateUserStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {

        boolean isUpdated = userService.activeUser(id, active);
        if (isUpdated) {
            String message = active ? "Tài khoản người dùng được kích hoạt thành công!" : "Tài khoản người dùng đã bị vô hiệu hóa thành công!";
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body("Không thể cập nhật trạng thái người dùng.");
        }
    }



}
