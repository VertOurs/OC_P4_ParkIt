package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

 public final class App {
     /**
      * Private constructor for avoid "HideUtilityClassConstructor".
      */
     private App() {

     }
    /**
     * @see Logger
     * @autor OC
     */
    private static final Logger LOGGER = LogManager.getLogger("App");

    /**
    * Allows you to launch the application.
     * @autor OC
     * @param args
    * **/
    public static void main(final String[] args) {
        LOGGER.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
