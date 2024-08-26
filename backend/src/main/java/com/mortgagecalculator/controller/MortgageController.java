package com.mortgagecalculator.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mortgage")
public class MortgageController {

    @PostMapping("/calculate")
    public double calculateMortgage(@RequestParam int principal,
                                    @RequestParam double annualInterest,
                                    @RequestParam int years) {
        final int MONTHS_IN_YEAR = 12;
        final int PERCENT = 100;

        double monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        int numberOfPayments = years * MONTHS_IN_YEAR;

        double mortgage = principal 
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);

        return mortgage;
    }

    @PostMapping("/paymentSchedule")
    public double[] calculatePaymentSchedule(@RequestParam int principal,
                                             @RequestParam double annualInterest,
                                             @RequestParam int years) {
        final int MONTHS_IN_YEAR = 12;
        final int PERCENT = 100;

        double monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        int numberOfPayments = years * MONTHS_IN_YEAR;
        double mortgage = calculateMortgage(principal, annualInterest, years);

        double[] paymentSchedule = new double[numberOfPayments];
        for (int month = 1; month <= numberOfPayments; month++) {
            paymentSchedule[month - 1] = mortgage;
        }

        return paymentSchedule;
    }
}
