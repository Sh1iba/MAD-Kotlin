
    fun main() {
        var exp = AllExpense()
        var exp1 = Expense(72,"Транспорт","23.09.2024")
        var exp2 = Expense(1500,"Транспорт","23.09.2024")
        var exp3 = Expense(100,"Еда и напитки","23.09.2024")
        var exp4 = Expense(198,"Еда и напитки","23.09.2024")
        var exp5 = Expense(1200,"Развлечения","10.09.2024")
        var exp6 = Expense(400,"Развлечения","10.02.2023")
        exp.addExpense(exp1)
        exp.addExpense(exp2)
        exp.addExpense(exp3)
        exp.addExpense(exp4)
        exp.addExpense(exp5)
        exp.addExpense(exp6)
        exp.showAllExpenses()
        println("\n" + exp.theSumOfExpensesByCategory())
    }
