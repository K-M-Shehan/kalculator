# kalculator

> a simple Kotlin calculator app for Android.

## Features

- Basic arithmetic: addition, subtraction, multiplication, and division
- Decimal support with accurate calculations
- Modulus and percentage operations
- Chained calculations without clearing between steps
- Expandable history panel showing recent operations
- Scrollable display so long expressions do not push buttons off screen
- Clear error messages for invalid operations and divide-by-zero cases

## How to use

1. Enter numbers with the keypad.
2. Pick an operator such as `+`, `-`, `×`, `÷`, `mod`, or `%`.
3. Tap `=` to calculate the result.
4. Use the history toggle to view more or fewer recent calculations.
5. Use `C` to clear everything or `⌫` to remove the last digit.

## Notes

- Chained operations are evaluated left to right.
- Long values stay on one line and can be scrolled inside the display area.
- Errors are shown directly in the display so the app can be used without guessing what went wrong.