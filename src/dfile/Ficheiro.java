/**
 * Copyright(c) 2018 mosmMy, Inc. Todos os direitos reservados.
 * Criado em Jan 25, 2018, pelas 2:34:28 AM
 * @author Moser José
 * @empresa Mekadir, Inc.
 * @versão 1.0.1
 * @email moser.jose@mekadir.com
 */
package dfile;
import java.io.File;
public class Ficheiro{
    private File inFile;
    public File getInFile() {
        return inFile;
    }
    public void setInFile(File inFile) {
        this.inFile = inFile;
    }
    public Ficheiro(){}
    public Ficheiro(File inFile) {
        this.inFile = inFile;
    }
    
    //Remove um directorio
    public void removerPasta(File inFile){
        if (!inFile.exists()) {
            System.out.println("Localização Inexistente");
            return;
        }
        if (inFile.isDirectory()) {
         File[] files = inFile.listFiles();
         for (File file : files) {
             removerPasta(file);
         }
     }
     inFile.delete();
    }
    
    //Lista todos os ficheiros em um diretorio
    public void listarTodosFicheiros(File caminho){
        try{
            File[] list = caminho.listFiles();
        for (File fl : list) {
            
            if (!fl.isDirectory())
               
                System.out.println(fl);
            else{
                
                System.out.println(String.format("** Pasta: %s **", fl));
                listarTodosFicheiros(fl);
            }
        }
        }
        catch(Exception e){
            
        }
    }
    
    //Remove todos os ficheiros com uma determinda extensão
    public void remFile(File caminho, String extensao){
        try{
            File[] lista = caminho.listFiles();
        for (File arquivos : lista) {
            if (!arquivos.isDirectory()){
               if(arquivos.getName().endsWith(extensao))
                    arquivos.delete();
                }
            else{
                remFile(arquivos, extensao);
            }
        }
        }
        catch(Exception e){}
    }
    
}
