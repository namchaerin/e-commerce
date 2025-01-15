package kr.hhplus.be.server.domain.User;

import jakarta.persistence.*;
import kr.hhplus.be.server.common.BaseEntity;
import lombok.Getter;


@Entity
@Getter
public class User extends BaseEntity {

    private String name;

    public User(String name) {
        this.name = name;
    }

}
