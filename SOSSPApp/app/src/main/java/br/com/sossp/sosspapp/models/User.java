package br.com.sossp.sosspapp.models;

import lombok.Data;

@Data
public class User {

    private Long userId;
    private String name;
    private String lastName;
    private String tag;
    private String dateOfbirth;
    private Genre genre;
    private String cpf;
    private String tel;
    private String email;
    private String password;
    private String imgAvatar;

}
