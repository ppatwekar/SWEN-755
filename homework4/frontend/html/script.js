// Function to submit credentials
function submitCredentials(event) {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    
    location.href = "http://localhost:3000/inventory.html";
    

    // fetch('/api/submit-credentials', {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify({ username, password })
    // })
    // .then(response => response.json())
    // .then(data => {
    //     alert('Credentials submitted successfully');
    //     console.log('Response:', data);
    // })
    // .catch(error => {
    //     console.error('Error:', error);
    //     alert('Error submitting credentials');
    // });
}

// Function to submit inventory
function submitInventory(event) {
    event.preventDefault();
    const inventory = document.getElementById('inventory').value;

    fetch('/api/submit-inventory', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ inventory })
    })
    .then(response => response.json())
    .then(data => {
        alert('Inventory submitted successfully');
        console.log('Response:', data);
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error submitting inventory');
    });
}
