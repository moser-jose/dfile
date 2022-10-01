package dfile;

import java.io.File;

/**
 *
 * @author mosmMy
 */
public class DFile {

    public static void main(String[] args) {
        String s= "E:\\BG";
        File f = new File(s);
        Ficheiro g = new Ficheiro(f);

        String[] a={".nfo",".txt",".exe"};
        
        /* g.listarTodosFicheiros(f);
        g.remFile(f,".exe"); */
        System.out.println(g.listFileExtension(f,".nfo"));
        //g.remFile(f,".nfo"); 
        g.remFile(f,a);
        
        
        //g.removerPasta(f);
 
    }
    
}
