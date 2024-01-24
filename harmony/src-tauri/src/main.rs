// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use tokio;
use std::sync::Arc;
use reqwest::{self, header};
use lazy_static::lazy_static;

lazy_static! {
    static ref JAR: Arc<reqwest::cookie::Jar> = Arc::new(reqwest::cookie::Jar::default());
    static ref CLIENT: reqwest::Client = reqwest::Client::builder().cookie_provider(Arc::clone(&JAR)).build().unwrap();
}

static URL: &str = "http://localhost:8080";

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command(rename_all = "snake_case")]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

// REGISTER FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn register(username: &str, email: &str, password: &str, password_confirm: &str) -> String {
    let response = register_request(username, email, password, password_confirm).unwrap();
    response.into()
}

#[tokio::main]
async fn register_request(username: &str, email: &str, password: &str, password_confirm: &str) -> Result<String, Box<dyn std::error::Error>> {
    let client = reqwest::Client::builder().build()?;

    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("username", username);
    params.insert("email", email);
    params.insert("password", password);
    params.insert("password_confirm", password_confirm);

    let request = client.request(reqwest::Method::POST, URL.to_string()+"/register")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END REGISTER FUNCTIONS

// LOGIN FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn login(email: &str, password: &str) -> () {
    login_request(email, password).unwrap();
}

#[tokio::main]
async fn login_request(email: &str, password: &str) -> Result<(), Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("email", email);
    params.insert("password", password);

    let request = (*CLIENT).request(reqwest::Method::POST, URL.to_string()+"/login")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;

    response.cookies().for_each(|cookie| {
        println!("Got cookie: {:?}", cookie);
    });

    Ok(())
}

#[tauri::command(rename_all = "snake_case")]
fn logout() -> String {
    let response = logout_request().unwrap();
    response.into()
}

#[tokio::main]
async fn logout_request() -> Result<String, Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let request = CLIENT.request(reqwest::Method::POST, URL.to_string() + "/post")
        .headers(headers);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}
// END LOGIN FUNCTIONS

// LIST SERVERS FUNCTION 

#[tauri::command(rename_all = "snake_case")]
fn list_servers() -> String {
    let response = list_servers_request().unwrap();
    response.into()
}

#[tokio::main]
async fn list_servers_request() -> Result<String, Box<dyn std::error::Error>> {
    let request = CLIENT.request(reqwest::Method::GET, URL.to_string() + "/servers");

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END LIST SERVERS FUNCTION

// LIST CREATE SERVER FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn create_server(server_name: &str) -> String {
    let response = create_server_request(server_name).unwrap();
    response.into()
}

#[tokio::main]
async fn create_server_request(server_name: &str) -> Result<String, Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("serverName", server_name);

    let request = CLIENT.request(reqwest::Method::POST, URL.to_string() + "/servers/create")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END CREATE SERVER FUNCTIONS

// JOIN SERVER FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn join_server(server_id: &str) -> String {
    let response = join_server_request(server_id).unwrap();
    response.into()
}

#[tokio::main]
async fn join_server_request(server_id: &str) -> Result<String, Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("serverId", server_id);

    let request = CLIENT.request(reqwest::Method::POST, URL.to_string() + "/servers/join")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END JOIN SERVER FUNCTIONS

// GET SERVER FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn get_server(server_id: &str) -> String {
    let response = get_server_request(server_id).unwrap();
    response.into()
}

#[tokio::main]
async fn get_server_request(server_id: &str) -> Result<String, Box<dyn std::error::Error>> {
    let request = CLIENT.request(reqwest::Method::GET, URL.to_string() + "/server/" + server_id);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END GET SERVER FUNCTIONS

// CREATE CATEGORY FUNCTIONS

#[tauri::command(rename_all = "snake_case")]
fn create_category(server_id: &str, category_name: &str) -> String {
    let response = create_category_request(server_id, category_name).unwrap();
    response.into()
}

#[tokio::main]
async fn create_category_request(server_id: &str, category_name: &str) -> Result<String, Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("categoryName", category_name);
    
    // Put correct URL post
    let request = CLIENT.request(reqwest::Method::POST, URL.to_string() + "/server/" + server_id + "/category/create")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END CREATE CATEGORY FUNCTIONS

// GET CHANNEL FUNCTION 

#[tauri::command(rename_all = "snake_case")]
fn get_channel(server_id: &str, category_id: &str, channel_id: &str) -> String {
    let response = get_channel_request(server_id, category_id, channel_id).unwrap();
    response.into()
}

#[tokio::main]
async fn get_channel_request(server_id: &str, category_id: &str, channel_id: &str) -> Result<String, Box<dyn std::error::Error>> {

    // /server/{serverId}/category/{categoryId}/channel/{channelId}
    let request = CLIENT.request(reqwest::Method::GET,
                                 URL.to_string()
                                     + "/server/" + server_id
                                     + "/category/" + category_id
                                     + "/channel/" + channel_id);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END GET CHANNEL FUNCTION

// START CREATE CHANNEL

#[tauri::command(rename_all = "snake_case")]
fn create_channel(server_id: &str, category_id: &str, channel_name: &str) -> String {
    let response = create_channel_request(server_id, category_id, channel_name).unwrap();
    response.into()
}

#[tokio::main]
async fn create_channel_request(server_id: &str, category_id: &str, channel_name: &str) -> Result<String, Box<dyn std::error::Error>> {
    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("categoryName", channel_name);

    let request = CLIENT.request(reqwest::Method::GET,
                                 URL.to_string()
                                     + "/server/" + server_id
                                     + "/category/" + category_id
                                     + "/channel/create")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END CREATE CHANNEL

fn main() {    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![
            greet,
            register,
            login,
            list_servers,
            create_server,
            join_server,
            get_server,
            create_category,
            get_channel,
            create_channel,
            logout
          ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}