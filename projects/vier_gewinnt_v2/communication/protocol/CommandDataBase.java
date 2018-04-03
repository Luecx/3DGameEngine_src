package projects.vier_gewinnt_v2.communication.protocol;

import projects.vier_gewinnt_v2.communication.protocol.utils.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.WeakHashMap;

/**
 * Created by finne on 27.01.2018.
 */
public class CommandDataBase {

    private Map<String, Command> commands = new WeakHashMap<>();

    public CommandDataBase(){

        this.registerCommand(new Command("help", "overview of all functions")
                .registerArgument(new Argument("func", false, "f"))
                .setExecutable(new Executable() {
            @Override
            public void execute(Command c) {
                if(c.getValues("func")!=null) {
                    System.out.println(commands.get(c.getValue("func")).getCommandLayout());
                }else{
                    for(String s:commands.keySet()) {
                        System.out.println(commands.get(s).getInfo());
                    }
                }
            }
        }));
    }

    public void registerCommand(Command c) {
        try{
            commands.put(c.getName().toLowerCase(), c);
        }catch (Exception e) {
        }
    }

    public void removeCommand(String key) {
        commands.remove(key.toLowerCase());
    }

    public void executeCommand(String s) {

        try {
            if (s.startsWith("-")) s = s.substring(1);
            s = StringUtils.transformIntoReadableCommand(s);
            int index = s.indexOf("-");


            String command = index >= 0 ? s.substring(0, index).trim() : s;
            String rest = index >= 0 ? s.substring(index + 1).trim() : "";

            for (String key : commands.keySet()) {
                if (key.equals(command.trim())) {
                    commands.get(key).execute(rest.split("-"));
                    commands.get(key).clearValues();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CommandDataBase dataBase = new CommandDataBase();
        dataBase.registerCommand(
                new Command("test", "just some casual testing")
                        .registerArgument(new Argument("size", "the size of the input",true, "s", "b_size"))
                        .registerArgument(new Argument("ka", "kolumns",true, "k", "b_size"))

                        .setExecutable(new Executable() {
                            @Override
                            public void execute(Command c) {
                                System.out.println("lkadokawodkawo");
                            }
                        }));


        dataBase.registerCommand(
                new Command("print", "print some text")
                    .registerArgument(new Argument("t", "the text to print", true, "text", "txt"))
                    .setExecutable(new Executable() {
                        @Override
                        public void execute(Command c) {
                            System.out.println(Arrays.toString(c.getValues("t")));
                        }
                    })
        );

        Scanner scanner = new Scanner(System.in);

        while(true){
            String command = scanner.nextLine();
            dataBase.executeCommand(command);
        }

    }

}
