package cep.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.GsonBuilder;

import cep.BuscarCEP;

/** Classe que realiza a chamada da WebService http://viacep.com.br/ws/ 
 * @author Yuri
 * @version 1.0 07 Dez 2020
 *  */
public class BuscarCEPService {
	/**Propriedade endereço do WebService*/
	static String urlWs = "http://viacep.com.br/ws/";
	/**Propriedade de conexão obtida com o WebService*/
	static int sucesso = 200;

	
	/** Método que realiza a chamada da WebService http://viacep.com.br/ws/ 
	 * @author Yuri
	 * @version 1.0 07 Dez 2020
	 * @param cep
	 *  */
	public static BuscarCEP buscaEnderecoPeloCep(String cep) throws Exception {
		/**Propriedade url do WebService*/
		String chamada = urlWs + cep + "/json";

		try {
			URL url = new URL(chamada);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() != sucesso) {
				throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader resposta = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			return new GsonBuilder().create().fromJson(resposta, BuscarCEP.class);

		} catch (Exception e) {
			throw new Exception("Erro: " + e);
		}

	}

}
