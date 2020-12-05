package cep;


public class BuscarCEP {
	
	private String logradouro;
	private String cep;
	private String bairro;
	private String complemento;
	private String localidade;

	
	
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getLocalidade() {
		return localidade;
	}
	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}
	@Override
	public String toString() {
		return "BuscarCEP [logradouro=" + logradouro + ", cep=" + cep + ", bairro=" + bairro + ", complemento="
				+ complemento + ", localidade=" + localidade + "]";
	}
	
	
	
	
}
