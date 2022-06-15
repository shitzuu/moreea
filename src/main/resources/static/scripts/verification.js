document.getElementById('code-button').addEventListener('click', () => {
    const code = document.getElementById('code');
    if (code.className === 'show') {
        code.className = 'hide';
    } else {
        code.className = 'show';
    }
    document.getElementById('code-button').remove();
});