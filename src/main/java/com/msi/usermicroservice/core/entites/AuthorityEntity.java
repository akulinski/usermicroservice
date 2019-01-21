package com.msi.usermicroservice.core.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authority")
@Getter
@Setter
@NoArgsConstructor
public class AuthorityEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_authority")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id_user")
    private UserEntity userEntity;

}
