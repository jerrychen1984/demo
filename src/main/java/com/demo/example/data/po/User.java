package com.demo.example.data.po;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutz.dao.entity.annotation.*;

import java.util.Date;

@Data
@Table("t_users")
@NoArgsConstructor
public class User {
    @Id
    private Long id;

    @Name
    @ColDefine(width = 64, notNull = true)
    private String username;

    @Column
    @ColDefine(width = 64, notNull = true)
    private String password;

    @Column
    @ColDefine(width = 64)
    private String email;

    @Column("gmt_pwd_reset")
    private Date lastPasswordResetDate;

}




