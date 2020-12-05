package futebol;

import lombok.Data;

@Data
public class TabelaModel {
    private String posicao;
    private String pontos;
    private Time time;
    private String jogos;
    private String vitorias;
    private String empates;
    private String derrotas;
    private String aproveitamento;
}
