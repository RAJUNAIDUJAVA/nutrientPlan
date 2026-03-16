package com.dnapass.training;

import junit.framework.TestSuite;
import org.junit.internal.builders.JUnit3Builder;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Formatter;
import java.util.Locale;

public class TestSuiteRunner {

    public static void main(String [] args){
        Result result = JUnitCore.runClasses(TestSuite.class);

        for(Failure failure : result.getFailures()){
            System.out.println("failed");
            System.out.println(failure.toString());
            System.out.println(failure.getTrimmedTrace());
            System.out.println(formatOutput(failure.getTestHeader(),"0",failure.getMessage()));
        }
        testRunFinished(result);
    }

    public static void testRunFinished(Result result){
        if(result != null){
            System.out.println("\n testRunFinished :"+ String.valueOf(result) + "\n time:"
                    +String.valueOf(result.getRunTime()) + " \n Run Count :"+ String.valueOf(result.getRunCount())
                    +" \n Failure Count :" + String.valueOf(result.getFailureCount()) + "\n Ignore count :"
                    + String.valueOf(result.getIgnoreCount())+ "\n is Successfull :"+ result.wasSuccessful());

                Double weightage = 100.0/result.getRunCount();
                Integer successCount = result.getRunCount() - result.getFailureCount() - result.getIgnoreCount();
                Double grade= successCount*weightage;

            System.out.println("Grade :=>>" + grade);
            System.out.println(formatOutput("",grade.toString(), null));
        }
    }

    private static String formatOutput(String testName, String value, String errorMessage){
        StringBuilder sb = new StringBuilder();
        Formatter f = new Formatter(sb, Locale.getDefault());
        String grade = (errorMessage == null ? value :"0");
        f.format("Comment :=>> %s: %s. %s marks\n", testName, (errorMessage == null ? "success": errorMessage),grade);
        if(errorMessage != null){
            f.format("<|-- \n%s\n --|>\n", errorMessage);
        }
        f.close();
        return sb.toString();
    }
}
