$(document).ready(function()
{
    // Check if the user already accepted 
    if (window.localStorage.getItem('accept_cookies'))
    {
        $('#cookie-consent').css('display','none');
    }

    $("#cookie-ok-button").click(function(){
        // Save on LocalStorage
        window.localStorage.setItem('accept_cookies', true);
        $('#cookie-consent').css('display','none');
        $('#cookie-consent').fadeOut();
    }); 
});


