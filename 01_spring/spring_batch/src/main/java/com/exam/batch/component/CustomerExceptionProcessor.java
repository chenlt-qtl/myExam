package com.exam.batch.component;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomerExceptionProcessor implements ItemProcessor<String, String> {

    private int attempCount = 0;

    @Override
    public String process(String item) throws Exception {
        System.out.println("processing item " + item);
        if (item.equalsIgnoreCase("26")) {
            attempCount++;
            if (attempCount >= 3) {
                System.out.println("Retried " + attempCount + " times success");
                return String.valueOf(Integer.parseInt(item) * -1);
            }else {
                System.out.println("Processed the "+ attempCount+" times fail");
                throw new CustomerRetryException("Process failed. Attempt: "+ attempCount);
            }
        }else {
            return String.valueOf(Integer.parseInt(item) * -1);
        }
    }
}
