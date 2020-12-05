package futebol;

public class Time {
    private String nome_popular;
    private String escudo;

    public String getNome_popular() {
        return nome_popular;
    }

    public void setNome_popular(String nome_popular) {
        this.nome_popular = nome_popular;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public Time(String nome_popular, String escudo) {
        this.nome_popular = nome_popular;
        this.escudo = escudo;
    }
}
