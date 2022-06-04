package me.weekbelt.persistence.staffer.repository;

import me.weekbelt.persistence.staffer.Staffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StafferRepository extends JpaRepository<Staffer, String> {
}
