package cep.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.GsonBuilder;

import cep.BuscarCEP;

public class BuscarCEPService {
	static String urlWs = "http://viacep.com.br/ws/";
	static int sucesso = 200;

	public static BuscarCEP buscaEnderecoPeloCep(String cep) throws Exception {

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
