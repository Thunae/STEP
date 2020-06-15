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
  const greetings = [
    "Hello world!",
    "¡Hola Mundo!",
    "你好，世界！",
    "Bonjour le monde!",
  ];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById("greeting-container");
  greetingContainer.innerText = greeting;
}

window.addEventListener("load", function () {
  const page = document.querySelector(".menu-page");
  page.className += " appear";
});

function successfulSubmit() {
  alert("Thank you for contacting me");
}

function fadeOut() {
  document.getElementsByClassName("page")[0].style.opacity = "0";
  setTimeout(() => {
    window.location.href = "./main.html";
  }, 1000);
}

//From Week-3 Tutorial
function getComments() {
  //Get the number of comments to show per page
  numComments = localStorage.getItem("num-of-comments");
  fetch(`/comments?num-comments=${numComments}`)
    .then((response) => response.json())
    .then((comments) => {
      container = document.getElementsByClassName("comments-section")[0];
      container.innerHTML = "";
      for (item of comments) {
        container.appendChild(createListElement(item));
      }
    });
  document.getElementsByName("num-comments")[0].value = numComments;
}

/** Creates an <li> element containing text. */
function createListElement(comment) {
  const liElement = document.createElement("li");
  liElement.className += "comment-list";

  fetch("/comment.html")
    .then((response) => response.text())
    .then((data) => {
      liElement.innerHTML = data;
      liElement.getElementsByClassName("comment-words")[0].innerText =
        comment.content;
      //Add funcitonality to the delete button
      deleteButtonElement = liElement.getElementsByClassName(
        "comment-delete"
      )[0];
      deleteButtonElement.addEventListener("click", () => {
        deleteComment(comment);

        // Remove the comment from the DOM.
        liElement.remove();
      });
      liElement.getElementsByClassName("comment-date")[0].innerText =
        comment.date;
    });

  return liElement;
}

function amountOfComments() {
  //The program will remember the number of comments the user wants to display
  num = document.getElementsByName("num-comments")[0].value;
  localStorage.setItem("num-of-comments", num);
  getComments();
}

function deleteComment(comment) {
  //Remove a comment and then reload the number of comments
  const params = new URLSearchParams();
  params.append("id", comment.id);
  fetch("/delete-data", { method: "POST", body: params }).then(() =>
    getComments()
  );
}

function findSkill(num) {
  let skills = [
    "skills/cpp-skill.html",
    "skills/py-skill.html",
    "skills/java-skill.html",
    "skills/html-skill.html",
    "skills/css-skill.html",
    "skills/js-skill.html",
    "skills/git-skill.html",
    "skills/bash-skill.html",
    "skills/matlab-skill.html",
    "skills/gcp-skill.html",
    "skills/sql-skill.html",
    "skills/office-skill.html",
  ];
  displaySkill(skills[num]);
}

function displaySkill(path) {
  fetch(path)
    .then((response) => response.text())
    .then((data) => {
      container = document.getElementById("main-display");
      container.style.opacity = 0;
      console.log(data);
      setTimeout(() => {
        container.innerHTML = data;
      }, 500);
      container.style.opacity = 1;
    });
}
