package client.controller;

import exceptions.InvalidInputException;
import exceptions.TooMuchLoginAttemptsException;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public abstract class LoginGuard {
    private static Long firstAttempt;
    private static Long lastAttempt;
    private static int counter;
    private static boolean locked;
    private static int timeConstant = 60;
    private static int remainedTime;
    static {
        counter = 0;
        locked = false;
    }

    public static void login(String username, HashMap<String, String> queries) throws IOException, InvalidInputException, TooMuchLoginAttemptsException {
        if (!locked) {
            try {
                RequestHandler.post("/accounts/account/", username, queries, false, null);
            } catch (ResourceException e) {
                throw new InvalidInputException(RequestHandler.getClientResource().getResponseEntity().getText());
            }
            finally {
                counter++;
                if (counter == 1)
                    firstAttempt = new Date().getTime();
                else if (counter == 5) {
                    lastAttempt = new Date().getTime();
                    checkThreat();
                }
            }
        }
        else
            throw new TooMuchLoginAttemptsException("You can try again in " + remainedTime + " seconds");
    }

    private static void checkThreat() {
        if ((lastAttempt - firstAttempt) < 10000)
        {
            locked = true;
            startTimer();
        }
        else {
            counter = 0;
            firstAttempt = new Date().getTime();
        }
    }

    private static void startTimer() {
        remainedTime = timeConstant;
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < timeConstant; i++) {
                    remainedTime -= 1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                locked = false;
                counter = 0;
                if (timeConstant < 480)
                    timeConstant*=2;
            }
        }.start();
    }

}
