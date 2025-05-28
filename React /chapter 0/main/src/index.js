// index.js
import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
let n = 1;
let s = 0;
let i = 1;

const root = ReactDOM.createRoot(document.getElementById("root"));



function renderFun() {

    n=2*n;
    s=s+ (n/100);

  root.render(
    <div>
      <App />
      <h1>total: {s} , added: {n/100}, day: {i++}</h1>
    </div>
  );
}

setInterval(renderFun, 1000);
