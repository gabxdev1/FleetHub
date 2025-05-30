package br.com.gabxdev.iam.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_service_catalog")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private Long code;
}
