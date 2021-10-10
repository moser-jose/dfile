package dfile;

import java.io.File;

/**
 *
 * @author mosmMy
 */
public class DFile {

    public static void main(String[] args) {
        String s= "D:\\Julho\\Downloads\\BP";
        File f = new File(s);
        Ficheiro g = new Ficheiro(f);
        
        /* g.listarTodosFicheiros(f);
        g.remFile(f,".exe"); */
        
        System.out.println(g.listFileExtension(f,".TS"));
        //g.removerPasta(f);
 
    }
    
}
