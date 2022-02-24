package io.github.kosmx.nettytest.server;

import org.apache.commons.cli.*;

public class Main {

    //This will be a standalone program anyway
    public static void main(String[] args) {
        Options options = new Options();
        var port = new Option("p", "port", true, "Server port");
        port.setRequired(true);
        options.addOption(port);
        var address = new Option("a", "address", true, "Server address");
        address.setRequired(false);
        options.addOption(address);
        var hideWarn = new Option("h", "hideWarning", false, "Hide no encryption warning");
        options.addOption(hideWarn);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();


        try {
            var cmd = parser.parse(options, args);
            int portNum = Integer.parseInt(cmd.getOptionValue(port));
            String serverAddress = cmd.hasOption(address) ? cmd.getOptionValue(address) : "0.0.0.0";
            if (serverAddress.equals("0.0.0.0")) System.out.println("Unsupported option: address");

            if (!cmd.hasOption(hideWarn)) {
                printWarning();
            }

            var server = new Server(portNum);

        } catch(ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);

        }
    }

    private static void printWarning() {

        System.out.println("The server does NOT encrypt nor verify the clients");
        System.out.println("Do not use this to send sensitive data");

    }

}
