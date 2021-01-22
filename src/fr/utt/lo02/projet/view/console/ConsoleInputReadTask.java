package fr.utt.lo02.projet.view.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

/**
 * A console input read task is a console reader that can be interrupted.
 * <p>
 * A ConsoleInputReadTask have essentially two states (1) reading, (2) interrupted
 * This object is used to read console input every TIME_QUANTUM. During the quantum, the thread can be interrupt.
 * This will lead to a null value return by the read methods.
 *
 * @see Callable
 */
public class ConsoleInputReadTask implements Callable<Integer>
{

    /**
     * Minimum and maximum default value when they aren't needed
     */
    public static final int NO_BOUNDS_VALUE = -1;

    /**
     * Quantum time between two read in the console
     */
    public static final int TIME_QUANTUM = 200;

    /**
     * BufferedReader used to read the System.in
     */
    private BufferedReader bufferedReader;

    /**
     * Minimum value for an Integer console read
     * <p>
     * Value must be superior to 0
     */
    private int min;

    /**
     * Minimum value for an Integer console read
     * <p>
     * Value must be superior to 0
     */
    private int max;

    /**
     * Prompt message which tell the user what he should input
     */
    private String askMessage;

    /**
     * Creates a ConsoleInputReadTask setting the bufferedReader to the System.in
     */
    public ConsoleInputReadTask()
    {
        bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));
    }

    /**
     * Prints the prompt and returns an Integer between min and max
     * <p>
     * The Integer will be null if the read is interrupted
     *
     * @param msg the message to prompt
     * @param min the minimum value of the integer
     * @param max the maximum value of the integer
     * @return the read Integer
     * @throws IOException If an I/O error occurs
     */
    public Integer getInt(String msg, int min, int max) throws IOException
    {

        this.min = min;
        this.max = max;
        this.askMessage = msg;
        return call();
    }

    /**
     * Prints the prompt and returns the read Integer
     * <p>
     * The Integer will be null if the read is interrupted
     *
     * @param msg the message to prompt
     * @return the read Integer
     * @throws IOException If an I/O error occurs
     */
    public Integer getInt(String msg) throws IOException
    {

        this.min = NO_BOUNDS_VALUE;
        this.max = NO_BOUNDS_VALUE;
        this.askMessage = msg;

        return call();
    }

    /**
     * Implementation of callable method, reads an integer in the console and returns null if the reading is interrupted
     *
     * This method does busy waiting, it basically sleep for QUANTUM_TIME and then read an Integer from the console
     * If the number is not a valid number, the method keep asking for a valid number
     *
     * @return the read Integer or null if the reading has been interrupted
     * @throws IOException If an I/O error occurs
     */
    @Override
    public Integer call() throws IOException
    {

        bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));

        String input;
        do
        {
            System.out.println(askMessage);
            try
            {
                while (!bufferedReader.ready())
                {
                    Thread.sleep(TIME_QUANTUM);
                }
                input = bufferedReader.readLine();
                try
                {
                    int integer = Integer.parseInt(input);

                    if (min != NO_BOUNDS_VALUE || max != NO_BOUNDS_VALUE)
                    {
                        if (integer < min || integer > max)
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
                    }
                } catch (NumberFormatException e)
                {
                    System.err.println("please enter valid number...");
                    input = "";
                }


            } catch (InterruptedException e)
            {
                return null;
            }
        } while ("".equals(input));

        return Integer.valueOf(input);
    }
}
