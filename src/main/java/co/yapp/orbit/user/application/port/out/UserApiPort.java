package co.yapp.orbit.user.application.port.out;

import co.yapp.orbit.user.adapter.out.response.UserInfoResponse;

public interface UserApiPort {
    UserInfoResponse getUserInfo(Long userId);
}
