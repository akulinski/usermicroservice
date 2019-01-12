package com.msi.usermicroservice.core.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "history_entity")
@Getter
@Setter
@NoArgsConstructor
public class HistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_history")
    private Integer id;

    @Column(name = "value")
    @Type(type = "text")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id_user")
    @JsonIgnore
    private UserEntity userEntity;

}
