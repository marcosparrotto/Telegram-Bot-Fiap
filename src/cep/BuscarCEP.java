package cep;

/** Classe que representa o endereço que será retornado da API 
 * @author Yuri
 *  @version 1.0 07 Dez 2020
 *  */

public class BuscarCEP {
	/**Propriedade Logradouro*/
	private String logradouro;
	/**Propriedade CEP*/
	private String cep;
	/**Propriedade Bairro*/
	private String bairro;
	/**Propriedade Complemento*/
	private String complemento;
	/**Propriedade Localidade*/
	private String localidade;

	
	/**Recupera a propriedade Logradouro
	 * @return logradouro*/
	public String getLogradouro() {
		return logradouro;
	}
	
	/**Configura a propriedade Logradouro
	 * @param logradouro*/
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	/**Recupera a propriedade CEP
	 * @return CEP*/
	public String getCep() {
		return cep;
	}
	
	/**Configura a propriedade CEP
	 * @param CEP*/
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	/**Recupera a propriedade Bairro
	 * @return bairro*/
	public String getBairro() {
		return bairro;
	}
	
	/**Configura a propriedade Bairro
	 * @param bairro*/
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	/**Recupera a propriedade Complemento
	 * @return complemento*/
	public String getComplemento() {
		return complemento;
	}
	
	/**Configura a propriedade Complemento
	 * @param complemento*/
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
	/**Recupera a propriedade Localidade
	 * @return localidade*/
	public String getLocalidade() {
		return localidade;
	}
	
	/**Configura a propriedade Localidade
	 * @param complemento*/
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	
	/**
	 * Transforma os dados do endereço em uma única String
	 * @return os dados do endereço*/
	@Override
	public String toString() {
		return "BuscarCEP [logradouro=" + logradouro + ", cep=" + cep + ", bairro=" + bairro + ", complemento="
				+ complemento + ", localidade=" + localidade + "]";
	}
	
	
	
	
}
