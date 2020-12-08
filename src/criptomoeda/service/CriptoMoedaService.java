package criptomoeda.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import criptomoeda.CriptoMoeda;

/** Classe que realiza a chamada da WebService https://www.mercadobitcoin.net/api/
 * @author Rafael Ferrés
 * @version 1.0 08 Dez 2020
 *  */
public class CriptoMoedaService {
	/**Propriedade endereÃ§o do WebService*/
	static String urlWs = "https://www.mercadobitcoin.net/api/";
	/**Propriedade de conexÃ£o obtida com o WebService*/
	static int sucesso = 200;
	
	/** Metodo que realiza a chamada da WebService https://www.mercadobitcoin.net/api/
	 * @author Rafael Ferrés
	 * @version 1.0 07 Dez 2020
	 * @param singla
	 *  */
	public static CriptoMoeda buscaCriptomoeda(String singla) throws Exception {
		String chamada = urlWs + singla + "/ticker/";
		
		try {
			URL url = new URL(chamada);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() != sucesso) {
				throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader resposta = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			JsonObject object = new JsonParser().parse(resposta).getAsJsonObject();

			return new GsonBuilder().create().fromJson(object.get("ticker"), CriptoMoeda.class);
		}catch(Exception e) {
			throw new Exception("Erro: " + e);
		}
	}
}
