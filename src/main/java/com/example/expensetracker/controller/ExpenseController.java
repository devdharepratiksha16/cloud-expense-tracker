
package com.example.expensetracker.controller;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @ModelAttribute("newExpense")
    public Expense prepareNewExpense() {
    return new Expense();
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("expenses",
                expenseService.getAllExpenses());

        model.addAttribute("totalIncome",
                expenseService.getTotalIncome());

        model.addAttribute("totalExpenses",
                expenseService.getTotalExpenses());

        model.addAttribute("balance",
                expenseService.getBalance());

        model.addAttribute("newExpense", new Expense());

        return "index";
    }

    @PostMapping("/add")
    public String addExpense(
            @ModelAttribute("newExpense") Expense expense,
            RedirectAttributes redirectAttributes) {

        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }

        expenseService.addExpense(expense);

        redirectAttributes.addFlashAttribute(
                "message",
                "Transaction added successfully!");

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "redirect:/";
    }
}
