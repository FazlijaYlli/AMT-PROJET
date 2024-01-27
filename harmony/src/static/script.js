const { invoke } = window.__TAURI__.tauri

async function register() {
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const passwordConfirm = document.getElementById("password-confirm").value;

    let content = JSON.parse(await invoke("register", { username: username, email: email, password: password, password_confirm: passwordConfirm }));

    console.log(content);

    if (content.status === "200") {
        window.location.replace("/static/login.html");
    } else {
        document.getElementById("password").value = "";
        document.getElementById("password-confirm").value = "";
        document.getElementById("error").textContent = "An error occured during register. Please try again.";
    }
}

async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    let content = await invoke("login", { email: email, password: password });

    if (content) {
        JSON.parse(content);
        console.log(content);
        if (content.status === "400") {
            document.getElementById("password").value = "";
            document.getElementById("error").textContent = "An error occured during login. Please try again.";
        } else {
            window.location.replace("/index.html");
        }
    } else {
        window.location.replace("/index.html");
    }
}

async function listServers() {
    let content = JSON.parse(await invoke("list_servers"));
    // TODO : Add the servers to the HTML layout.
    // content.data contient la liste des serveurs.
    const list = document.getElementById("server-list");
    list.innerHTML = "";

    content.data.forEach(element => {
        let li = document.createElement("li");
        li.innerHTML = `
    <div class="flex justify-between align-middle w-full">
        <div class="mt-auto mb-auto pb-3">
            <div class="m-2 ml-7">
                <p class="font-semibold text-xl">${element.name}</p>
                <p class="font-light text-xl">Owner ID : ${element.owner}</p>
            </div>
        </div>
        <button onclick=joinServer("${element.id}") class="m-4 mb-auto mt-auto bg-blue-500 rounded font-semibold text-white h-8 pl-4 pr-4">Join</button>
    </div>`;
        li.style.listStyle = "none";
        list.appendChild(li);
    });

    console.log(content);
}

async function createServer() {
    const serverName = document.getElementById("server-name").value;
    let content = JSON.parse(await invoke("create_server", { server_name: serverName }));
    console.log(content);

    if (content.status == "200") {
        listServers();
    }
}

async function joinServer(serverId) {
    let content = await invoke("join_server", { server_id: serverId });
    console.log(content);
    document.cookie = "server_id=" + serverId;
    window.location.href = "server.html";
}

async function getServer(serverId) {
    let content = JSON.parse(await invoke("get_server", { server_id: serverId }));
    console.log(content);
    return content;
}

async function createCategory() {
    const serverId = document.getElementById("server-id").value;
    const categoryName = document.getElementById("category-name").value;
    let content = await invoke("create_category", { server_id: serverId, category_name: categoryName });
    console.log(content);
}

async function getChannel(serverId, categoryId, channelId) {
    // TODO : Get the correct channel ID from the click event
    let content = JSON.parse(await invoke("get_channel", { server_id: serverId, category_id: categoryId, channel_id: channelId }));
    console.log(content);
    return content;
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

async function getMe() {
    let content = await invoke("get_me");
    //console.log(content);
    return content;
}

async function sendMsg(serverId, categoryId, channelId, text) {
    let content = JSON.parse(await invoke("send_msg", {
        server_id: serverId,
        category_id: categoryId,
        channel_id: channelId,
        text: text
    }
    ));
    console.log(content);

    if (content.status == "200") {
        document.getElementById("input").value = "";
        addMessage("/static/img/pfp_test2.jpg", content.data);
    }
}

function addMessage(img_link, message) {
    // TODO : Send the message to the server and only show to message if a success response is received.
    const messageArea = document.querySelector("#message-area");
    let element = document.createElement("div")
    element.innerHTML = `
    <div class="flex justify-end mb-4">
        <div class="mr-2 py-3 px-4 bg-blue-400 rounded-bl-3xl rounded-tl-3xl rounded-tr-xl text-white">
            <div class="font-semibold">${message.author}</div>
            <div>${message.content}</div>
        </div>
        <img src="${img_link}" class="object-cover h-8 w-8 rounded-full self-end" alt="" />
    </div>`;
    messageArea.appendChild(element);
}