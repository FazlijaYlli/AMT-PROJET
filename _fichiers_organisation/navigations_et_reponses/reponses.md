
# landing Page

## GET landingPage()

géré dans le frontend

# login

## GET login()

géré dans le frontend

## POST login(user)

```POST form-param
    username: "username",
    password: "password" 
```

### case 1: login error

```json
{
    "status": 400,
    "message": "Error, can't login"
}
```

### case 2: login success

```json
{
    "status": 200,
    "message": "Login success",
    "data" : 
        {
            "username": "username",
            "token": "TOKEN" // TODO : ok ??
        },
}
```

then redirect to GET listServers(authUser)


# register

## GET register()

géré dans le frontend

## POST register(user)

```POST form-param
    username: "username",
    email: "email",
    password: "password",
    password-confirm: "password"
```

### case 1: register error

```json
{
    "status": 400,
    "message": "Error, can't register"
}
```

### case 2: register success

```json
{
    "status": 200,
    "message": "Register success",
}
```

redirect to login

# listServers

## GET listServers(authUser)

### case 1: listServers error

```json
{
    "status": 400,
    "message": "Error, can't list servers"
}
```

### case 2: listServers success

```json
{
    "status": 200,
    "message": "List of servers for user authUser",
    "data": [
        {
            "id": 1,
            "name": "server1"
        },
        {
            "id": 2,
            "name": "server2"
        }
        ...
        {
            "id": 10,
            "name": "server10"
        }
    ]
}
```

# createServerForm

## GET createServerForm()

géré dans le frontend

## POST createServerForm(authUser, server)

```POST form-param
    serverName: "name",
    owner: "email"
```

### case 1: createServerForm error

```json
{
    "status": 400,
    "message": "Error, can't create server"
}
```

### case 2: createServerForm success

```json
{
    "status": 200,
    "message": "Create server success",
    "data": {
        "id": 1,
        "name": "createdServerName"
    }
}
```

Redirect to GET server(createdServer)

# joinServer action

## POST joinServer(authUser, serverId)

```POST form-param
    serverId: "id"
```

### case 1: joinServer error

```json
{
    "status": 400,
    "message": "Error, can't join server"
}
```

### case 2: joinServer success

```json
{
    "status": 200,
    "message": "Join server success",
    "data": {
        "id": 1,
        "name": "joinedServerName"
    }
}
```

Redirect to GET server(serverId)

# server

## GET server(authUser, serverId)

### case 1: server error

```json
{
    "status": 400,
    "message": "Error, can't get server"
}
```

### case 2: server success

```json
{
    "status": 200,
    "message": "Get server success",
    "data": {
        "id": 1,
        "name": "serverName",
        "owner": "serverOwner",
        "categories": [
            {
                "id": 1,
                "name": "category1",
                "channels": [
                    {
                        "id": 1,
                        "name": "channel1"
                    },
                    {
                        "id": 2,
                        "name": "channel2"
                    }
                    ...
                    {
                        "id": 10,
                        "name": "channel10"
                    }
                ]
            },
            {
                "id": 2,
                "name": "category2",
                "channels": [
                    ...
                ]
            },
            ...
            {
                "id": 10,
                "name": "category10",
                "channels": [
                    ...
                ]
            },
        ]
    }
}
```

Populate the server page with the data

# createCategoryForm

ACCESS ONLY FOR SERVER OWNER

## GET createCategoryForm(authUser, serverId)

géré dans le frontend

## POST createCategoryForm(authUser, serverId)

```POST form-param
    categoryName: "name"
```

### case 1: createCategoryForm error

```json
{
    "status": 400,
    "message": "Error, can't create category"
}
```

### case 2: createCategoryForm success

```json
{
    "status": 200,
    "message": "Create category success",
    "data": {
        "id": 1,
        "name": "createdCategoryName"
    }
}
```

Redirect to GET server(serverId)

# createChannelForm

ACCESS ONLY FOR SERVER OWNER

## GET createChannelForm(authUser, serverId)

géré dans le frontend

## POST createChannel(authUser, serverId, categoryId)

```POST form-param
    categoryId: "id"
    channelName: "name"
```

### case 1: createChannel error

```json
{
    "status": 400,
    "message": "Error, can't create channel"
}
```

### case 2: createChannel success

```json
{
    "status": 200,
    "message": "Create channel success",
    "data": {
        "id": 1,
        "name": "createdChannelName"
    }
}
```

Redirect to GET server(serverId)

# channel

## GET channel(authUser, serverId, channelId)

### case 1: channel error

```json
{
    "status": 400,
    "message": "Error, can't get channel"
}
```

### case 2: channel success

```json
{
    "status": 200,
    "message": "Get channel success",
    "data": {
        "id": 1,
        "name": "channelName",
        "messages": [
            {
                "id": 1,
                "content": "message1",
                "author": "author1",
                "timestamp": "dd/mm/yyyy"
            },
            {
                "id": 2,
                "content": "message2",
                "date": "date2"
            }
            ...
            {
                "id": 10,
                "content": "message10",
                "date": "date10"
            }
        ]
    }
}
```

Populate the channel page with the data
