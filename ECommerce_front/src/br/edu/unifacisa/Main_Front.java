package br.edu.unifacisa;

import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.crypto.Data;

import br.edu.unifacisa.modelos.Constantes;
import br.edu.unifacisa.services.AdminService;
import br.edu.unifacisa.services.ClientService;
import br.edu.unifacisa.services.SellerService;
import br.edu.unifacisa.services.UserService;

public class Main_Front {

	public static void main(String[] args) throws IOException, InterruptedException {
		UserService userService = new UserService();
		AdminService adminService = new AdminService();
		SellerService sellerService = new SellerService();
		ClientService clientService = new ClientService();
		Scanner sc = new Scanner(System.in);
		int menuL1 = 0;
		int menuL2 = 0;
		int menuL3 = 0;
		int menuL4 = 0;
		int menuL5 = 0;
		int select = 0;
		String token = "";
		
		do {
			if (menuL1 == 0) {
				System.out.print("\n 1. Login\n 2. Cadastro\n99. Sair\n    Escolha uma opção: ");
				menuL1 = Integer.parseInt(sc.nextLine());
			
			} else if (menuL1 == 1) { // Login
				System.out.print("Digite o login: ");
				String login = sc.nextLine();

				System.out.print("Digite o senha: ");
				String senha = sc.nextLine();
				
				token = userService.login(login, senha);
				
				if (!token.equals("Usuário não encontrado") ) {
					String userTyper = userService.getUserType(token);
					System.out.println("\n\nLodado como " + userTyper);
					
					do {
						if (userTyper.equals(Constantes.ADMIN)) { // Logado como administrador
							System.out.println("Categorias:");
							System.out.println(adminService.getCategories());
							
							System.out.print(" 1. Adcionar categoria\n 2. Remover categoria"
									+ "\n 3. Editar categoria\n99. Deslogar\n    Escolha uma opção:");
							menuL2 = Integer.parseInt(sc.nextLine());
							
							if (menuL2 == 1) {   // Adiciona categoria
								System.out.print("Digite o nome da categoria:");
								System.out.println("\n" + adminService.addCategorie(sc.nextLine(),token) + "\n");
							}
							// falta fazer delete e edit
							
						}else if (userTyper.equals(Constantes.SELLER)) {  // Logado como vendedor
							menuL2 = 0;
							do {
							if (menuL2 == 0) { 
								System.out.print("\n 1. Meus produtos\n 2. Minhas vendas"
										+ "\n99. Logoff\n    Escolha uma opção:");
								menuL2 = Integer.parseInt(sc.nextLine());
								
							} else if (menuL2 == 1) { // Meus produtos
								System.out.println("\nMeus Produtos\n");
								System.out.println(sellerService.getListMyProducts(token));// mostra meus produtos
								
								System.out.print(" 1. Adicionar produto\n 2. Remover produto"
										+ "\n99. voltar\n    Escolha uma opção:");
								menuL3 = Integer.parseInt(sc.nextLine());
								
								if (menuL3 == 1) { // Adiciona produto
									System.out.println("\nEscolha uma categoria: ");
									System.out.println(adminService.getCategories());
									int category = Integer.parseInt(sc.nextLine());
									
									System.out.print("Digite o nome: ");
									String name = sc.nextLine();
									
									System.out.print("Digite o preço: ");
									String price = sc.nextLine();
									
									System.out.print("Digite a quantidade: ");
									String quantity = sc.nextLine();
									
									System.out.print("Digite uma descrição: ");
									String description = sc.nextLine();									
									
									System.out.println(sellerService.addProduct(
												name,price,quantity,description,category, token));									
								}								
							} else if (menuL2 == 2) { 
								System.out.println("\n Minhas vendas\n");
								System.out.println(sellerService.getMySales(token));
								menuL3 = Integer.parseInt(sc.nextLine());
							}
							
							
							} while (menuL2 != 99);
							menuL2 = 0;
							menuL1 = 0;
							
						} else if (userTyper.equals(Constantes.CLIENT)) { // logado como cliente
							int selectCat = 0;
							int selectProduct = 0;
							int selectQuantity = 0;
							
							do {								
								if (menuL2 == 0) {// exibe os produtos por categoria
									System.out.println("\nCategorias:\n");
									System.out.println(adminService.getCategories());
									System.out.print("Escolha uma categoria, ou"
													+ "\n 77. Meu carrinho"
													+ "\n 88. Meus pedidos"
													+ "\n 99. Logoff: ");
									menuL2 = Integer.parseInt(sc.nextLine());
									if (menuL2 != 99) {
										if (menuL2 < 77) {
											selectCat = menuL2;
											menuL2 = 1;
										}
									}
								}else if (menuL2 == 1) {// escolhe o produto da categoria
									System.out.println("\nCategoria: " + adminService.getCategory(selectCat) + "\n");
									System.out.println(clientService.listProductsByCategory(selectCat));
									System.out.print("Escolha um produto, ou"
													+ "\n 77. Meu carrinho"
													+ "\n 88. Meus pedidos"
													+ "\n 99. Voltar: ");
									menuL2 = Integer.parseInt(sc.nextLine());
									
									if (menuL2 != 99) {
										if (menuL2 < 77) {
											selectProduct = menuL2;
											menuL2 = 2;
											}
									}else menuL2 = 0;
								}else if (menuL2 == 2) { // pagina do produto
									System.out.println("\nProduto:\n");
									System.out.println("   " + clientService.getProduct(selectProduct ));
									System.out.print("\nEscolha a quantidade , ou"
													+ "\n 77. Meu carrinho"
													+ "\n 88. Meus pedidos"
													+ "\n 99. Voltar: ");
									menuL2 = Integer.parseInt(sc.nextLine()); // seleciona a quantidade
									
									if (menuL2 != 99) {
										if (menuL2 < 77) {
											selectQuantity = menuL2;
											menuL2 = 77;
											System.out.println("\n" + clientService.addItenCart(selectQuantity,token) + "");
											}
									} else menuL2 = 1;
									
								}else if (menuL2 == 77) { // meu carrinho
									System.out.println("\nMeu Carrinho\n");
									String myCart = clientService.getMyCart(token);
									System.out.println(myCart);
									if (!myCart.equals("Vazio"))
										System.out.print("\n  1. Finalizar compra");
									System.out.print("\n 88. Meus pedidos"
													+ "\n 99. Voltar: ");									
									menuL2 = Integer.parseInt(sc.nextLine());
									if (menuL2 != 99) {
										if (menuL2 == 1 && !myCart.equals("Vazio")) {
											menuL2 = 4;
											}
									}else menuL2 = 0;
									
								}else if (menuL2 == 4) { // pagamento
									System.out.print("\n\nDigite o endereço para entrega:");
									String address = sc.nextLine();
									System.out.print("\n\nEscolha a forma de pagamento:"
											+ "\n  1. Boleto "
											+ "\n  2. PIX "
											+ "\n  3. Cartão de crédito"
											+ "\n 99. Voltar: ");
									menuL2 = Integer.parseInt(sc.nextLine());
									
									if (menuL2 != 99) {
										if (menuL2 <= 3 && menuL2 >= 1 && address != "") {
											System.out.println(clientService.confirmPurchase(menuL3,address, token));
											menuL2 = 88;
										} else {
											System.out.println("Dados inválidos");
										}
									}else menuL2 = 77;
									
								}else if (menuL2 == 88) { // pagina meus pedidos
									System.out.println("Meus pedidos\n");
									System.out.println(clientService.getMyRequests(token));
									System.out.print("Digite 99 para voltar:");
									menuL2 = Integer.parseInt(sc.nextLine());
									if (menuL2 == 99)  menuL2 = 0;
								}							
							}while (menuL2 != 99);
						}
						
					}while(menuL2!= 99);
					menuL2 = 0;
					menuL1 = 0;
					
				}else System.out.println("\nUsuário não encontrado");
				
			} else if (menuL1 == 2) {// cadastro usuário
				System.out.print("1. Vendedor\n2. Cliente\n99. Voltar\n    Escolha uma opção:");
				menuL2 = Integer.parseInt(sc.nextLine());

				if (menuL2 == 1 || menuL2 == 2) { // Cadastro
//					System.out.print("Digite o ID: ");
//					String id = sc.nextLine();

					System.out.print("Digite o login: ");
					String login1 = sc.nextLine();
	
					System.out.print("Digite o senha: ");
					String senha1 = sc.nextLine();
					
					System.out.print("Digite o nome: ");
					String name = sc.nextLine();
					
					try {
						String userType = "SELLER";
						if (menuL2 == 2) {
							userType = "CLIENT";
						}
						String retorno  = userService.createUser( login1, senha1, name, userType);
						System.out.println(retorno);
					} catch (Exception e) {
						System.out.println(e.getMessage());
						menuL1 = 0;
					}
				}
				
				
			} else {
				System.out.println("Opção invalida");
			}
			
			
			
		}while (menuL1 != 99);
	}
}
