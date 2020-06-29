package com.invivoo.vivwallet.api.domain.expertise;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExpertiseMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;

    @Enumerated(EnumType.STRING)
    private Expertise expertise;

    @Enumerated(EnumType.STRING)
    private ExpertiseMemberStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

}
