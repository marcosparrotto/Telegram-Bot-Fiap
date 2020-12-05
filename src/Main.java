import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.vdurmont.emoji.EmojiParser;

import app.UI;
import cep.BuscarCEP;
import cep.service.BuscarCEPService;
import criptomoeda.CriptoMoeda;
import criptomoeda.service.CriptoMoedaService;
import xadrez.Partida;
import xadrez.PeçaXadrez;
import xadrez.PosiçãoXadrez;
import xadrez.XadrezException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

//https://web.telegram.org/#/im?p=@fiap_38SCJ_bot

public class Main {

	public static void main(String[] args) throws Exception {

		// variaveis do xadrez
		Partida partida = new Partida();
		List<PeçaXadrez> capturadas = new ArrayList<>();
		PosiçãoXadrez origem = null;
		PosiçãoXadrez destino;

		// Menu de opções do BOT
		int menu = 0;
		// 0 - menu
		// 1 - xadrez
		// 2 - Consulta CEP
		// 3 - Consulta Criptomoedas

		// RespostaEsperada Xadrez
		int estadoEsperado = 0;
		// 1 - origem
		// 2 - destino
		// 3 - pomoção peão
		// 4 - Consulta CEP
		// 5 - Consulta Criptomoedas

		// Criação do objeto bot com as informações de acesso
		TelegramBot bot = TelegramBotAdapter.build(Config.Token);

		// objeto responsável por receber as mensagens
		GetUpdatesResponse updatesResponse;
		// objeto responsável por gerenciar o envio de respostas
		SendResponse sendResponse;
		// objeto responsável por gerenciar o envio de ações do chat
		BaseResponse baseResponse;

		// controle de off-set, isto é, a partir deste ID será lido as mensagens
		// pendentes na fila
		int m = 0;

		// loop infinito pode ser alterado por algum timer de intervalo curto
		while (true) {

			// executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial)
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// lista de mensagens
			List<Update> updates = updatesResponse.updates();

			if (updates != null) {
				// análise de cada ação da mensagem
				for (Update update : updates) {

					// atualização do off-set
					m = update.updateId() + 1;
					String answer = "Erro!";
					String mensagem = update.message().text();

					try {
					switch (menu) {
					case 0: // Menu
						if (mensagem.equals("/startXadrez")) {
							menu = 1;
							partida = new Partida();
							capturadas = new ArrayList<>();
							answer = UI.printPartida(partida, capturadas);
							answer = EmojiParser.parseToUnicode("Vamos começar o xadrez! \n\n" + answer
									+ "\n\nDigite a posição da peça que você gostaria de mover");
							estadoEsperado = 1;
						}else if(mensagem.equals("/startCEP")) {	
							menu = 2;
							answer = EmojiParser.parseToUnicode("\n\n" + "Informe o CEP");
							System.out.println("CEP" + mensagem);
							estadoEsperado = 4;
		
						}else if(mensagem.equals("/startCriptoMoedas")){
							menu = 3;
							answer = EmojiParser.parseToUnicode("\n\n" + "Informe a sigla da Criptomoeda (Exemplo BTC)");
							estadoEsperado = 5;
						}else {
						
							answer = EmojiParser.parseToUnicode("Escolha entre: \n -/startXadrez \n -/startCEP \n -/startCriptoMoedas");
						}
						break;
					case 1: // xadrez iniciado
						switch (estadoEsperado) {
						case 1: // Esperando posição origem
							try {
								char coluna = mensagem.toLowerCase().charAt(0);
								int linha = Integer.parseInt(mensagem.substring(1));
								origem = new PosiçãoXadrez(coluna, linha);
								boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
								answer = UI.printTabuleiro(partida.getPeças(), possiveisMovimentos);
								answer = EmojiParser.parseToUnicode(
										answer + "\n\n" + "Digite a posição de destino");
								estadoEsperado = 2;
							} catch (XadrezException e) {
								answer = e.getMessage()+ "\nTente novamente...";
							} catch (InputMismatchException e) {
								answer = e.getMessage() + "\nTente novamente...";
							}
							catch (RuntimeException e) {
								answer = "Erro lendo posicao no Xadrez, valores validos a-h 1-8"+ "\nTente novamente...";
							}
							break;
						case 2: // Esperando posição destino
							try {
								char coluna = mensagem.toLowerCase().charAt(0);
								int linha = Integer.parseInt(mensagem.substring(1));
								destino = new PosiçãoXadrez(coluna, linha);
								PeçaXadrez peçaCapturada = partida.executarMovimentoXadrez(origem, destino);
								if (peçaCapturada != null) {
									capturadas.add(peçaCapturada);
								}

								if (partida.getpromovida() != null) {
									estadoEsperado = 3;
									answer = "Escolha entre (T/C/B/D) para promover";
								} else {
									answer = UI.printPartida(partida, capturadas);
									if(partida.getCheckMate()) {
										menu = 0;
										estadoEsperado = 0;
									} else {
										answer = answer + "\nDigite posição da peça que você gostaria de mover";
										estadoEsperado = 1;
									}
									answer = EmojiParser.parseToUnicode(answer);
								}
							}  catch (XadrezException e) {
								answer = e.getMessage()+ "\nTente novamente...";
							} catch (InputMismatchException e) {
								answer = e.getMessage()+ "\nTente novamente...";
							}
							catch (RuntimeException e) {
								answer = "Erro lendo posicao no Xadrez, valores validos a-h 1-8"+ "\nTente novamente...";
							}
							break;
						case 3: // Esperando peça promovida
							String type = mensagem.toUpperCase();
							if(!type.equals("T") && !type.equals("C") && !type.equals("B") && !type.equals("D")){
								answer = EmojiParser.parseToUnicode("Tipo não encontrado! \nEscolha entre (T/C/B/D) para promover");
							} else {
								partida.alterarPeçaPromovida(type);
								answer = UI.printPartida(partida, capturadas);
								if(partida.getCheckMate()) {
									menu = 0;
									estadoEsperado = 0;
								} else {
									answer = answer + "\nDigite posição da peça que você gostaria de mover";
									estadoEsperado = 1;
								}
								answer = EmojiParser.parseToUnicode(answer);
							}
							break;
						}
						break;
					case 2:
						switch (estadoEsperado) {
						case 4:
							try {
								String cep = mensagem;
								BuscarCEP buscarCep = BuscarCEPService.buscaEnderecoPeloCep(cep);
								answer = EmojiParser.parseToUnicode(buscarCep.getLogradouro() + "\n" + 
										buscarCep.getBairro() + "\n" + 
										buscarCep.getComplemento() + "\n" + 
										buscarCep.getLocalidade()) + "\n\nEscolha entre: \n -/startXadrez \n -/startCEP \n -/startCriptoMoedas";
								menu=0;
								estadoEsperado = 0;
							}catch (Exception e) {
								answer = e.getMessage()+ "\nCEP Inválido...";
							}break;
						}
						break;
					case 3:
						switch (estadoEsperado) {
						case 5:
							try {
								String criptoMoedaMessage = mensagem;
								CriptoMoeda criptoMoeda = CriptoMoedaService.buscaCriptomoeda(criptoMoedaMessage);
								answer = EmojiParser.parseToUnicode("O valor atual da " 
																	+ mensagem 
																	+ " é de " + NumberFormat.getCurrencyInstance().format(criptoMoeda.getLast()))
																	+ "\n\nEscolha entre: \n -/startXadrez \n -/startCEP \n -/startCriptoMoedas" ;
								menu = 0;
								estadoEsperado = 0;
							}catch(Exception e) {
								answer = e.getMessage() + "\nSigla Inválida ...";
							}
							break;
						}
						break;
					}
					} catch (XadrezException e) {
						answer = e.getMessage();
					} catch (InputMismatchException e) {
						answer = e.getMessage();
					}finally {
						System.out.println("Recebendo mensagem: " + mensagem);

						// envio de "Escrevendo" antes de enviar a resposta
						baseResponse = bot
								.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						// verificação de ação de chat foi enviada com sucesso
						System.out.println("Resposta de Chat Action Enviada: " + baseResponse.isOk());

						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), answer));
						// verificação de mensagem enviada com sucesso
						System.out.println("Mensagem Enviada: " + sendResponse.isOk());	
					}
				}
			}

		}

	}
}