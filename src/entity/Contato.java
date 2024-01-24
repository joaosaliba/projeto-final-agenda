package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Contato {
    private static long contadorIds = 1;

    private Long id;
    private String nome;
    private String sobreNome;
    private List<Telefone> telefones;

    public Contato() {
    }

    public Contato(Long id, String nome, String sobreNome, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
        atualizarContadorIds(id);
    }

    public Contato(String nome, String sobreNome, List<Telefone> telefones) {
        this.id = contadorIds++;
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.telefones = telefones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    private static void atualizarContadorIds(long id) {
        contadorIds = Math.max(contadorIds, id + 1);
    }
    public void addContact(List<Contato> contatos) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o nome do contato: ");
        String nome = sc.next();
        System.out.print("\nDigite o sobrenome do contato: ");
        String sobreNome = sc.next();

        List<Telefone> telefones = new ArrayList<>();
        int option = -1;


        do {
            System.out.print("Digite o ddd do contato: ");
            String ddd = sc.next();
            System.out.print("\nDigite o numero do contato: ");
            Long numero = Long.parseLong(sc.next());

            Telefone telefone = new Telefone();
            telefone.setDdd(ddd);
            telefone.setNumero(numero);


            telefones.add(telefone);
            System.out.print("\nDeseja adicioanr outro telefone?( 0 - Não, 1-Sim ) ");
            option = Integer.parseInt(sc.next());
        } while (option != 0);

        Contato novoContato = new Contato(nome, sobreNome, telefones);

        if (!contatoExiste(novoContato, contatos) && !telefoneDuplicado(novoContato, contatos)) {
            contatos.add(novoContato);
            System.out.println("Contato adicionado com sucesso!");
        } else {
            System.out.println("Já existe um contato com esse nome ou telefone. Operação cancelada.");
        }

    }

    public void listarContatos(List<Contato> contatos) {
        System.out.println("\n ----------------------------------- \n");

        System.out.println("##################");
        System.out.println("##### AGENDA #####");
        System.out.println(">>>> Contatos <<<<");
        System.out.println("Id  | Nome");

        for (Contato contato : contatos) {
            System.out.println(contato.getId() + " | " + contato.getNome());
        }
        System.out.println("\n ----------------------------------- \n");

    }

    public void editContact(List<Contato> contatos) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o id do contato que deseja editar: ");
        long id = scanner.nextLong();

        Contato contato = obterContatoPorId(id, contatos);

        if (contato != null) {
            System.out.println("Contato encontrado. Informações atuais:");
            exibirDetalhesContato(contato);
            System.out.print("Digite o novo nome do contato: ");
            contato.setNome(scanner.nextLine());
            System.out.print("Digite o novo sobrenome do contato: ");
            contato.setSobreNome(scanner.nextLine());
            int opcao;
            do {

                System.out.print("\nDeseja editar os telefones? (1 - Sim, 0 - Não): ");
                opcao = scanner.nextInt();

                if (opcao == 1) {
                    List<Telefone> telefonesAtuais = contato.getTelefones();
                    Telefone telefone = new Telefone(0);

                    telefone.exibirTelefones(telefonesAtuais);
                    System.out.print("Digite o ID do telefone que deseja editar (ou 0 para adicionar novo): ");
                    long telefoneId = scanner.nextLong();

                    if (telefoneId == 0) {
                        Telefone novoTelefone = telefone.adicionarTelefoneNovo(telefoneId);
                        telefonesAtuais.add(novoTelefone);
                    } else {
                        telefone.editarTelefone(telefonesAtuais, telefoneId);
                    }

                    contato.setTelefones(telefonesAtuais);
                    System.out.println("Telefones editados com sucesso.");
                }
            } while (opcao != 0);


            System.out.println("Contato editado com sucesso!");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }


    public void removeContact(List<Contato> contatos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o id do contato que deseja remover:");
        long id = scanner.nextLong();

        contatos.removeIf(contato -> contato.getId() == id);
        System.out.println("Contato removido com sucesso!");

    }



    private boolean contatoExiste(Contato contato, List<Contato> contatos) {
        return contatos.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(contato.getNome()));
    }

    private boolean telefoneDuplicado(Contato contato, List<Contato> contatos) {
        for (Contato c : contatos) {
            for (Telefone telefone : contato.getTelefones()) {
                if (c.getTelefones().stream().anyMatch(t -> t.getNumero().equals(telefone.getNumero()))) {
                    return true;
                }
            }
        }
        return false;
    }
    private void exibirDetalhesContato(Contato contato) {
        System.out.println("ID: " + contato.getId());
        System.out.println("Nome: " + contato.getNome());
        System.out.println("Sobrenome: " + contato.getSobreNome());
    }

    public Contato obterContatoPorId(long id, List<Contato> contatos) {
        return contatos.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

}
