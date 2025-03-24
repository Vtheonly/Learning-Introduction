r/ app.js
console.log('Hello from app.js!');

function add(a, b) {
  return a + b;
	
}

function multiply(a, b) {
  return a * b;
}

const resultAdd = add(20, 40);
console.log('Result of add:', resultAdd);

const resultMultiply = multiply(10, 5);
console.log('Result of multiply:', resultMultiply);

const numbers = [1, 2, 3, 4, 5];
const sum = numbers.reduce((acc, curr) => acc + curr, 0);
console.log('Sum of numbers:', sum);
