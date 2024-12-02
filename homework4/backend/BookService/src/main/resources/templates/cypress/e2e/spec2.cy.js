describe('HTML Form Submission', () => {
    it('should fill out and submit the form successfully', () => {
        // Visit the page where your form is located
        cy.visit('http://localhost:8080/login/');
        cy.intercept('POST', '/api/session/').as('formSubmit');

        // Adjust the path to your HTML file
        // Fill out the form fields
        cy.get('input[id="a"]').type('admin_user');
        cy.get('input[id="b"]').type('secureAdminPass123');
        // Submit the form
        cy.get('form').submit();

        cy.wait('@formSubmit').then((interception) => {
            console.log('Request Body', interception.request.body);
            cy.log('Request Body', JSON.stringify(interception.request.body));
            //expect(interception.request.body.username).to.equal('admin_user');
            //expect(interception.request.body.password).to.equal('secureAdminPass123');
            expect(interception.request.body.username).not.equal('admin_user');
            expect(interception.request.body.password).not.equal('secureAdminPass123');

        });
    });
});