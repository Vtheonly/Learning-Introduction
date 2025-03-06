// DateTime.js
import React from 'react';

function DateTime() {
  const now = new Date();
  const dateString = now.toLocaleDateString();
  const timeString = now.toLocaleTimeString();

  return (
    <div>
      <p>Date: {dateString}</p>
      <p>Time: {timeString}</p>
    </div>
  );
}

export default DateTime;