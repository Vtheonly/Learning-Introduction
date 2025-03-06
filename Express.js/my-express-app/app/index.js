const express = require('express'); // Import Express
const app = express(); // Create an Express app
const port = 3000; // Define the port

// Define a route for the root URL
app.get('/', (req, res) => {
    res.send('Hello from Express!');
});

// Define a route for the root URL
app.get('/user/:id/nodi/:id3/jogi', (req, res) => {
    const userId = req.params.id;
    const userId3 = req.params.id3;
    res.send(`User ID: ${userId}`);
    res.send(`User ID: ${userId3}`);
  });

// Start the server
app.listen(port, () => {
    console.log(`App running at http://localhost:${port}`);
});
