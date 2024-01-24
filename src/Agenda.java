import entity.Contato;
import entity.Telefone;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//BufferedReader FileReader
//BufferedWriter FileWriter
public class Agenda {
    private static final String ARQUIVO_CONTATOS = "contatos.txt";
    final List<Contato> contatos;

    public Agenda() {
        this.contatos = new ArrayList<>();
        this.carregarContatos();
        this.runMenu();
    }

    public static void main(String[] args) {
        new Agenda();

    }


    private void runMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        Contato contato = new Contato();
        contato.listarContatos(contatos);
        showMenuOptions();
        System.out.print("Digite a opção desejada: ");
        choice = sc.nextInt();

        executAcction(choice);

        runMenu();
    }


    private void showMenuOptions() {
        System.out.println("\n ----------------------------------- \n");
        System.out.println(" 1 - Adicionar Contato");
        System.out.println(" 2 - Remover Contato");
        System.out.println(" 3 - Editar Contato");
        System.out.println(" 4 - Sair");
        System.out.println("--------------------------------------------");
        System.out.println(" 5- Listar Contatos");
        System.out.println(" 6- Listar Telefones Por Contato Id");
        System.out.println("\n ----------------------------------- \n");

    }

    private void executAcction(int choice) {
        Contato contato = new Contato();
        Telefone telefone = new Telefone();
        salvarContatos();

        switch (choice) {
            case 1 -> contato.addContact(contatos);
            case 2 -> contato.removeContact(contatos);
            case 3 -> contato.editContact(contatos);
            case 4 -> {
                System.out.println("Até mais.");
                Scanner sc = new Scanner(System.in);
                sc.close();
                System.exit(0);
            }
            case 5-> contato.listarContatos(contatos);
            case 6-> {
                telefone.exibirTelefonesPorContatoID(contatos);
            }
            default -> System.out.println("Opção invalida. Tente novamente!");

        }
    }


    private void carregarContatos() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_CONTATOS))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dadosContato = linha.split(";");

                long id = Long.parseLong(dadosContato[0]);
                String nome = dadosContato[1];
                String sobreNome = dadosContato[2];

                List<Telefone> telefones = new ArrayList<>();

                if (dadosContato.length > 3 && !dadosContato[3].isEmpty()) {
                    String[] dadosTelefones = dadosContato[3].split(",");

                    for (String dadosTelefone : dadosTelefones) {
                        String[] dados = dadosTelefone.split(":");
                        long telefoneId = Long.parseLong(dados[0]);
                        String ddd = dados[1];
                        long numero = Long.parseLong(dados[2]);

                        Telefone telefone = new Telefone(telefoneId, ddd, numero);
                        telefones.add(telefone);
                    }
                }

                Contato contato = new Contato(id, nome, sobreNome, telefones);
                contatos.add(contato);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void salvarContatos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_CONTATOS))) {
            for (Contato contato : contatos) {
                bw.write(contato.getId() + ";" + contato.getNome() + ";" + contato.getSobreNome() + ";");

                List<Telefone> telefones = contato.getTelefones();
                for (Telefone telefone : telefones) {
                    bw.write(telefone.getId() + ":" + telefone.getDdd() + ":" + telefone.getNumero() + ",");
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
