package futebol;

import java.util.List;

import lombok.Data;

/**
 * Classe de Modelo de uma rodada
 */

@Data
public class RodadaModel {
    private String nome;
    private String status;
    private List<PartidaModel> partidas;
}
