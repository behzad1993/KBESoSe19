package de.htw.ai.kbe.runmerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.lineSeparator;


/**
 * Run me runner.
 */
public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private static final String USAGE_MESSAGE = "USAGE: -c className is required (optional: -o reportName).";


    public static void main(String[] args) {
        try {
            // 1.) parse arguments from command line
            String[] parsedArgs = parseArguments(args);
            String requiredClassName = parsedArgs[0];
            String reportOutputFileName = parsedArgs[1];

            // 2.) produce report concerning annotation class
            String report = reportMaker(requiredClassName);

            // 3.) save report to file or log to console
            String classNameMessage = "OK. That's your class name: " + requiredClassName;
            if (reportOutputFileName != null) {
                saveToFile(reportOutputFileName, report);
                LOGGER.log(Level.INFO, classNameMessage + ". That's your report output file: " + parsedArgs[1]);
            } else {
                LOGGER.log(Level.INFO, classNameMessage + ". No output file given. That's the output:" + lineSeparator() + report);
            }

            // catch exceptions concerning parsing
        } catch (MissingOptionException | MissingArgumentException somethingIsMissing) {
            LOGGER.severe(somethingIsMissing.getMessage() + ". " + USAGE_MESSAGE);
        } catch (ParseException exp) {
            LOGGER.severe("Parsing failed. Reason: " + exp.getMessage());

            // catch exceptions concerning report generating
        } catch (IllegalAccessException e) {
            LOGGER.severe("Generating of report failed. Reason: illegal access. " + e.getMessage());
        } catch (InstantiationException e) {
            LOGGER.severe("Generating of report failed. Reason: instantiation failed.");
        } catch (ClassNotFoundException e) {
            LOGGER.severe("Generating of report failed. Reason: " + e.getMessage() + " class not found.");
        }
    }


    /*package-private*/ static String[] parseArguments(String[] args) throws ParseException {
        // create the parser
        CommandLineParser parser = new DefaultParser();
        // create the Options
        Options options = new Options();
        options.addRequiredOption("c", "className", true, "Name of class");
        options.addOption("o", "reportOutputFile", true, "Output file for report (optional)");

        return doParseArguments(args, parser, options);
    }

    private static String[] doParseArguments(String[] args, CommandLineParser parser, Options options) throws ParseException {
        // parse the command line arguments (throws all of the exceptions)
        CommandLine line = parser.parse(options, args);

        String[] checkedArgs = new String[2];
        checkedArgs[0] = line.getOptionValue("c");

        if (line.hasOption("o")) {
            checkedArgs[1] = line.getOptionValue("o");
        }
        return checkedArgs;
    }

    /*package-private*/ static String reportMaker(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (className == null) {
            throw new ClassNotFoundException();
        }
        Class<?> c = Class.forName(className);
        Object instance = c.newInstance();
        Method[] methods = c.getDeclaredMethods();

        return doReportMaker(methods, instance);
    }

    private static String doReportMaker(Method[] methods, Object instance) {
        StringBuilder reportString = new StringBuilder();

        for (Method method : methods) {
            reportString
                    .append(lineSeparator())
                    .append("Currently reporting about invocation of method: ")
                    .append(method.getName())
                    .append(" -> ");
            if (method.isAnnotationPresent(RunMe.class)) {
                try {
                    method.invoke(instance);
                    reportString
                            .append(" Success!");

                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                    reportString.append(" Not invokable: ").append(e.getClass().getSimpleName());
                }
            } else {
                reportString.append(" Found no annotation!");
            }
            reportString.append(lineSeparator());
        }
        return reportString.toString();
    }

    /*package-private*/ static void saveToFile(String reportOutputFileName, String message) {
        try {
            doSaveToFile(reportOutputFileName, message);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            LOGGER.warning(e.getMessage());
        }
    }

    private static void doSaveToFile(String reportOutputFileName, String message) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(reportOutputFileName, "UTF-8");
        writer.println(message);
        writer.close();
    }
}
