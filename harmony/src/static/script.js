const { invoke } = window.__TAURI__.tauri

let greetInputEl;
let greetMsgEl;

async function register() {
  const username = document.getElementById("username").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const passwordConfirm = document.getElementById("password-confirm").value;

  let content = await invoke("register", { username: username, email: email, password: password, passwordConfirm: passwordConfirm });
  console.log(content);
}

async function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  let content = await invoke("login", { email: email, password: password});
  console.log(content);
}

async function listServers() {
  let content = await invoke("list_servers");
  // TODO : Add the servers to the HTML layout.
  // content.data contient la liste des serveurs.
  console.log(content);
}

async function createServer() {
  const serverName = document.getElementById("server-name").value;
  // TODO : get the owner from the session
  const owner = document.getElementById("owner").value;
  let content = await invoke("create_server", { server_name: serverName, owner: owner });
  console.log(content);
}

async function joinServer() {
  const serverId = document.getElementById("server-id").value;
  let content = await invoke("join_server", { server_id: serverId });
  console.log(content);
}

async function getServer() {
  // TODO : Get the server ID from the session
  const serverId = document.getElementById("server-id").value;
  let content = await invoke("get_server", {server_id: serverId});
  console.log(content);
}

async function createCategory() {
  const serverId = document.getElementById("server-id").value;
  const categoryName = document.getElementById("category-name").value;
  let content = await invoke("create_category", { server_id: serverId, category_name: categoryName });
  console.log(content);
}

async function getChannel() {
  // TODO : Get the correct channel ID from the click event
  const serverId = document.getElementById("server-id").value;
  const categorylId = document.getElementById("category-id").value;
  const channelId = document.getElementById("channel-id").value;
  let content = await invoke("get_channel", { server_id: serverId, category_id: categorylId, channel_id: channelId });
  console.log(content);
}

async function createChannel() {
  // TODO get the category and server id correctly
  const serverId = document.getElementById("server-id").value;
  const categorylId = document.getElementById("category-id").value;
  const channelName = document.getElementById("channel-name").value;
  let content = await invoke("create_channel", { server_id: serverId, category_id: categorylId, channel_name: channelName });
  console.log(content);
}

async function logout() {
  let content = await invoke("logout");
  console.log(content);
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