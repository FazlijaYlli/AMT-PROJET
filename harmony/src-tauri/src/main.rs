// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use tokio;
use reqwest;

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

// REGISTER FUNCTIONS

#[tauri::command]
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

    let request = client.request(reqwest::Method::POST, "https://httpbin.org/post")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END REGISTER FUNCTIONS

// LOGIN FUNCTIONS

#[tauri::command]
fn login(email: &str, password: &str) -> String {
    let response = login_request(email, password).unwrap();
    response.into()
}

#[tokio::main]
async fn login_request(email: &str, password: &str) -> Result<String, Box<dyn std::error::Error>> {
    let client = reqwest::Client::builder().build()?;

    let mut headers = reqwest::header::HeaderMap::new();
    headers.insert("Content-Type", "application/x-www-form-urlencoded".parse()?);

    let mut params = std::collections::HashMap::new();
    params.insert("email", email);
    params.insert("password", password);

    let request = client.request(reqwest::Method::POST, "https://httpbin.org/post")
        .headers(headers)
        .form(&params);

    let response = request.send().await?;
    let body = response.text().await?;

    println!("{}", body);

    Ok(body)
}

// END LOGIN FUNCTIONS

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![greet])
        .invoke_handler(tauri::generate_handler![register])
        .invoke_handler(tauri::generate_handler![login])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}