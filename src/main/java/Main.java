import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    //Sera utilizado para mudar a cor do print no console para amarelo
    public static final String ANSI_YELLOW = "\u001B[33m";

    //Sera utilizado para retornar a configuaração de cor para o padrão após imprimir colorido
    public static final String ANSI_RESET = "\u001B[0m";

    //Definindo localização para formatar moeda para ###.###,##
    public static final NumberFormat dinheiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {

        //3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        Funcionario[] funcionarios = {
            new Funcionario("Maria", LocalDate.of(2000,10,18), new BigDecimal(2009.44), "Operador"),
            new Funcionario("João", LocalDate.of(1990,5,12), new BigDecimal(2284.38), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961,5,2), new BigDecimal(9836.14), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988,10,14), new BigDecimal(19119.88), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995,1,5), new BigDecimal(2234.68), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999,11,19), new BigDecimal(1582.72), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993,3,31), new BigDecimal(4071.84), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994,7,8), new BigDecimal(3017.45), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003,5,24), new BigDecimal(1606.85), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996,9, 2), new BigDecimal(2799.93), "Gerente"),
        };
        List<Funcionario> listaFuncionarios = new ArrayList<>(Arrays.stream(funcionarios).toList());


        //3.2 – Remover o funcionário “João” da lista.
        removerFuncionario(listaFuncionarios, "João");


        /*3.3 – Imprimir todos os funcionários com todas suas informações, sendo que:
        • informação de data deve ser exibido no formato dd/mm/aaaa;
        • informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.*/
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO LISTA DE FUNCIONÁRIOS<<<<<<<<<" + ANSI_RESET);

        imprimirFuncionarios(listaFuncionarios);


        //3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>ALTERAÇÃO NO SALÁRIOS DOS FUNCIONÁRIOS<<<<<<<<<" + ANSI_RESET);

        aumentarSalarioFuncionarios(listaFuncionarios, 10);
        imprimirFuncionarios(listaFuncionarios);


        //3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : listaFuncionarios) {
            List<Funcionario> funcionariosInseridos = funcionariosPorFuncao.getOrDefault(funcionario.getFuncao(), new ArrayList<>());
            funcionariosInseridos.add(funcionario);
            funcionariosPorFuncao.put(funcionario.getFuncao(), funcionariosInseridos);
        }


        //3.6 – Imprimir os funcionários, agrupados por função.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO FUNCIONÁRIOS POR FUNÇÃO<<<<<<<<<" + ANSI_RESET);

        for (String funcao : funcionariosPorFuncao.keySet()) {
            imprimirFuncionarios(funcionariosPorFuncao.get(funcao));
        }


        //3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO ANIVERSARIANTES DE OUTUBRO E DEZEMBRO<<<<<<<<<" + ANSI_RESET);

        imprimirFuncionarios(listaFuncionarios.stream().filter(funcionario ->
                        funcionario.getDataNascimento().getMonth().equals(Month.OCTOBER)
                        || funcionario.getDataNascimento().getMonth().equals(Month.DECEMBER))
                        .toList());


        //3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO FUNCIONÁRIOS MAIORES DE IDADE<<<<<<<<<" + ANSI_RESET);

        for (Funcionario funcionario: listaFuncionarios) {
            if(funcionario.getDataNascimento().isBefore(LocalDate.now().minus(18, ChronoUnit.YEARS))){
                System.out.println(funcionario.getNome() + " " + Period.between(funcionario.getDataNascimento() , LocalDate.now()).getYears() + " Anos");
            }
        }


        //3.10 – Imprimir a lista de funcionários por ordem alfabética.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO FUNCIONÁRIOS EM ORDEM ALFABÉTICA<<<<<<<<<" + ANSI_RESET);

        Collections.sort(listaFuncionarios);
        imprimirFuncionarios(listaFuncionarios);


        //3.11 – Imprimir o total dos salários dos funcionários.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO TOTAL DOS SALÁRIOS<<<<<<<<<" + ANSI_RESET);

        BigDecimal somaSalarios = listaFuncionarios.stream()
                .map(funcionario -> funcionario.getSalario())
                .reduce(BigDecimal.ZERO, (subtotal, funcAtual) -> subtotal.add(funcAtual));
        System.out.println(dinheiro.format(somaSalarios));


        //3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        System.out.println(ANSI_YELLOW + ">>>>>>>>>IMPRIMINDO QUANTIDADE DE SALÁRIOS MÍNIMOS<<<<<<<<<" + ANSI_RESET);

        for (Funcionario funcionario:listaFuncionarios) {
            System.out.println(funcionario.getNome() + " " + funcionario.getSalario().divide(BigDecimal.valueOf(1212), 1, RoundingMode.HALF_UP));
        }
    }

    static void aumentarSalarioFuncionarios(List<Funcionario> listaFuncionarios, double percentualAumento){
        for (Funcionario funcionario: listaFuncionarios) {
            funcionario.setSalario(funcionario.getSalario().multiply(BigDecimal.valueOf(1 + (percentualAumento/100))));
        }
    }


    static void removerFuncionario(List<Funcionario> listaFuncionarios, String nome){
        listaFuncionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }


    static void imprimirFuncionarios(List<Funcionario> listaFuncionarios){
        System.out.println("Nome\t Data Nascimento \t Salário \t\t Função");
        System.out.println("___________________________________________________________");
        listaFuncionarios.forEach(funcionario -> {
            System.out.printf("%s\t %td/%tm/%tY\t\t\t %s\t %s%n",funcionario.getNome(),
                    funcionario.getDataNascimento(), funcionario.getDataNascimento(), funcionario.getDataNascimento(),
                    dinheiro.format(funcionario.getSalario()), funcionario.getFuncao());}
        );
        System.out.println("___________________________________________________________\n");
    }
}
