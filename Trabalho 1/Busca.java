import java.io.RandomAccessFile;

public class Busca {

    public static void main(String... args) throws Exception
    {

        RandomAccessFile file = new RandomAccessFile("cep_ordenado.dat", "r");
        buscaCEP(file, "22250060");
        file.close();
    }

    public static void buscaCEP(RandomAccessFile file, String cep) throws Exception {
        Endereco end = new Endereco();
        long inicio = 0;
        long fim = file.length()/300L;
        long meio = 0; 
        int comparacao;
        int contador = 0;
        boolean encontrado = false;
        while(inicio <= fim && !encontrado) {

            meio = (fim + inicio)/2L;

            file.seek(meio * 300L);
 
            end.leEndereco(file);

            comparacao = end.getCep().compareTo(cep);

            if(comparacao < 0) {
                inicio = meio + 1; 
            } else if (comparacao > 0) {
                fim = meio - 1;
            } else {
                encontrado = !encontrado;
            }
            contador++;
        }

        System.out.println((encontrado) ? 
            "CEP encontrado, ele está na linha " + (meio + 1) + " foram necessárias " + contador + " buscas" : 
            "O CEP " + cep + " não foi encontrado no arquivo"
        );
    }
}