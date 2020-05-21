package sample;

public class Updater extends Thread
{
    Controller controller;

    Updater (Controller _controller)
    {
        controller = _controller;
    }

    public void run()
    {
        while (true)
        {
            // Update the controller
            controller.update();

            // Do a little sshhhh...
            try
            {
                // Hush little thread..
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
