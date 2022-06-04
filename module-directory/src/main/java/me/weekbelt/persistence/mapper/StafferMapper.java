package me.weekbelt.persistence.mapper;

import java.util.UUID;
import me.weekbelt.apiserver.staffer.dto.StafferCreateRequest;
import me.weekbelt.apiserver.staffer.dto.StafferResponse;
import me.weekbelt.persistence.Phone;
import me.weekbelt.persistence.PhoneType;
import me.weekbelt.persistence.staffer.Staffer;

public class StafferMapper {

    public static Staffer toStaffer(StafferCreateRequest createRequest) {
        Phone phone = Phone.builder()
            .number(createRequest.getNumber())
            .phoneType(PhoneType.valueOf(createRequest.getPhoneType()))
            .build();

        return Staffer.builder()
            .id(UUID.randomUUID().toString())
            .name(createRequest.getName())
            .phone(phone)
            .branchId(createRequest.getBranchId())
            .build();
    }

    public static StafferResponse toStafferResponse(Staffer staffer) {
        return StafferResponse.builder()
            .department(staffer.getDepartment().getName())
            .name(staffer.getName())
            .number(staffer.getPhone().getNumber())
            .phoneType(staffer.getPhone().getPhoneType().name())
            .branchId(staffer.getBranchId())
            .build();
    }
}
