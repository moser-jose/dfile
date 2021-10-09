package dfile;

import java.io.File;

/**
 *
 * @author mosmMy
 */
public class DFile {

    public static void main(String[] args) {
        String s= "C:\\Users\\Mosmmy\\Downloads\\Documents";
        File f = new File(s);
        Ficheiro g = new Ficheiro(f);
        
        g.listarTodosFicheiros(f);
        //g.remFile(f,".torrent");
        //g.removerPasta(f);
 
    }
    
}
