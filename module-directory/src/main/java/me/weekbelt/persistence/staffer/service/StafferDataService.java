package me.weekbelt.persistence.staffer.service;

import lombok.RequiredArgsConstructor;
import me.weekbelt.persistence.staffer.Staffer;
import me.weekbelt.persistence.staffer.repository.StafferRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StafferDataService {

    private final StafferRepository stafferRepository;

    public Staffer save(Staffer staffer) {
        return stafferRepository.save(staffer);
    }
}
