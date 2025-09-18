package instrutor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import turno.Turno;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Instrutor {

    @Id
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Turno turno;
}
