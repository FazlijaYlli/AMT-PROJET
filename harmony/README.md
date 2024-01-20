# Tauri + Vanilla

This template should help get you started developing with Tauri in vanilla HTML, CSS and Javascript.

## Launching the web application
1. Install the Tauri CLI : `cargo install create-tauri-app --locked`
2. Launch : `cargo tauri dev`   

## Recommended IDE Setup

- [VS Code](https://code.visualstudio.com/) + [Tauri](https://marketplace.visualstudio.com/items?itemName=tauri-apps.tauri-vscode) + [rust-analyzer](https://marketplace.visualstudio.com/items?itemName=rust-lang.rust-analyzer)

## Run the watch command for CSS from src folder
``npx tailwindcss -i ./static/style/input.css -o ./static/style/output.css --watch``

## Run the tauri app from the src-tauri folder
``cargo tauri dev``