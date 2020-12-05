package futebol;

import lombok.Data;

@Data
public class PartidaModel {
    private String placar;
    private String data_realizacao;
    private EstadioModel estadio;
}
