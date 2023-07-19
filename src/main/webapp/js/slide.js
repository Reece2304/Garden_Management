var slides = document.getElementsByClassName("Slides");
var slideIndex = slides.length;

showSlides(slideIndex); 

function Slide(n) {
	  showSlides(slideIndex = n);
	}

function changeSlides(n) {
  showSlides(slideIndex += n);
}

function showSlides(n) {
  var i;
  var slides = document.getElementsByClassName("Slides");
  var display = document.getElementsByClassName("inactive");
  var captionText = document.getElementById("caption");
  if (n > slides.length) {slideIndex = 1} //if at the end of the list and the user presses the right arrow
  if (n < 1) {slideIndex = slides.length} //if at the start of the list and the user presses the left arrow
  for (i = 0; i < slides.length; i++) {
    slides[i].style.display = "none"; //don't display the slides that aren't currently active
  }
  for (i = 0; i < display.length; i++) {
    display[i].className = display[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "flex";
  display[slideIndex-1].className += " active"; //display the new active slide
  captionText.innerHTML = display[slideIndex-1].alt;
}