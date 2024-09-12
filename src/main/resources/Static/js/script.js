
let cuurentTheme=getTheme();
changeTheme();


function setTheme(theme){
  localStorage.setItem("theme",theme);
}

function getTheme(){
  let theme= localStorage.getItem("theme");
  return theme ? theme:"light";
}

function changeTheme(){
  document.querySelector("html").classList.add(cuurentTheme);

  const changeThemebutton=document.querySelector("#theme_change_button");

  changeThemebutton.querySelector("span").textContent = cuurentTheme == "light"? "Dark" : "Light";
  
  changeThemebutton.addEventListener("click",(event)=>{
    let oldtheme=cuurentTheme;
    if(cuurentTheme == "dark"){
      cuurentTheme="light"
    }else{
      cuurentTheme="dark"
    }
    setTheme(cuurentTheme);
    document.querySelector("html").classList.remove(oldtheme);
    document.querySelector("html").classList.add(cuurentTheme);
    changeThemebutton.querySelector("span").textContent = cuurentTheme == "light" ? "Dark" : "Light";

  });


}