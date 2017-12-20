package AI.Body;

/**
 * Created by santiago on 12/1/17.
 */
public class BodyUtils {


    public static String createMessage(Object... message){
        StringBuilder builder=new StringBuilder();

        for(int i=0;i<message.length;i++){
            if(message!=null){
                builder.append(message[i]);
            }
        }

        return builder.toString();
    }
}
