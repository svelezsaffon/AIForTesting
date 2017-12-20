package AI.Body;

import AI.Actions.Action;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by santiago on 11/30/17.
 */
public class  Todos<Element> {


    private Queue<Element> todos;

    public Todos(){
        this.todos= new LinkedList<Element>();
    }

    public void addAction(Element add){
        this.todos.add(add);
    }


    public Queue<Element> getActions(){
        return this.todos;
    }

    public boolean isEmpty(){
        return this.todos.isEmpty();
    }

    public Element getNextAction(){
        return this.todos.remove();
    }

    public Element viewNextAction(){
        return this.todos.peek();
    }


}
