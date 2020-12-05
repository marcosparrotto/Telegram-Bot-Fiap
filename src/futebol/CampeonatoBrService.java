package futebol;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CampeonatoBrService {

    public String obtemRodada(String rodada) {
        StringBuilder sb = new StringBuilder("");

        try {
            String response = Unirest.get("https://api.api-futebol.com.br/v1/campeonatos/10/rodadas/" + rodada)
                    .header("Authorization", "Bearer live_4ff4e48bc1cbd4e0b321476316f49f")
                    .header("Cookie", "PHPSESSID=08egfm3867cjrb3mijan222fll").asString().getBody();

            RodadaModel rodadaModel = new Gson().fromJson(response, RodadaModel.class);

            sb.append(rodadaModel.getNome());
            sb.append(System.getProperty("line.separator"));
            sb.append("Status: ");
            sb.append(rodadaModel.getStatus());
            sb.append(System.getProperty("line.separator"));
            sb.append("Partidas: ");
            sb.append(System.getProperty("line.separator"));

            for (PartidaModel p : rodadaModel.getPartidas()) {
                sb.append(p.getPlacar());
                sb.append(System.getProperty("line.separator"));
                sb.append("Data de Realização: ");
                sb.append(p.getData_realizacao());
                sb.append(System.getProperty("line.separator"));
                sb.append("Estadio: ");
                sb.append(p.getEstadio().getNome_popular());
                sb.append(System.getProperty("line.separator"));
                sb.append(System.getProperty("line.separator"));
            }
        } catch (UnirestException e) {
            sb.append("Erro na consulta das informações");
        }

        return sb.toString();
    }

    public String obtemTabela() {
        Unirest.setTimeouts(0, 0);
        String response;
        try {
            response = Unirest.get("https://api.api-futebol.com.br/v1/campeonatos/10/tabela")
                    .header("Authorization", "Bearer live_4ff4e48bc1cbd4e0b321476316f49f")
                    .header("Cookie", "PHPSESSID=jc0m33kmi2u82tdlma0jpe5qbu").asString().getBody();

            Type listType = new TypeToken<ArrayList<TabelaModel>>() {
            }.getType();

            ArrayList<TabelaModel> tabelaModel = new Gson().fromJson(response, listType);

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-4s", "Pos"));
            sb.append(String.format("%-14s", "Time"));
            sb.append(String.format("%-5s", "Pts"));
            sb.append(String.format("%-5s", "Vit"));
            sb.append(String.format("%-5s", "Emp"));
            sb.append(String.format("%-5s", "Der"));
            sb.append(String.format("%-5s", "Jog"));
            sb.append(String.format("%-5s", "Apro"));
            sb.append(System.getProperty("line.separator"));

            for (TabelaModel tabelaModel2 : tabelaModel) {
                sb.append(String.format("%-3s", tabelaModel2.getPosicao()));
                sb.append(String.format("%-15s", tabelaModel2.getTime().getNome_popular()));
                sb.append(String.format("%-5s", tabelaModel2.getPontos()));
                sb.append(String.format("%-5s", tabelaModel2.getVitorias()));
                sb.append(String.format("%-5s", tabelaModel2.getEmpates()));
                sb.append(String.format("%-5s", tabelaModel2.getDerrotas()));
                sb.append(String.format("%-5s", tabelaModel2.getJogos()));
                sb.append(String.format("%-5s", tabelaModel2.getAproveitamento()));
                sb.append(System.getProperty("line.separator"));
            }

            return sb.toString();

        } catch (UnirestException e) {
            return "Erro na consulta das informações";
        }
    }
}
