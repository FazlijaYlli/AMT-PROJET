const { invoke } = window.__TAURI__.tauri;

let greetInputEl;
let greetMsgEl;

function register() {
  // TODO : Get the informations from the forms and send them to the server
}

function login() {
  // TODO : send infos to the server and allow login if success.
}

function changeChannel(channel_id) {
  // TODO : clear the message area and load the messages stored in the channel using the ID.
}

function addMessage(img_link, content) {
  // TODO : Send the message to the server and only show to message if a success response is received.
  messageArea = document.querySelector("#message-area");
  let element = document.createElement("div")
  element.innerHTML = `
  <div class="flex justify-end mb-4">
  <div class="mr-2 py-3 px-4 bg-blue-400 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl text-white">
  ${content}
  </div>
  <img src="${img_link}"
  class="object-cover h-8 w-8 rounded-full"
  alt="" />
  </div>`
  messageArea.appendChild(element);
}

/*
async function greet() {
  // Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
  greetMsgEl.textContent = await invoke("greet", { name: greetInputEl.value });
}*/

async function testServer() {
  let content = await invoke("test_server");
  addMessage('./img/foot.jpg', content)
}