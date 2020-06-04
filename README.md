# ClipBoard++
ClipBoard++ is a simple JavaFX application for saving clipboard text.
ClipBoard++ will constantly poll the system clipboard for new text and save it in
the application's TableView, this text can be accessed at a later time.

Effectively, ClipBoard++ is a clipboard history manager, ensuring that you 
never accidentally overwrite anything that you copy.

If you are looking for the binary executable, a copy is available in the `bin` directory in this repository.
I'll try to keep a compiled jar file for Windows 10, MacOS, and Linux 64-bit systems, although I can't promise that
this will be extremely up to date. You should have JDK8 in order to run these. I highly recommend building from
source in order to ensure that you receive the latest features/fixes.


#### Screenshots
![screenshot1](./img/ClipBoard++(1).png)

![screenshot2](./img/ClipBoard++(2).png)

Note: ClipBoard++ makes no use of C++, the name simply implies a Clipboard better than
the system clipboard.
