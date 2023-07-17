import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario extends Pessoa implements Comparable<Funcionario>{
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        this.salario = salario;
        this.funcao = funcao;
        this.setNome(nome);
        this.setDataNascimento(dataNascimento);
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public int compareTo(Funcionario outroFuncionario) {
        return this.getNome().compareTo(outroFuncionario.getNome());
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "nome=" + this.getNome() +
                ", dataNascimento=" + this.getDataNascimento() +
                ", salario=" + salario +
                ", funcao='" + funcao + '\'' +
                '}';
    }
}
