package me.weekbelt.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phone {

    @Column(nullable = false)
    private String number;

    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;
}
