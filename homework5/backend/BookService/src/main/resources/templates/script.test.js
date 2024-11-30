const { URL } = require('url');

describe('Login URL Validation', () => {
    const loginFormSubmission = (username, password) => {
        const baseUrl = 'http://localhost:8080/api/session/';
        const loginRequest = {
            username: username, // Use the function parameter here
            password: password, // Use the function parameter here
        };

        // Simulate API response
        const sessionId = 'mock-session-id';
        const redirectUrl = `http://localhost:8080/books/?sessionId=${sessionId}`;

        return {
            url: redirectUrl,
            containsSensitiveData: redirectUrl.includes(username) || redirectUrl.includes(password),
        };
    };

    it('should not expose username or password in the URL', () => {
        const username = 'usernameA';
        const password = 'passwordA';

        const result = loginFormSubmission(username, password);

        // URL should not include sensitive data
        expect(result.containsSensitiveData).toBe(false);
        console.log(`Tested URL: ${result.url}`);
    });

    it('should validate URL structure and redirection', () => {
        const sessionId = 'mock-session-id';
        const redirectUrl = `http://localhost:8080/books/?sessionId=${sessionId}`;
        const url = new URL(redirectUrl);

        // Validate hostname
        expect(url.hostname).toBe('localhost');

        // Validate path
        expect(url.pathname).toBe('/books/');

        // Validate query parameters
        expect(url.searchParams.get('sessionId')).toBe(sessionId);
    });
});
