ConsoleWrite($CmdLine[1] & @CRLF);
ConsoleWrite($CmdLine[2] & @CRLF);
WinWait("Open","",5);
ControlFocus("Open","","ToolbarWindow323");
ControlClick("Open","","ToolbarWindow323","right");
ControlSend("Open","","ToolbarWindow323","e");
Sleep(250);
Send("{DEL}");
Sleep(250);
Send($CmdLine[1] & "{ENTER}");
Sleep(1000);
ControlFocus("Open","","Edit1");
ControlClick("Open","","Edit1");
ControlSend("Open","","Edit1",$CmdLine[2]);
Sleep(500);
ControlClick("Open","&Open","Button1");