package futebol;

import lombok.Data;

/**
 * Classe de Modelo de uma Partida
 */

@Data
public class PartidaModel {
    private String placar;
    private String data_realizacao;
    private EstadioModel estadio;
}
