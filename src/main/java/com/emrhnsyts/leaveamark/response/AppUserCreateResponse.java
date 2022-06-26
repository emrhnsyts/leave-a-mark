package com.emrhnsyts.leaveamark.response;

import com.emrhnsyts.leaveamark.entity.AppUser;
import lombok.Data;

@Data
public class AppUserCreateResponse {
    private Long userId;
    private String username;
    private String email;

    public AppUserCreateResponse(AppUser appUser) {
        this.userId = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
    }
}
