package LogSystem;

/**
 * Created by santiago on 11/21/17.
 */
public class Log {

    public static void Log(String ... aux){
        for(int i=0;i<aux.length;i++){
            System.out.println(aux[i]);
        }
    }
}
