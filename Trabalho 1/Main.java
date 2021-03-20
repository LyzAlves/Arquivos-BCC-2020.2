import java.util.*;
import java.io.RandomAccessFile;

class ComparaCEP implements Comparator<Endereco> {

    @Override
    public int compare(Endereco end1, Endereco end2) {
        return end1.getCep().compareTo(end2.getCep());
    }
}

public class Main {

	public static void main(String... args) throws Exception {

		ArrayList<Endereco> listaEnderecos = new ArrayList<Endereco>();

        RandomAccessFile file1 = new RandomAccessFile("cep.dat", "r");

		while(file1.getFilePointer() < file1.length()) {
			Endereco e = new Endereco();
			e.leEndereco(file1);
			listaEnderecos.add(e);
		}

        file1.close();
		Collections.sort(listaEnderecos, new ComparaCEP());
        RandomAccessFile file2 = new RandomAccessFile("cep_ordenado.dat", "rw");
		for(Endereco e: listaEnderecos)
           e.escreveEndereco(file2);
	    file2.close();
	}	
}