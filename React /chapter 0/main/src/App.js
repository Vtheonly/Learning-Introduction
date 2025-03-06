// App.js
import React from "react";

const num1 = 10;
const num2 = 5;

function App() {
  return (
    <div>


      {(() => {


        
        if (num1 > num2) {
          return <p>Num1 is greater than num2 (using function)</p>;
        }


      })()}




    </div>
  );
}

export default App;
