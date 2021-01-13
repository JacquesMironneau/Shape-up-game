package fr.utt.lo02.projet.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ConsoleInputReadTask implements Callable<Integer> {


    public static final int NO_BOUNDS_VALUE = -1;
    private BufferedReader br;

    private int min;
    private int max;
    private String askMessage;

    public ConsoleInputReadTask()
    {
        br = new BufferedReader(
                new InputStreamReader(System.in));
    }

    public Integer getInt(String msg, int min, int max) throws IOException
    {

        this.min = min;
        this.max = max;
        this.askMessage = msg;
        return call();
    }

    public Integer getInt(String msg) throws IOException
    {

        this.min = NO_BOUNDS_VALUE;
        this.max = NO_BOUNDS_VALUE;
        this.askMessage = msg;

        return call();
    }

    public Integer call() throws IOException {

        br = new BufferedReader(
                new InputStreamReader(System.in));

        String input;
        do {

            System.out.println(askMessage);

            try {
                while (!br.ready()  /*  ADD SHUTDOWN CHECK HERE */) {
                    Thread.sleep(200);
                }
                input = br.readLine();
                try {
                    int integer = Integer.parseInt(input);

                    if (min == NO_BOUNDS_VALUE && max == NO_BOUNDS_VALUE)
                    {

                    }
                    else if (integer < min || integer > max)
                    {
                        StringBuilder errorMessage = new StringBuilder();
                        errorMessage.append("please enter a number between ")
                                .append(min)
                                .append(" and ")
                                .append(max)
                                .append("...");

                        System.err.println(errorMessage);

                        input = "";
                    }
                } catch (NumberFormatException e)
                {
                    System.err.println("please enter valid number...");
                    input = "";
                }


            } catch (InterruptedException e) {
                // TODO: 1/7/21 remove log message
//                System.out.println("operation cancelled by the other threado");
                return null;
            }
        } while ("".equals(input));
        return Integer.valueOf(input);
    }
}
