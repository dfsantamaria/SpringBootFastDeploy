$(document).ready(function()
{
    // Check if the user already accepted 
    if (window.localStorage.getItem('accept_cookies'))
    {
        $('#cookie-consent').css('display','none');
    }

    $("#cookie-ok-button").click(function()
    {
        // Save on LocalStorage        
        $('#cookie-consent').fadeOut();
        $('#cookie-consent').css('display','none');
        window.localStorage.setItem('accept_cookies', true);              
    }); 
});


