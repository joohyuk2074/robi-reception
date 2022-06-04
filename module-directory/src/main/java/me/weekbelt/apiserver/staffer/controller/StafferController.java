package me.weekbelt.apiserver.staffer.controller;

import lombok.RequiredArgsConstructor;
import me.weekbelt.apiserver.staffer.dto.StafferCreateRequest;
import me.weekbelt.apiserver.staffer.dto.StafferResponse;
import me.weekbelt.apiserver.staffer.service.StafferService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class StafferController {

    private final StafferService stafferService;

    @PostMapping("/v1/staffers")
    public StafferResponse save(@RequestBody StafferCreateRequest createRequest) {
        return stafferService.save(createRequest);
    }
}
