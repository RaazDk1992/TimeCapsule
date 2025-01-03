package com.raazdk.TimeCapsule.mappers;

import com.raazdk.TimeCapsule.models.TUser;
import com.raazdk.TimeCapsule.security.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDTOMapper {
    TUser toUser(RegisterRequest registerRequest);
    RegisterRequest toDto(TUser user);
}
