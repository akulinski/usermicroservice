package com.msi.usermicroservice.core.entites;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "history_entity")
@Getter
@Setter
@NoArgsConstructor
class HistoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_history")
    private Integer id;

    @Column(name = "value",precision = 255)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id_user")
    private UserEntity userEntity;

}
