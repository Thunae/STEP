// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function successfulSubmit() {
    alert("Thank you for contacting me");
}

var intervalID = 0;
var opacity = 0;

function fadeOut(){
   intervalID = setInterval(hide, 10);
}

function fadeIn(){
   intervalID = setInterval(show, 10);
}

function hide(){
    var current_item = document.getElementById("showcase");
    opacity = Number(window.getComputedStyle(current_item).getPropertyValue("opacity"));
    if(opacity > 0){
        opacity = opacity - 0.05;
        current_item.style.opacity = opacity;
    }else{
        clearInterval(intervalID);
    }
}

function show(){
    var current_item = document.getElementById("showcase");
    opacity = Number(window.getComputedStyle(current_item).getPropertyValue("opacity"));
    if(opacity < 1){
        opacity = opacity + 0.05;
        current_item.style.opacity = opacity;
    }else{
        clearInterval(intervalID);
    }
}
