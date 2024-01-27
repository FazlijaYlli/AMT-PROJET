# Pre-requisites

- [Rust](https://www.rust-lang.org/tools/install)
- [Node.js](https://nodejs.org/en/download/)
- Tauri CLI: `cargo install tauri-cli`

# Dev mode

Run the watch command for CSS from `src` folder to generate the `output.css`` file
```shell script
npx tailwindcss -i ./static/style/styles.css -o ./static/style/output.css --watch
```

Run the tauri app from the `src-tauri` folder
```shell script
cargo tauri dev
```

# Build
Run the build command inside the `src-tauri` folder
```shell script
cargo tauri build 
```

Output will be in `src-tauri/target/release/bundle` folder.