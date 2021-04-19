#include <stdio.h>
#include <string.h>
#include <stdlib.h>

typedef struct _End End;

struct _End
{
	char logradouro[72];
	char bairro[72];
	char cidade[72];
	char uf[72];
	char sigla[2];
	char cep[8];
	char lixo[2];
};

int compara(const void* c1, const void* c2){
    
    return strncmp(((End*)c1)->cep,((End*)c2)->cep,8);

}

int main(int argc, char**argv){

    FILE *f, *saida;
	End *e;
	long posicao, qtd, qtd2, parte;


	f = fopen("cep.dat","r");

	fseek(f,0,SEEK_END);
	posicao = ftell(f);
	qtd = posicao/sizeof(End);
	parte = (qtd/8);

    int i = 1;
    rewind(f);

    printf("______________________[DIVIDIR E ORDENAR]______________________\n");

    while(i++ < 17){
        
        printf("Dividindo...\t");

        qtd2 = (i < 17)?parte:(qtd - (parte*15));

        e = (End*) malloc(qtd2*sizeof(End));
        
        if(!fread(e,sizeof(End),qtd2,f) == qtd2){
            printf("Erro na leitura do arquivo!!!");
        }

	    qsort(e,qtd2,sizeof(End),compara);
        printf("Ordenando particao %d\n",i-1);
        char fileName[200];
        sprintf(fileName,"cep_%d.dat",i-1);
        saida = fopen(fileName,"w");
	    printf("Gerando arquivo %s...\n",fileName);
        fwrite(e,sizeof(End),qtd2,saida);
	    
        fclose(saida); 
	    free(e);
        
    }
    i--;	
    fclose(f);        

    
    printf("______________________[CONCATENACAO]______________________\n");

    FILE *A, *B, *C;
    int j = 1;
    End ea1,ea2;

    while(i < 32){
        
        char fileName1[200];
        char fileName2[200];
        char fileName3[200];

        sprintf(fileName1,"cep_%d.dat",j++);
        printf("Concatenando: %s\t",fileName1);
        sprintf(fileName2,"cep_%d.dat",j++);
        printf("%s...\n",fileName2);
        sprintf(fileName3,"cep_%d.dat",i++);
        printf("Gerando: %s...\n\n",fileName3);

        A = fopen(fileName1,"r");
        B = fopen(fileName2,"r");
        C = fopen(fileName3,"w");

        
        fread(&ea1,sizeof(End),1,A);
	    fread(&ea2,sizeof(End),1,A);

        while(!feof(A) && !feof(B)){
		
            if(compara(&ea1,&ea2) < 0){

                fwrite(&ea1,sizeof(End),1,C);
                fread(&ea1,sizeof(End),1,A);

            }
            else{

                fwrite(&ea2,sizeof(End),1,C);
                fread(&ea2,sizeof(End),1,B);

            }
	    }

        while(!feof(A)){

            fwrite(&ea1,sizeof(End),1,C);
            fread(&ea1,sizeof(End),1,A);		

        }
        while(!feof(B)){

            fwrite(&ea2,sizeof(End),1,C);
            fread(&ea2,sizeof(End),1,B);		

        }

        fclose(A);
	    fclose(B);
	    fclose(C);

    }

	return 0;
}