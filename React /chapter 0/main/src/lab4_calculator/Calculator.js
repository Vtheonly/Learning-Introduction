// Calculator.js
import React from 'react';

function Calculator() {
  const num1 = 10;
  const num2 = 5;
  const sum = num1 + num2;
  const difference = num1 - num2;
  const product = num1 * num2;
  const quotient = num1 / num2;

  return (
    <div>
      <p>Sum: {sum}</p>
      <p>Difference: {difference}</p>
      <p>Product: {product}</p>
      <p>Quotient: {quotient}</p>
    </div>
  );
}

export default Calculator;