import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.Charset;

public class Endereco {
	
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String sigla;
	private String cep;

	public void leEndereco(DataInput datain) throws IOException
	{
		byte logradouro[] = new byte[72];
		byte bairro[] = new byte[72];
		byte cidade[] = new byte[72];
		byte estado[] = new byte[72];
		byte sigla[] = new byte[2];
		byte cep[] = new byte[8];

		datain.readFully(logradouro);
		datain.readFully(bairro);
		datain.readFully(cidade);
		datain.readFully(estado);
		datain.readFully(sigla);
		datain.readFully(cep);

        datain.readByte(); 
		datain.readByte(); 

		Charset enc = Charset.forName("ISO-8859-1");

		this.setLogradouro(new String(logradouro,enc));
		this.setBairro(new String(bairro,enc));
		this.setCidade(new String(cidade,enc));
		this.setEstado(new String(estado,enc));
		this.setSigla(new String(sigla,enc));
		this.setCep(new String(cep,enc));
	}


	public void escreveEndereco(DataOutput dataout) throws IOException
	{		

        Charset enc = Charset.forName("ISO-8859-1");
 
		dataout.write(this.logradouro.getBytes(enc));
		dataout.write(this.bairro.getBytes(enc));
		dataout.write(this.cidade.getBytes(enc));
		dataout.write(this.estado.getBytes(enc));
		dataout.write(this.sigla.getBytes(enc));
		dataout.write(this.cep.getBytes(enc));
		dataout.write(" \n".getBytes(enc));
	}
		
	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	public String getBairro() {
		return bairro;
	}
			
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getEstado() {
		return estado;
    }

    public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getCep() {
		return cep;
	}
	
	public void setCep(String cep) {
		this.cep = cep;
	}
}