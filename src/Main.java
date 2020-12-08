import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

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
import futebol.CampeonatoBrService;
import xadrez.Partida;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezException;

//https://web.telegram.org/#/im?p=@fiap_38SCJ_bot

public class Main {

	public static void main(String[] args) throws Exception {

		// variaveis do xadrez
		Partida partida = new Partida();
		List<PecaXadrez> capturadas = new ArrayList<>();
		PosicaoXadrez origem = null;
		PosicaoXadrez destino;

		// Menu de op��es do BOT
		int menu = 0;
		// 0 - menu
		// 1 - xadrez
		// 2 - Consulta CEP
		// 3 - Consulta Criptomoedas
		// 4 - Campeoato Brasileiro

		// RespostaEsperada Xadrez
		int estadoEsperado = 0;
		// 1 - origem
		// 2 - destino
		// 3 - pomocao peao
		// 4 - Consulta CEP
		// 5 - Consulta Criptomoedas
		// 50 - Tabela Campeonato
		// 51 - Rodada do Campeonato

		// Criacao do objeto bot com as informacoes de acesso
		TelegramBot bot = TelegramBotAdapter.build(Config.Token);

		// objeto responsavel por receber as mensagens
		GetUpdatesResponse updatesResponse;
		// objeto responsavel por gerenciar o envio de respostas
		SendResponse sendResponse;
		// objeto responsavel por gerenciar o envio de acoes do chat
		BaseResponse baseResponse;

		// controle de off-set, isto e, a partir deste ID sera lido as mensagens
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
				// analise de cada acao da mensagem
				for (Update update : updates) {

					// atualizacaoo do off-set
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
											+ "\n\nDigite a posicao da peca que você gostaria de mover");
									estadoEsperado = 1;
								} else if (mensagem.equals("/startCEP")) {
									menu = 2;
									answer = EmojiParser.parseToUnicode("\n\n" + "Informe o CEP");
									System.out.println("CEP" + mensagem);
									estadoEsperado = 4;

								} else if (mensagem.equals("/startCriptoMoedas")) {
									menu = 3;
									answer = EmojiParser
											.parseToUnicode("\n\n" + "Informe a sigla da Criptomoeda (Exemplo BTC)");
									estadoEsperado = 5;
								} else if (mensagem.equals("/startCampeonatoBrasileiro")) {
									menu = 4;
									answer = EmojiParser.parseToUnicode(
											"Para ver a tabela do Campeonato, digite /tabela \n Para ver uma rodada, digite /rodada");
									estadoEsperado = 50;
								} else {
									answer = EmojiParser.parseToUnicode(
											"Escolha entre: \n -/startXadrez \n -/startCEP \n -/startCriptoMoedas \n -/startCampeonatoBrasileiro");
								}
								break;
							case 1: // xadrez iniciado
								switch (estadoEsperado) {
									case 1: // Esperando posicao origem
										try {
											char coluna = mensagem.toLowerCase().charAt(0);
											int linha = Integer.parseInt(mensagem.substring(1));
											origem = new PosicaoXadrez(coluna, linha);
											boolean[][] possiveisMovimentos = partida.possiveisMovimentos(origem);
											answer = UI.printTabuleiro(partida.getPecas(), possiveisMovimentos);
											answer = EmojiParser
													.parseToUnicode(answer + "\n\n" + "Digite a posicao de destino");
											estadoEsperado = 2;
										} catch (XadrezException e) {
											answer = e.getMessage() + "\nTente novamente...";
										} catch (InputMismatchException e) {
											answer = e.getMessage() + "\nTente novamente...";
										} catch (RuntimeException e) {
											answer = "Erro lendo posicao no Xadrez, valores validos a-h 1-8"
													+ "\nTente novamente...";
										}
										break;
									case 2: // Esperando posicao destino
										try {
											char coluna = mensagem.toLowerCase().charAt(0);
											int linha = Integer.parseInt(mensagem.substring(1));
											destino = new PosicaoXadrez(coluna, linha);
											PecaXadrez pecaCapturada = partida.executarMovimentoXadrez(origem, destino);
											if (pecaCapturada != null) {
												capturadas.add(pecaCapturada);
											}

											if (partida.getpromovida() != null) {
												estadoEsperado = 3;
												answer = "Escolha entre (T/C/B/D) para promover";
											} else {
												answer = UI.printPartida(partida, capturadas);
												if (partida.getCheckMate()) {
													menu = 0;
													estadoEsperado = 0;
												} else {
													answer = answer
															+ "\nDigite posicao da peca que você gostaria de mover";
													estadoEsperado = 1;
												}
												answer = EmojiParser.parseToUnicode(answer);
											}
										} catch (XadrezException e) {
											answer = e.getMessage() + "\nTente novamente...";
										} catch (InputMismatchException e) {
											answer = e.getMessage() + "\nTente novamente...";
										} catch (RuntimeException e) {
											answer = "Erro lendo posicao no Xadrez, valores validos a-h 1-8"
													+ "\nTente novamente...";
										}
										break;
									case 3: // Esperando peca promovida
										String type = mensagem.toUpperCase();
										if (!type.equals("T") && !type.equals("C") && !type.equals("B")
												&& !type.equals("D")) {
											answer = EmojiParser.parseToUnicode(
													"Tipo não encontrado! \nEscolha entre (T/C/B/D) para promover");
										} else {
											partida.alterarPecaPromovida(type);
											answer = UI.printPartida(partida, capturadas);
											if (partida.getCheckMate()) {
												menu = 0;
												estadoEsperado = 0;
											} else {
												answer = answer + "\nDigite posicao da peca que você gostaria de mover";
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
											answer = EmojiParser.parseToUnicode(buscarCep.getLogradouro() + "\n"
													+ buscarCep.getBairro() + "\n" + buscarCep.getComplemento() + "\n"
													+ buscarCep.getLocalidade()) + "\n\nEscolha entre:\n -/startXadrez\n -/startCEP\n -/startCriptoMoedas\n -/startCampeonatoBrasileiro";
											menu = 0;
											estadoEsperado = 0;
										} catch (Exception e) {
											answer = e.getMessage() + "\nCEP Inválido...";
										}
										break;
								}
								break;
							case 3:
								switch (estadoEsperado) {
									case 5:
										try {
											String criptoMoedaMessage = mensagem;
											CriptoMoeda criptoMoeda = CriptoMoedaService
													.buscaCriptomoeda(criptoMoedaMessage);
											answer = EmojiParser.parseToUnicode("O valor atual da " + mensagem
													+ " é de "
													+ NumberFormat.getCurrencyInstance().format(criptoMoeda.getLast()));
											menu = 0;
											estadoEsperado = 0;
										} catch (Exception e) {
											answer = e.getMessage() + "\nSigla Inválida ...";
										}
										break;
								}
								break;
							case 4:
								switch (estadoEsperado) {
									case 50:
										if (mensagem.equalsIgnoreCase("/tabela")) {
											answer = new StringBuilder().append("Tabela do Campeonato Brasileiro")
													.append(System.getProperty("line.separator"))
													.append(new CampeonatoBrService().obtemTabela()).toString();
											menu = 0;
											estadoEsperado = 0;
										} else if (mensagem.equalsIgnoreCase("/rodada")) {
											answer = "Digite a rodada que você quer ver os resultados";
											estadoEsperado = 51;
										}
										break;
									case 51:
										answer = new CampeonatoBrService().obtemRodada(mensagem);
										menu = 0;
										estadoEsperado = 0;
										break;
								}
								break;
						}
					} catch (XadrezException e) {
						answer = e.getMessage();
					} catch (InputMismatchException e) {
						answer = e.getMessage();
					} finally {
						// envio de "Escrevendo" antes de enviar a resposta
						baseResponse = bot
								.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
						// verificacao de acao de chat foi enviada com sucesso
						System.out.println("Resposta de Chat Action Enviada: " + baseResponse.isOk());

						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), answer));
						// verificacao de mensagem enviada com sucesso
						System.out.println("Mensagem Enviada: " + sendResponse.isOk());
					}
				}
			}
		}
	}
}
