# ClipBoard++
ClipBoard++ is a simple JavaFX application for saving clipboard text.
ClipBoard++ will constantly poll the system clipboard for new text and save it in
the application's TableView, this text can be accessed at a later time.

Effectively, ClipBoard++ is a clipboard history manager, ensuring that you 
never accidentally overwrite anything that you copy.

## Installation
**If you're on Windows 10, an installer is available [here](./installer/clipboard++_setup_win10_64-bit.exe).**
*** Keep in mind, I'll only update this periodically as it takes some time to wrap the JAR file, 
build the installer, add the icons and such. If you want the latest, please build from source. ***

**If you're on a 64-bit Linux, we also include a compiled jar in `./installer` for your convenience.**

**If you're on MacOS, sorry, I'm not maintaining another build. Just build from source, or try to run the JAR file directly, there shouldn't be any issues.**

## Usage
#### Screenshots
1. Home page where no clipboards are present.

![screenshot1](./img/ClipBoard++(1).png)

2. All clipboards items inserted, in the software also you have the possibility to edit or delete existing entries.

![screenshot2](./img/ClipBoard++(2).png)

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[GPL v3](https://www.gnu.org/licenses/gpl-3.0.en.html)

Note: ClipBoard++ makes no use of C++, the name simply implies a Clipboard a step above the system clipboard.
