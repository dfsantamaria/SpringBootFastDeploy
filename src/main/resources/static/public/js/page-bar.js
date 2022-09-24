function submitPage(formname, inputname, pageNum)
 {
  document.getElementById(inputname).value=pageNum;   
  document.getElementById(formname).submit();
 }          
    