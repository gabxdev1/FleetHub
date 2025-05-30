package br.com.gabxdev.company.domain;

import br.com.gabxdev.core.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_company")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
