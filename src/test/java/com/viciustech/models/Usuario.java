package com.viciustech.models;

public class Usuario {
    private String nome;
    private String email;
    private String password;
    private String administrador;

    // Construtor para facilitar a criação do objeto
    public Usuario(String nome, String email, String password, String administrador) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.administrador = administrador;
    }

    // Getters e Setters (O Jackson/Gson do RestAssured precisa deles para converter para JSON)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAdministrador() { return administrador; }
    public void setAdministrador(String administrador) { this.administrador = administrador; }
}
