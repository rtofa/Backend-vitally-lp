package br.com.vitallyoficial.api.infrastructure.entity;

import br.com.vitallyoficial.api.domain.model.LeadType;
import br.com.vitallyoficial.api.domain.model.RdSyncStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_lead")
public class LeadEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Enumerated(EnumType.STRING)
    private LeadType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "rd_sync_status", nullable = false)
    private RdSyncStatus rdSyncStatus = RdSyncStatus.PENDING;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "lead", cascade = CascadeType.ALL)
    private List<LeadItemEntity> items;

}
