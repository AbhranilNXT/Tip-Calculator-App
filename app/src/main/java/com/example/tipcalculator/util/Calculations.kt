package com.example.tipcalculator.util

fun totalTip(
    totalBill: Double,
    tipPercentage: Double
): Double {
    return if(totalBill > 1 &&
        totalBill.toString().isNotEmpty())
        ((totalBill * tipPercentage) / 100) else 0.00

}


fun calculateTotalPerPerson(totalBill: Double,
                            splitBy : Int,
                            tipPercentage: Double) : Double {
    val bill = totalTip(
        totalBill =totalBill,
        tipPercentage = tipPercentage) + totalBill
    return (bill/splitBy)
}