import Entidades.*;
import Enums.*;
import Services.*;

public class Main {
    public static void main(String[] args) {
        //Criar Usuarios e Cadastrar
        UsuarioService usuarios = new UsuarioService();
        CursoService servicoCurso = new CursoService();
        OpotunidadeService op = new OpotunidadeService();
        InscricaoService in = new InscricaoService();

        Usuario test = new Usuario("A","email","Senha", null, true);
        Curso cursoA = new Curso("Comp",1,15,"1");
        Curso cursoB = new Curso("Eng",2,20,"1");

        servicoCurso.adcionarCurso(cursoA);
        servicoCurso.adcionarCurso(cursoB);

        System.out.println(test.toString());

        usuarios.cadastrar(test);

        System.out.println(usuarios.toString());

        //Adcionando mais tipos de usuarios;

        Docente docente_test = new Docente("B", "bEmail", "123", Papel.DOCENTE, true,"1","x");
        usuarios.cadastrar(docente_test);

        usuarios.cadastrar(new DiscenteDiretor("C", "cEmail", "123", Papel.DISCENTE_DIRETOR, true,"1",5,cursoA,null,"Diretor" ));
        Discente testDiscente = new Discente("D", "dEmail", "123", Papel.DISCENTE, true,"2",5,cursoA);
        usuarios.cadastrar(testDiscente);
        servicoCurso.adcionarDiscente(testDiscente, cursoA);

        System.out.println(cursoA.toString());
        System.out.println(cursoB.toString());
        System.out.println(servicoCurso.toString());


        servicoCurso.mudarCurso(testDiscente, cursoB);
        System.out.println(servicoCurso.toString());
        System.out.println("Discentes :" + servicoCurso.listarAlunosPorStatus(cursoB, true));

        //listar usuarios
        System.out.println(usuarios.toString());

        //Oportunidade

        Oportunidade tentativa = new Oportunidade("ABC","cba",statusOportunidade.Pendente,TipoOportunidade.Projeto,Modalidade.Remoto,15,5,null,null,docente_test,docente_test);
        Oportunidade testOportunidade = op.criarOportunidade(tentativa, docente_test);

        System.out.println(testOportunidade.toString());

        //Aluno se inscreve em uma oportunidade
        Inscricao inscricao = in.inscrever(testDiscente,testOportunidade);
        System.out.println(inscricao.toString());






    }
}
