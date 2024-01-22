// Prevents additional console window on Windows in release, DO NOT REMOVE!!
#![cfg_attr(not(debug_assertions), windows_subsystem = "windows")]

use std::error::Error;
use tokio;
use reqwest;

// Learn more about Tauri commands at https://tauri.app/v1/guides/features/command
#[tauri::command]
fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[tauri::command]
fn test_server() -> String {
    let content = test_server_async().unwrap();
    content.into()
}

#[tokio::main]
async fn test_server_async() -> Result<String, Box<dyn Error>> {
    let resp = reqwest::get("https://httpbin.org/ip")
        .await?
        .text()
        .await?;
    println!("{:#?}", resp);
    Ok(resp)
}

fn main() {
    tauri::Builder::default()
        .invoke_handler(tauri::generate_handler![greet])
        .invoke_handler(tauri::generate_handler![test_server])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}