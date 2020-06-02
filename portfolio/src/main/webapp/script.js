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

window.addEventListener("load", function(){
    const page = document.querySelector(".menu-page");
    page.className += " appear";
});

function successfulSubmit() {
    alert("Thank you for contacting me");
}

function fadeOut(){
    (document.getElementsByClassName("page")[0].style.opacity = '0').then(
        setTimeout(function(){window.location.href = "./main.html";}, 1000));
}

//From Week-3 Tutorial
function getGreeting() {
  console.log('Handling the response.');
  fetch('/data').then(response => response.text()).then((quote) => {
    document.getElementsByClassName('greeting-cont')[0].innerText = quote;
  });
}

