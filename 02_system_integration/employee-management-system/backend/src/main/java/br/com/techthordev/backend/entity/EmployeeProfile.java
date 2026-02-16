package br.com.techthordev.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "employee_profile", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeProfile {

    @Id
    private Long id;

    /**
     * One-to-One → Employee
     */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "employee_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_employee_profile_employee")
    )
    private Employee employee;

    @Column(columnDefinition = "TEXT")
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

}
