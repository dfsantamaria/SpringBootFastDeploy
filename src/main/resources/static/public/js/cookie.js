$(document).ready(function()
{
    // Check if the user already accepted 
    if (window.localStorage.getItem('accept_cookies'))
    {
        $('#cookie-consent').css('visibility','hidden');
    }

    $("#cookie-ok-button").click(function(){
        // Save on LocalStorage
        window.localStorage.setItem('accept_cookies', true);
        $('#cookie-consent').fadeOut();
    }); 
});


