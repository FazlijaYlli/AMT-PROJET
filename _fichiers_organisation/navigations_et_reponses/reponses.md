
# Landing Page

## GET /landing 

Géré dans le frontend

# Root (/)

## GET /login

Géré dans le frontend

## POST /login

Form params
```POST form-param
    email: "email",
    password: "password" 
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't login"
}
```

### case 2: Success

Status 200 response with session cookie `quarkus-credential`. Cookie needs to be sent for every authenticated request

Redirect to GET `/servers`

## GET /me (Auth)

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't get user"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "Get user success",
    "data": {
        "id": 1,
        "username": "authUser",
        "email": "email",
    }
}
```

## GET /register

Géré dans le frontend

## POST /register

Form params
```POST form-param
    username: "username",
    email: "email",
    password: "password",
    password-confirm: "password"
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't register"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "Register success",
}
```

Redirect to `/login`

## POST /logout (Auth)

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't logout"
}
```

### case 2: Success

Status 200 response with immediatly expiring session cookie `quarkus-credential`

Redirect to `/landing`

# /servers (Auth)

## GET /

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't list servers"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "List of all servers",
    "data": [
        {
            "id": 1,
            "name": "server1",
            "owner": "ownerId"
        },
        {
            "id": 2,
            "name": "server2",
            "owner": "ownerId"
        }
        ...
        {
            "id": 10,
            "name": "server10",
            "owner": "ownerId"
        }
    ]
}
```

## GET /joined

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't list joined servers"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "List of all joined server for username",
    "data": [
        {
            "id": 1,
            "name": "server1",
            "owner": "ownerId"
        },
        {
            "id": 2,
            "name": "server2",
            "owner": "ownerId"
        }
        ...
        {
            "id": 10,
            "name": "server10",
            "owner": "ownerId"
        }
    ]
}
```

## GET /create

Géré dans le frontend

## POST /create

Form params
```POST form-param
    serverName: "name",
    owner: "id"
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't create server"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "Create server success",
    "data": {
        "id": 1,
        "name": "createdServerName",
        "owner": "ownerId"
    }
}
```

Redirect to GET `/server/{serverId}`

## POST /join

Form params
```POST form-param
    serverId: "id"
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't join server"
}
```

### case 2: Success

```json
{
    "status": 200,
    "message": "Join server success",
    "data": {
        "id": 1,
        "name": "joinedServerName",
        "owner": "ownerId"
    }
}
```

Redirect to GET `/server/{serverId}`

# /server (Auth)

## GET /{serverId}

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

# /server/{serverId}/category (Auth) 

## GET /create (ServerOwner)

Géré dans le frontend

## POST /create (ServerOwner)

Form params
```POST form-param
    categoryName: "name"
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't create category"
}
```

### case 2: Success

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

Redirect to GET `/server/{serverId}`

# /server/{serverId}/category/{categoryId}/channel (Auth)

## GET /{channelId}

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't get channel"
}
```

### case 2: Success

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
                "timestamp": "unix-timestamp"
            },
            {
                "id": 2,
                "content": "message2",
                "author": "author2",
                "timestamp": "unix-timestamp"
            },
            // ...
            {
                "id": 10,
                "content": "message10",
                "author": "author10",
                "timestamp": "unix-timestamp"
            }
        ]
    }
}
```

Populate the channel page with the data


## GET /create (ServerOwner)

Géré dans le frontend

## POST /create (ServerOwner)

Form params
```POST form-param
    channelName: "name"
```

### case 1: Error

```json
{
    "status": 400,
    "message": "Error, can't create channel"
}
```

### case 2: Success

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

Redirect to GET `/server/{serverId}`
