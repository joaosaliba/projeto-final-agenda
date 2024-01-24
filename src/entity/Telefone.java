package entity;

import java.util.List;
import java.util.Scanner;

public class Telefone {
    private static long contadorIds = 1;

    private long id;
    private String ddd;
    private Long numero;

    public Telefone() {
        this.id = contadorIds++;
    }

    public Telefone(long id) {
        this.id = id;
    }


    public Telefone(long id, String ddd, Long numero) {
        this.id = id;
        this.ddd = ddd;
        this.numero = numero;
        atualizarContadorIds(id);
    }

    public long getId() {
        return id;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    private static void atualizarContadorIds(long id) {
        contadorIds = Math.max(contadorIds, id + 1);
    }

    public Telefone adicionarTelefoneNovo(long telefoneId) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o DDD do novo telefone: ");
        String novoDdd = scanner.nextLine();
        System.out.print("Digite o número do novo telefone: ");
        Long novoNumero = Long.parseLong(scanner.nextLine());

        Telefone novoTelefone = new Telefone(telefoneId, novoDdd, novoNumero);
        return novoTelefone;
    }

    public void editarTelefone(List<Telefone> telefonesAtuais, long telefoneId) {
        Scanner sc = new Scanner(System.in);
        Telefone telefoneExistente = obterTelefonePorId(telefonesAtuais, telefoneId);
        if (telefoneExistente != null) {
            System.out.print("Digite o novo DDD do telefone: ");
            telefoneExistente.setDdd(sc.nextLine());

            System.out.print("Digite o novo número do telefone: ");
            telefoneExistente.setNumero(Long.parseLong(sc.nextLine()));

            System.out.println("Telefone editado com sucesso.");
        } else {
            System.out.println("Telefone não encontrado.");
        }
    }

    public Telefone obterTelefonePorId(List<Telefone> telefones, long id) {
        return telefones.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public void exibirTelefones(List<Telefone> telefones) {
        if (telefones.isEmpty()) {
            System.out.println("Nenhum telefone cadastrado.");
        } else {
            for (Telefone telefone : telefones) {
                System.out.println("ID: " + telefone.getId() + " | DDD: " + telefone.getDdd() + " | Número: " + telefone.getNumero());
            }
        }
    }

    public void exibirTelefonesPorContatoID(List<Contato> contatos) {
        System.out.print("Digite o ID do contato a mostrar telefones");
        Contato contato = new Contato();
        Scanner sc = new Scanner(System.in);
        int contatoId = sc.nextInt();
        contato = contato.obterContatoPorId(contatoId, contatos);
        exibirTelefones(contato.getTelefones());

    }

}
