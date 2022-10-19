package data;

import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.JSONObject;

public class Shared {

    private ArrayList<PrintWriter> list = new ArrayList<>();
    private ArrayList<JSONObject> infected_list = new ArrayList<>();

    public void add_out(PrintWriter out){
        this.list.add(out);
    }
    public String add_infected(String classname, String infected){
        int new_infected = Integer.parseInt(infected);
        for (JSONObject js: infected_list) {
            if(js.get("class").toString().equals(classname)){
                int old_infected = Integer.parseInt(String.valueOf(js.get("infected")));
                new_infected = new_infected + old_infected;
                js.remove("infected");
                js.put("infected",Integer.toString(new_infected));
                return classname + " " + Integer.toString(new_infected);
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("class", classname);
        obj.put("infected", Integer.toString(new_infected));
        infected_list.add(obj);
        return classname + " " + Integer.toString(new_infected);
    }
    public String recover_infected(String classname, String recovered){
        int new_recovered = Integer.parseInt(recovered);
        for (JSONObject js: infected_list) {
            if(js.get("class").toString().equals(classname)){
                int old_infected = Integer.parseInt(String.valueOf(js.get("infected")));
                new_recovered = old_infected - new_recovered;
                if(new_recovered < 0){
                    return "This won't work, more recovered students than absolut infected!";
                }
                js.remove("infected");
                js.put("infected",Integer.toString(new_recovered));
                return classname + " " + Integer.toString(new_recovered);
            }
        }
        return "Class not found!";
    }
    public String list(){
        String return_string = "";
        for (JSONObject js:infected_list){
            return_string = return_string+js.get("class")+" "+js.get("infected")+"#";
        }
        return return_string;
    }
    public void message_all(String message){
        for(PrintWriter out:list){
            out.write(message);
            out.flush();
        }
    }
}
