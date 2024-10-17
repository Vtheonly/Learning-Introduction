const { app, BrowserWindow } = require('electron');


let mainWindow = null;
app.on('ready', () => {
  console.log('Hello from Electron.');
  mainWindow = new BrowserWindow({
    width: 8000,
    height: 600
  });

  mainWindow.loadFile('index.html');

});