package ru.mipt.diht.studens;


import ru.mipt.diht.studens.commands.Commands;
import ru.mipt.diht.studens.exception.HandlerException;
import twitter4j.Twitter;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class PackageParse {
    public static void parse(TwitterProvider twitterPr, String[] args) {
        try {
            ArrayList<String> current = new ArrayList<>();
            for (int i = 0; i < args.length; ++i) {
                current.clear();
                while (i < args.length) {
                    if (!(args[i].contains(";"))) {
                        current.add(args[i]);
                        i++;
                    } else {
                        current.add(args[i].substring(0, args[i].indexOf(";")));
                        break;
                    }
                }
                if (current.size() == 0) {
                    return;
                }
                String[] com = new String[current.size()];
                com = current.toArray(com);
                try {
                    Commands command = Commands.fromString(com);
                    command.execute(twitterPr);
                } catch (NoSuchElementException e) {
                    System.out.println("\033[31m" + e.toString() + "\033[0m");
                }
            }
        } catch (IllegalMonitorStateException e) {
            System.out.println("Goodbye");
            System.exit(0);
        } catch (IllegalArgumentException e) {
            HandlerException.handler("Wrong arguments", e);
        } catch (Exception e) {
            HandlerException.handler( e);
        }
    }
}
