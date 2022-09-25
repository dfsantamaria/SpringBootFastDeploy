/**
 *
 * @author Daniele Francesco Santamaria daniele.santamaria@unict.it
 * -- https://github.com/dfsantamaria/SpringBootFastDeploy.git --
 * 
 */


function submitPage(formname, inputname, pageNum)
 {
  document.getElementById(inputname).value=pageNum;   
  document.getElementById(formname).submit();
 }          
    