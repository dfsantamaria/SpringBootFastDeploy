 /* @author Daniele Francesco Santamaria daniele.santamaria@unict.it 
 -- https://github.com/dfsantamaria/SpringBootFastDeploy.git -- */

function togglePassword(password, show, hide)
 {
  var x = document.getElementById(password);
  var show_eye = document.getElementById(show);
  var hide_eye = document.getElementById(hide);
  hide_eye.classList.remove("d-none");
  if (x.type === "password")
   {
    x.type = "text";
    show_eye.style.display = "none";
    hide_eye.style.display = "block";
  } 
  else 
  {
    x.type = "password";
    show_eye.style.display = "block";
    hide_eye.style.display = "none";
  }
}