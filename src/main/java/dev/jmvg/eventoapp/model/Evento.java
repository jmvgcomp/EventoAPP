package dev.jmvg.eventoapp.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long codigo;
    @NotEmpty
    private String nome;
    @NotEmpty
    private String local;
    @NotEmpty
    private String data;
    @NotEmpty
    private String horario;

    @OneToMany
    private List<Convidados> convidados;

    public List<Convidados> getConvidados() {
        return convidados;
    }

    public void setConvidados(List<Convidados> convidados) {
        this.convidados = convidados;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
