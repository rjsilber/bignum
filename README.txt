This project was created as part of a graduate computer science course.
This project reads arithmetic expressions (addition +, multiplication *, and exponentiation ^)
written in reverse polar notation. Expressions are terminated by empty lines.
The program reads the arithmetic expressions in RPN from the input file input.txt, and if
the expression is valid, it will use the ExpressionElementConstructor class to calculate
the result of the expression, performing infinite precision arithmetic.
Note: Exponent values are guaranteed to be finite arithmetic values (computationally).
The package com.robynsilber.bignum contains five Java classes: Expression, ExpressionElement,
ExpressionElementConstructor, Operand, and Operator. This package was created to handle the
infinite precision arithmetic calculations.