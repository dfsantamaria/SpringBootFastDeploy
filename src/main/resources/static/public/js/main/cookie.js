$(document).ready(function()
{
    // Check if the user already accepted 
    if (!Cookies.get('accept_cookies')) //localstorage: !window.localStorage.getItem('accept_cookies'))
    {
     $('#cookie-consent').css('display','block');
    }

    $("#cookie-ok-button").click(function()
    {
        // Save on LocalStorage        
        $('#cookie-consent').fadeOut();
        $('#cookie-consent').css('display','none');
        Cookies.set('accept_cookies', true, {expires: 90});   //localstorage: window.localStorage.setItem('accept_cookies', true);             
    }); 
});