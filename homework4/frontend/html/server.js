const express = require('express');
const app = express();
const PORT = 3000;

// Middleware to parse JSON request bodies
app.use(express.json());

// Serve static files (HTML, CSS, JS)
app.use(express.static(__dirname));

// Endpoint for submitting credentials
app.post('/api/submit-credentials', (req, res) => {
    const { username, password } = req.body;
    console.log(`Received credentials: Username=${username}, Password=${password}`);
    res.json({ message: 'Credentials received successfully' });
});

// Endpoint for submitting inventory
app.post('/api/submit-inventory', (req, res) => {
    const { inventory } = req.body;
    console.log(`Received inventory: ${inventory}`);
    res.json({ message: 'Inventory received successfully' });
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
